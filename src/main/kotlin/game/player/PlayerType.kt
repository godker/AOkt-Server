package com.godker.game.player

enum class PlayerType(val value: Int) {
    USER(0x01),
    COUNSELOR(0x02),
    SEMI_GOD(0x04),
    GOD(0x08),
    ADMIN(0x10),
    ROLE_MASTER(0x20),
    CHAOS_COUNCIL(0x40),
    ROYAL_COUNCIL(0x80),
}