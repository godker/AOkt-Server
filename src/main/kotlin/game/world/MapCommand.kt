package com.godker.game.world

import com.godker.game.player.Player
import kotlinx.coroutines.CompletableDeferred

sealed interface MapCommand

data class AddPlayer(val player: Player) : MapCommand
data class RemovePlayer(val playerId: Int) : MapCommand
data class MovePlayer(val playerId: Int) : MapCommand
data class GetPlayer(val playerId: Int, val reply: CompletableDeferred<Player?>) : MapCommand