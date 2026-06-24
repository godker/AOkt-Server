package com.godker.game

//AO Classes are 1-based
enum class Class(val id: Int, val title: String) {
    MAGE(1, "Mago"),
    CLERIC(2, "Clérigo"),
    WARRIOR(3, "Guerrero"),
    ASSASSIN(4, "Asesino"),
    THIEF(5, "Ladrón"),
    BARD(6, "Bardo"),
    DRUID(7, "Druida"),
    BANDIT(8, "Bandido"),
    PALADIN(9, "Paladín"),
    HUNTER(10, "Cazador"),
    WORKER(11, "Trabajador"),
    PIRATE(12, "Pirata");

    companion object {
        infix fun of(id: Int): Class = entries.firstOrNull { it.id == id } ?: throw IllegalArgumentException("No such class: $id")
        infix fun nameOf(id: Int): String = entries.firstOrNull { it.id == id }?.name ?: throw IllegalArgumentException("No such class: $id")
    }
}