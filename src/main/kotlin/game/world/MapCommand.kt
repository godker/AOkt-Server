package com.godker.game.world

import com.godker.game.player.Player

sealed interface MapCommand {
    data class AddPlayer(val player: Player) : MapCommand
    data class RemovePlayer(val playerId: Int) : MapCommand
    data class MovePlayer(val playerId: Int) : MapCommand
}