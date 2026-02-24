package com.godker.game

enum class Race(val id: Int, val title: String) {
    HUMAN(1, "Humano"),
    ELF(2, "Elfo"),
    DROW(3, "Drow"),
    GNOME(4, "Gnomo"),
    DWARF(5, "Enano");

    companion object {
        infix fun of(id: Int): Race = entries.firstOrNull { it.id == id } ?: throw IllegalArgumentException("No such race: $id")
        infix fun nameOf(id: Int): String = entries.firstOrNull { it.id == id }?.title ?: throw IllegalArgumentException("No such race: $id")
    }
}