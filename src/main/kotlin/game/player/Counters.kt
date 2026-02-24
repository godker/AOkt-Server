package com.godker.game.player
import kotlinx.serialization.Serializable

@Serializable
data class Counters(
    var idle: Int = 0,
    var attack: Int = 0,
    var hp: Int = 0,
    var stamina: Int = 0,
    var freezing: Int = 0,
    var lava: Int = 0, //TODO: burning?
    var food: Int = 0,
    var water: Int = 0,
    var poison: Int = 0,
    var paralisis: Int =0,
    var blind: Int = 0,
    var dumb: Int = 0,
    var invisibility: Int = 0,
    var hidden: Int = 0,
    var mimetized: Int = 0,
    var blocking: Int = 0,
    var penalty: Int = 0,
    var sendToMap: Triple<Int, Int, Int>? = null,
    //TODO: esto es un flag...
    @Deprecated("Esto es un flag")
    var exiting: Boolean = false,
    var exit: Int = 0,
    var meditation: Int = 0,
    //TODO: esto es un flag...
    @Deprecated("Esto es un flag")
    var canMeditate: Boolean = false,
    var timerCastSpell: Int  = 0,
    var timerCanAttack: Int = 0,
    var timerCanUseBow: Int = 0,
    var timerCanWork: Int = 0,
    var timerUse: Int = 0,
    var timerSpellAndHit: Int = 0,
    var timerHitAndSpell: Int = 0,
    var timerHitAndUse: Int = 0,
    var timerAttackable: Int = 0,
    var timerOwnNpc: Int = 0,
    //TODO: ver que es esto
    var timerAttackableState: Int = 0,
    //TODO: no es un flag?
    var working: Int = 0,
    var hiding: Int = 0,
    var failedUsageAttempts: Int = 0,
    var goHome: Int = 0,
    @Deprecated("Esto es un stat. Puede ser también calculado como la suma de todos los skills.")
    var assignedSkills: Byte = 0
){

    fun resetCounters() {
        idle = 0
        attack = 0
        hp = 0
        stamina = 0
        freezing = 0
        lava = 0
        food = 0
        water = 0
        poison = 0
        paralisis = 0
        blind = 0
        dumb = 0
        invisibility = 0
        hidden = 0
        mimetized = 0
        blocking = 0
        penalty = 0
        exiting = false
        exit = 0
        meditation = 0
        canMeditate = false
        timerCastSpell = 0
        timerCanAttack = 0
        timerCanUseBow = 0
        timerCanWork = 0
        timerUse = 0
        timerSpellAndHit = 0
        timerHitAndSpell = 0
        timerHitAndUse = 0
        timerAttackable = 0
        timerOwnNpc = 0
        timerAttackableState = 0
        working = 0
        hiding = 0
        failedUsageAttempts = 0
        goHome = 0
        assignedSkills = 0
    }
}
