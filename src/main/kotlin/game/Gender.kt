package com.godker.game

enum class Gender(val id: Int, val value: String) {
    MALE(1, "Hombre"),
    FEMALE(2, "Mujer");

    companion object {
        infix fun of(id: Int): Gender = entries.firstOrNull { it.id == id } ?: throw IllegalArgumentException("No such gender: $id")
        infix fun nameOf(id: Int): String = entries.firstOrNull { it.id == id }?.value ?: throw IllegalArgumentException("No such gender: $id")
    }
}