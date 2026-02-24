package com.godker.game.player

import kotlinx.serialization.Serializable

//TODO: Redo this class

@Serializable
data class Flags(
    var dead: Byte = 0, //¿Esta muerto?
    var trading: Boolean = false, //¿Esta comerciando?
    var logged: Boolean = false, //¿Esta online?
    var meditating: Boolean = false,
    var hunger: Byte = 0,
    var thirst: Byte = 0,
    var canMove: Byte = 0,
    @Deprecated("Por que hay un timer en flags?")
    var timerLanzarSpell: Int = 0,
    var canWork: Byte = 0,
    var poisoned: Byte = 0,
    var paralyzed: Byte = 0,
    var immobilized: Byte = 0,
    var dumbness: Byte = 0,
    var blindness: Byte = 0,
    var invisible: Byte = 0,
    var cursed: Byte = 0,
    var blessed: Byte = 0,
    var hidden: Byte = 0,
    var naked: Byte = 0,
    var rest: Boolean = false,
    var spell: Int = 0,
    var potionDrank: Boolean = false,
    var potionType: Byte = 0,

    var cantBeAttacked: Boolean = false,
    var attackableBy: Int = 0,
    var shareNpcWith: Int = 0,

    var flies: Byte = 0,
    var navigating: Byte = 0,
    var lock: Boolean = false,
    var resurrectionLock: Boolean = false,

    var effectDuration: Int = 0,
    var targetNpc: Int = 0, // Npc señalado por el usuario
    var targetNpcType: NpcType? = null, // Tipo del npc señalado
    var ownedNpc: Int = 0, // Npc que le pertenece (no puede ser atacado)
    var npcInv: Int = 0,

    var ban: Byte = 0,
    var administrativeBan: Byte = 0,

    var targetUser: Int = 0, // Usuario señalado

    var targetObj: Int = 0, // Obj señalado
    var targetObjMap: Int = 0,
    var targetObjX: Int = 0,
    var targetObjY: Int = 0,

    var targetMap: Int = 0,
    var targetX: Int = 0,
    var targetY: Int = 0,

    var targetObjInvIndex: Int = 0,
    var targetObjInvSlot: Int = 0,

    var attackedByNpc: Int = 0,
    var attackedByUser: Int = 0,
    var attackedNpc: Int = 0,
    var ignored: Boolean = false,

    var inConsultation: Boolean = false,
    var sendDenounces: Boolean = false,

    var statsChanged: Byte = 0,
    var privileges: PlayerType = PlayerType.USER,
    var specialPrivilege: Boolean = false,

    var lastKilledCriminal: String = "",
    var lastKilledCitizen: String = "",

    var oldBody: Int = 0,
    var oldHead: Int = 0,
    var adminInvisible: Byte = 0,
    var adminChasable: Boolean = false,

    var chatColor: Int = 0,

    var timesWalk: Int = 0,
    var startWalk: Int = 0,
    var countSH: Int = 0,

    var lastMessage: Byte = 0,

    var silenced: Byte = 0,

    var mimetized: Byte = 0,

    var sentinelIndex: Byte = 0,
    var sentinelOk: Boolean = false,
    var lastMap: Int = 0,
    var traveling: Byte = 0,
    var paralyzedBy: String = "",
    var paralyzedByIndex: Int = 0,
    var paralyzedByNpcIndex: Int = 0
)
