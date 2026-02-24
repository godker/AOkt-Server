package com.godker.game.player

const val MAX_ATTRIBUTES = 5

enum class Attributes(val value: Int, val description: String) {
    STRENGTH(0, "Fuerza"),
    DEXTERITY(1, "Agilidad"),
    INTELLIGENCE(2, "Inteligencia"),
    CHARISMA(3, "Carisma"),
    CONSTITUTION(4, "Constitución"),
}
