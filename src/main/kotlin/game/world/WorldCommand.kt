package com.godker.game.world

import com.godker.connection.PlayerSession
import com.godker.game.player.Player

sealed interface WorldCommand
data class RegisterPlayer(val player: Player) : WorldCommand
data class UnregisterPlayer(val playerId: Int) : WorldCommand
data class ChangeMap(val player: Player, val fromMapId: Int, val toMapId: Int) : WorldCommand