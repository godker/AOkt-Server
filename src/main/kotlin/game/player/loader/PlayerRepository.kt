package com.godker.game.player.loader

import com.godker.game.player.Player

interface PlayerRepository {
    suspend fun loadPlayerByName(name: String): Player?
    suspend fun save(player: Player)
    suspend fun exists(name: String): Boolean
}