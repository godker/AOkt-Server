package com.godker.connection.packets

import com.godker.game.Class
import com.godker.game.objects.Object
import com.godker.game.player.InventoryObject

sealed interface OutgoingPacket {
    val packetId: ServerPackets
}

data class LoggedOk(val archetype: Class) : OutgoingPacket {
    override val packetId = ServerPackets.LOGGED
}

data class DiceRoll(val stats: IntArray) : OutgoingPacket {
    override val packetId = ServerPackets.DICEROLL
}

data class SendUserIdInServer(val playerId: Int) : OutgoingPacket {
    override val packetId = ServerPackets.USERINDEXINSERVER
}

data class SendChangeMap(val map: Int, val version: Int) : OutgoingPacket {
    override val packetId = ServerPackets.CHANGEMAP
}

data class InventoryUpdate(val playerId: Int, val item: InventoryObject, val slot: Int, val objInfo: Object) : OutgoingPacket {
    override val packetId = ServerPackets.CHANGEINVENTORYSLOT
}

data class SpellListUpdate(val playerId: Int, val spell: Int, val slot: Int, val spellName: String): OutgoingPacket {
    override val packetId = ServerPackets.CHANGESPELLSLOT
}

data class SendMultiMessage(val messageId: MessageType, val args: List<Any>? = null) : OutgoingPacket {
    override val packetId = ServerPackets.MULTIMESSAGE
}

data class ParalyzeOk(override val packetId: ServerPackets = ServerPackets.PARALYZEOK): OutgoingPacket

enum class MessageType {
    DONT_SEE_ANYTHING,
    NPC_SWING,
    NPC_KILLS_USER,
    BLOCKED_WITH_SHIELD_USER,
    BLOCKED_WITH_SHIELD_OTHER,
    USER_SWING,
    SAFE_MODE_ON,
    SAFE_MODE_OFF,
    RESUSCITATION_SAFE_OFF,
    RESUSCITATION_SAFE_ON,
    NOBILITY_LOST,
    CANT_USE_WHILE_MEDITATING,
    NPC_HIT_USER,
    USER_HIT_NPC,
    USER_ATTACKED_SWING,
    USER_HITTED_BY_USER,
    USER_HITTED_USER,
    WORK_REQUEST_TARGET,
    HAVE_KILLED_USER,
    USER_KILL,
    EARN_EXP,
    HOME,
    CANCEL_HOME,
    FINISH_HOME,
}