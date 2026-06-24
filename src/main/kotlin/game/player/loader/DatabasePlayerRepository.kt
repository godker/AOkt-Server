package com.godker.game.player.loader

import com.godker.database.DatabaseFactory
import com.godker.game.player.Player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.postgresql.util.PGobject

class DatabasePlayerRepository : PlayerRepository {
    override suspend fun loadPlayerByName(name: String): Player? {
        println("Voy a probar $name")
        println(exists(name))

        return null
    }

    override suspend fun save(player: Player) {
        DatabaseFactory.transaction { connection ->
            connection
        }
    }

    override suspend fun create(player: Player, password: String, ip: String) {
        DatabaseFactory.transaction { connection ->
            player.id = connection.prepareStatement(
                """INSERT INTO player
                    (
                    name, mail, password, ip, char,
                    archetype, race, gender, home, x, y, map
                    )
                    VALUES (?, ?, ?, ?::inet, ?, ?, ?, ?, ?, ?, ?, ?)
                    RETURNING id
                    """.trimIndent()
            ).use { statement ->
                statement.setString(1, player.name)
                statement.setString(2, player.mail)
                statement.setString(3, password)
                statement.setString(4, ip)

                val appearanceJson = PGobject().apply {
                    type = "jsonb"
                    value = Json.encodeToString(player.char)
                }
                statement.setObject(5, appearanceJson)
                statement.setInt(6, player.archetype.id)
                statement.setInt(7, player.race.id)
                statement.setInt(8, player.gender.id)
                statement.setInt(9, player.home.id)
                statement.setInt(10, player.position.second)
                statement.setInt(11, player.position.third)
                statement.setInt(12, player.position.first)

                statement.executeQuery().use { rs ->
                    rs.next()
                    rs.getInt("id")
                }
            }

            //TODO: validations in case that the insert fails
            println("INSERTING PLAYER $player.id")

            // Flags
            connection.prepareStatement(
                """INSERT INTO player_flags
                    (
                    player_id
                    )
                    VALUES (?)
                    """.trimIndent()
            ).use { statement ->
                statement.setInt(1, player.id)

                statement.executeUpdate()
            }

            // Inventory
            connection.prepareStatement(
                """INSERT INTO player_inventory
                    (
                    player_id, slot_index, obj_id, quantity, is_equipped
                    )
                    VALUES (?,?,?,?,?)
                """.trimIndent()
            ).use { statement ->
                for (i in 0..< player.inventory!!.limit){
                    val item = player.inventory[i]
                    statement.setInt(1, player.id)
                    statement.setInt(2, i)
                    statement.setInt(3, item.objId)
                    statement.setInt(4, item.quantity)
                    statement.setBoolean(5, item.equipped)

                    statement.addBatch()
                }

                statement.executeBatch()
            }

            // Stats
            connection.prepareStatement(
                """INSERT INTO player_stats
                    (
                    player_id, gold, bank_gold, max_hp, min_hp, max_mp, min_mp, max_sta, min_sta, max_hit, min_hit, max_hunger, min_hunger, max_thirst, min_thirst, defense, exp, elv, elu, skills, exp_skills, elu_skills, attributes, attributes_backup, spells, users_killed, criminals_killed, npcs_killed, skill_points
                    )
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """.trimIndent()
            ).use { statement ->
                statement.apply {
                    setInt(1, player.id)
                    setLong(2, player.stats.gold)
                    setLong(3, player.stats.bank)
                    setInt(4, player.stats.maxHp)
                    setInt(5, player.stats.minHp)
                    setInt(6, player.stats.maxMAN)
                    setInt(7, player.stats.minMAN)
                    setInt(8, player.stats.maxSta)
                    setInt(9, player.stats.minSta)
                    setInt(10, player.stats.maxHIT)
                    setInt(11, player.stats.minHIT)
                    setInt(12, player.stats.maxHam)
                    setInt(13, player.stats.minHam)
                    setInt(14, player.stats.maxAGU)
                    setInt(15, player.stats.minAGU)
                    setInt(16, player.stats.def)
                    setLong(17, player.stats.exp)
                    setInt(18, player.stats.elv.toInt())
                    setInt(19, player.stats.elu)

                    val skillObj = connection.createArrayOf("integer", player.stats.userSkills.toTypedArray())
                    setArray(20, skillObj)

                    val skillExpObj = connection.createArrayOf("integer", player.stats.expSkills.toTypedArray())
                    setArray(21, skillExpObj)

                    val skillEluObj = connection.createArrayOf("integer", player.stats.eluSkills.toTypedArray())
                    setArray(22, skillEluObj)

                    val attributesObj = connection.createArrayOf("integer", player.stats.userAttributes.toTypedArray())
                    setArray(23, attributesObj)

                    val attributesBackupObj = connection.createArrayOf("integer", player.stats.userAttributesBackup.toTypedArray())
                    setArray(24, attributesBackupObj)

                    val spellsObj = connection.createArrayOf("integer", player.stats.userSpells.toTypedArray())
                    setArray(25, spellsObj)

                    setInt(26, player.stats.usersKilled)
                    setInt(27, player.stats.criminalsKilled)
                    setInt(28, player.stats.killedNpcs)
                    setInt(29, player.stats.skillPoints)

                    executeUpdate()
                }
            }
        }
    }
    override suspend fun exists(name: String): Boolean {
        return DatabaseFactory.query { connection ->

                connection.prepareStatement(
                    "SELECT id FROM player WHERE name=?"
                ).use { statement ->
                    statement.setString(1, name)

                    statement.executeQuery().use { rs -> rs.next() }
                }

        }
    }
}