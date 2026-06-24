package com.godker.game.player

import com.godker.connection.SessionRegistry
import com.godker.connection.packets.*
import com.godker.game.City
import com.godker.game.Class
import com.godker.game.Gender
import com.godker.game.Race
import com.godker.game.objects.ObjectRegistry
import com.godker.game.player.loader.PlayerRepository
import com.godker.game.world.WorldActor
import kotlin.math.floor
import kotlin.math.max

class PlayerService(
    private val repository: PlayerRepository,
    private val world: WorldActor,
    private val objectRegistry: ObjectRegistry,
    private val sessionRegistry: SessionRegistry,
) {
    suspend fun login(name: String, password: String): Player? {
        val player = repository.loadPlayerByName(name) ?: return null

        //TODO: password check

        player.apply {
            flags.apply {
                targetNpc = 0
                targetNpcType = NpcType.COMMON
                targetObj = 0
                targetUser = 0
                privileges = PlayerType.USER
            }

            //TODO: constants for empty shield, helmet, weapon
            char.apply {
                fx = 0
                shield = if (inventory == null || inventory.shieldSlot == 0) 2 else shield
                helmet = if (inventory == null || inventory.helmetSlot == 0) 2 else helmet
                weapon = if (inventory == null || inventory.weaponSlot == 0) 2 else weapon
            }

            inventory?.bagSlot?.takeIf { it > 0 }?.let { slot ->
                objectRegistry.getOrNull(slot)?.bagType?.let { type ->
                    inventory.updateInventoryLimit(type)
                }
            }
        }
        //TODO:login validations

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

        //TODO: return an object for error notification instead of nulls
        //TODO: more name validations?
        if (name.isEmpty() || name.length > 15 || password.isEmpty() || mail.isEmpty()) return null

        if (repository.exists(name)) return null

        if (attributes[Attributes.STRENGTH.ordinal] == 0) return null

        val playerAppearance = Appearance.default(race, gender, head) ?: return null
        val counters = Counters()
        val flags = Flags()
        val stats = Stats()

        //TODO: reputation

        stats.userAttributes = attributes
        stats.userAttributesBackup = attributes

        stats.skillPoints = 10

        var randStat =
            (1..(floor((stats.userAttributes[Attributes.CONSTITUTION.ordinal] / 3).toFloat())).toInt()).random()
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

        when (archetype) {
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

        val playerInventory = Inventory(30)

        //Newbie red potion
        playerInventory.add(InventoryObject(857, 200))

        if (stats.maxMAN > 0 || archetype == Class.PALADIN) {
            //Newbie mana potion
            playerInventory.add(InventoryObject(856, 200))
        } else {
            //Newbie dexterity potions
            playerInventory.add(InventoryObject(855, 100))
            //Newbie strength potions
            playerInventory.add(InventoryObject(858, 50))
        }

        //Clothes
        playerInventory.armourSlot = when (race) {
            Race.HUMAN -> playerInventory.add(InventoryObject(463, 1, true))
            Race.ELF -> playerInventory.add(InventoryObject(464, 1, true))
            Race.DROW -> playerInventory.add(InventoryObject(465, 1, true))
            Race.DWARF, Race.GNOME -> playerInventory.add(InventoryObject(466, 1, true))
        }
        playerInventory.armourObjId = playerInventory.get(playerInventory.armourSlot).objId

        //Weapon
        playerInventory.weaponSlot = when (archetype) {
            Class.HUNTER -> playerInventory.add(InventoryObject(859, 1, true))
            Class.WORKER -> playerInventory.add(InventoryObject((561..565).random(), 1, true))
            else -> playerInventory.add(InventoryObject(460, 1, true))
        }
        playerInventory.weaponObjId = playerInventory.get(playerInventory.weaponSlot).objId
        val obj = objectRegistry.getOrNull(playerInventory.weaponObjId) ?: return null
        playerAppearance.weapon = Appearance.getWeaponAnimation(obj, race)

        if (archetype == Class.HUNTER) {
            playerInventory.ammoSlot = playerInventory.add(InventoryObject(860, 150, true))
            playerInventory.ammoObjId = 860
        }

        //Rations
        playerInventory.add(InventoryObject(467,100))
        playerInventory.add(InventoryObject(468,100))

        //Default animations?
        playerAppearance.shield = Appearance.emptyShield()
        playerAppearance.helmet = Appearance.emptyHelmet()

        //TODO: reset factions

        val player = Player(
            0/*TODO: ID*/, name, mail,
            playerAppearance, playerAppearance,
            archetype = archetype, home = home, race = race, gender = gender,
            position = Triple(1, 50, 50) /*TODO: position by home?*/,
            counters = counters,
            flags = flags,
            stats = stats,
            inventory = playerInventory
        )

        repository.create(player, password, "0.0.0.0")
        //return player
        return login(name, password)
    }

    suspend fun handleIncoming(sessionId: Long, packet: IncomingPacket) {

        return when (packet) {
            is LoginPacket -> handleLogin(sessionId, packet)
            is ThrowDicePacket -> handleThrowDice(sessionId)
            is LoginCreatePacket -> handleCreateNewUser(sessionId, packet)
            is QuitPacket -> handleLogout(sessionId)
        }
    }

    private suspend fun handleLogin(sessionId: Long, packet: LoginPacket) {
        //TODO something if failed
        val player = login(packet.name, packet.password) ?: return //TODO: ?
        completeLogin(sessionId, player)
    }

    private fun handleThrowDice(sessionId: Long) {

        val session = sessionRegistry.getSession(sessionId)?: return

        session.pendingDiceThrowing[Attributes.STRENGTH.ordinal] = //Fuerza
            max(15, 13 + (0..3).random() + (0..2).random())
        session.pendingDiceThrowing[Attributes.DEXTERITY.ordinal] = //Agilidad
            max(15, 12 + (0..3).random() + (0..3).random())
        session.pendingDiceThrowing[Attributes.INTELLIGENCE.ordinal] = //Inteligencia
            max(16, 13 + (0..3).random() + (0..2).random())
        session.pendingDiceThrowing[Attributes.CHARISMA.ordinal] = //Carisma
            max(15, 12 + (0..3).random() + (0..3).random())
        session.pendingDiceThrowing[Attributes.CONSTITUTION.ordinal] = //Constitución
            16 + (0..1).random() + (0..1).random()

        session.send(DiceRoll(session.pendingDiceThrowing))
    }

    private suspend fun handleCreateNewUser(sessionId: Long, packet: LoginCreatePacket) {
        //TODO: Dice validation

        val session = sessionRegistry.getSession(sessionId)?: return

        println("Creating a char named ${packet.name}")

        val player = create(
            packet.name, packet.password, packet.mail,
            Class of packet.archetype.toInt(),
            Race of packet.race.toInt(),
            Gender of packet.gender.toInt(),
            City of packet.home.toInt(),
            packet.head,
            session.pendingDiceThrowing
        ) ?: return //TODO: session.send(ErrorMsg)

        completeLogin(sessionId, player)
    }

    private suspend fun completeLogin(sessionId: Long, player: Player) {
        sessionRegistry.bindPlayer(sessionId, player.id)
        
        updateUserInventory(player)
        updateUserSpells(player)

        player.flags.resurrectionLock = !player.flags.dead
        val msgType = if (player.flags.resurrectionLock)
            MessageType.RESUSCITATION_SAFE_ON
        else
            MessageType.RESUSCITATION_SAFE_OFF

        sessionRegistry.send(player.id, SendMultiMessage(msgType))

        if (player.flags.paralyzed)
            sessionRegistry.send(player.id, ParalyzeOk())

        //Seguir por If mapa = 0 Then
        world.registerPlayer(player)

        sessionRegistry.send(player.id, LoggedOk(player.archetype))
    }

    private suspend fun handleLogout(sessionId: Long) {

        sessionRegistry.getPlayerBySession(sessionId)?.let { playerId ->
            val player = world.getPlayer(playerId)?: return

            //TODO: process of AOs quit
            repository.save(player)

            world.unregisterPlayer(playerId)
        }

        sessionRegistry.unbindPlayer(sessionId)
    }

    private fun updateUserInventory(player: Player, slot: Int = -1) {
        val inventory = player.inventory ?: return

        if (slot == -1) {
            inventory.getAll().forEachIndexed { index, `object` ->
                val objInfo = objectRegistry.getOrNull(`object`.objId) ?: return@forEachIndexed
                sessionRegistry.send(player.id, InventoryUpdate(player.id, `object`, index, objInfo))
            }
        }else{
            inventory.get(slot).let {
                val objInfo = objectRegistry.getOrNull(it.objId) ?: return
                sessionRegistry.send(player.id, InventoryUpdate(player.id, it, slot, objInfo))
            }
        }
    }
    
    private fun updateUserSpells(player: Player, slot: Int = -1) {
        val spells = player.stats.userSpells
        
        if (slot == -1) {
            spells.forEachIndexed { index, spell ->
                //TODO: spells names (spells loading)
                sessionRegistry.send(player.id, SpellListUpdate(player.id, spell, index, "(None)"))
            }
        } else sessionRegistry.send(player.id, SpellListUpdate(player.id, spells[slot], slot, "(None)"))
    }
}