package com.godker.game.player

import com.godker.game.City
import com.godker.game.Class
import com.godker.game.Gender
import com.godker.game.Race
import com.godker.game.player.loader.PlayerRepository
import com.godker.game.world.WorldActor
import kotlin.math.floor

class PlayerService(
    private val repository: PlayerRepository,
    private val world: WorldActor
) {

    suspend fun login(name: String, password: String): Player? {
        val player = repository.loadPlayerByName(name) ?: return null

        //TODO: password check

        world.registerPlayer(player)

        return player
    }

    suspend fun create(name: String,
                       password: String,
                       mail: String,
                       archetype: Class,
                       race: Race,
                       gender: Gender,
                       home: City,
                       head: Int,
                       attributes: IntArray
                       ): Player? {

        //TODO: validations?

        val playerAppearance = Appearance.default(race, gender, head) ?: return null
        val counters = Counters()
        val flags = Flags()
        val stats = Stats()

        stats.userAttributes = attributes
        stats.userAttributesBackup = attributes

        stats.skillPoints = 10

        var randStat = (1..(floor((stats.userAttributes[Attributes.CONSTITUTION.ordinal] / 3).toFloat())).toInt()).random()
        stats.maxHp = 15 + randStat
        stats.minHp = 15 + randStat

        randStat = (1..(floor((stats.userAttributes[Attributes.DEXTERITY.ordinal] / 6).toFloat())).toInt()).random()
        if (randStat == 1) randStat = 2

        stats.maxSta = 20 * randStat
        stats.minSta = 20 * randStat

        stats.maxAGU = 100
        stats.minAGU = 100
        stats.maxHam = 100
        stats.minHam = 100

        stats.maxMAN = 0
        stats.minMAN = 0

        when(archetype){
            Class.MAGE -> {
                stats.maxMAN = stats.userAttributes[Attributes.INTELLIGENCE.ordinal] * 3
                stats.minMAN = stats.userAttributes[Attributes.INTELLIGENCE.ordinal] * 3
                stats.userSpells[0] = 2
            }
            Class.CLERIC, Class.DRUID, Class.BARD, Class.ASSASSIN, Class.BANDIT -> {
                stats.maxMAN = 50
                stats.minMAN = 50

                if (archetype != Class.BANDIT)
                    stats.userSpells[0] = 2
                if (archetype == Class.DRUID)
                    stats.userSpells[1] = 46
            }
            else -> {
                stats.maxMAN = 0
                stats.minMAN = 0
            }
        }

        stats.maxHIT = 2
        stats.minHIT = 1

        stats.exp = 0
        stats.elu = 300
        stats.elv = 1


        val inventory = Inventory(30)
        inventory.add(InventoryObject(857, 200))

        val player = Player(
            0/*TODO: ID*/, name, mail,
            playerAppearance, playerAppearance,
            archetype = archetype, home = home, race = race, gender = gender,
            position = Triple(1, 50, 50) /*TODO: position by home?*/,
            counters = counters,
            flags = flags,
            stats = stats,
            inventory = inventory
        )

        repository.save(player)
        //return player
        return login(name, password)
    }

    suspend fun logout(player: Player) {
        repository.save(player)

        world.unregisterPlayer(player.id)
    }
}