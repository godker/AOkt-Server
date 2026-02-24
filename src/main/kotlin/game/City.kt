package com.godker.game

enum class City(val id: Int, val title: String) {
    ULLATHORPE(1, "Ullathorpe"),
    NIX(2, "Nix"),
    BANDERBILL(3, "Banderbill"),
    LINDOS(4, "Lindos"),
    ARGHAL(5, "Arghal"),
    ARKHEIN(6, "Arkhein");

    companion object {
        infix fun of(id: Int): City = entries.firstOrNull { it.id == id } ?: throw IllegalArgumentException("No such city: $id")
        infix fun nameOf(id: Int): String = entries.firstOrNull { it.id == id }?.title ?: throw IllegalArgumentException("No such city: $id")
    }
}