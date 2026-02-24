package com.godker.game.player

import kotlinx.serialization.Serializable

const val SKILLS_COUNT: Int = 20
const val CLASSES_COUNT: Int = 12
const val RACES_COUNT: Int = 5
const val MAX_SPELLS: Int = 35

@Serializable
class Stats(
    var gold: Int = 0,
    var bank: Int = 0,

    var maxHp: Int = 0,
    var minHp: Int = 0,

    var maxSta: Int = 0,
    var minSta: Int = 0,
    var maxMAN: Int = 0,
    var minMAN: Int = 0,
    var maxHIT: Int = 0,
    var minHIT: Int = 0,

    var maxHam: Int = 0,
    var minHam: Int = 0,

    var maxAGU: Int = 0,
    var minAGU: Int = 0,

    var def: Int = 0,
    var exp: Long = 0,
    var elv: Byte = 0,
    var elu: Int = 0,
    var userSkills: IntArray = IntArray(SKILLS_COUNT) {0},
    var userAttributes: IntArray = IntArray(MAX_ATTRIBUTES){0},
    var userAttributesBackup: IntArray = IntArray(MAX_ATTRIBUTES){0},
    var userSpells: IntArray = IntArray(MAX_SPELLS){0},
    @Deprecated("Esto es un counter?")
    var usersKilled: Int = 0,
    @Deprecated("Esto es un counter?")
    var criminalsKilled: Int = 0,
    @Deprecated("Esto es un counter?")
    var killedNpcs: Int = 0,
    @Deprecated("Esto es un counter?")
    var skillPoints: Int = 0,

    @Deprecated("Esto es un stat")
    var expSkills: IntArray = IntArray(SKILLS_COUNT){0},
    var eluSkills: IntArray = IntArray(SKILLS_COUNT){0},

)
