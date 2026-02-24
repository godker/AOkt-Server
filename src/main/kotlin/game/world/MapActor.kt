package com.godker.game.world

import com.godker.connection.PlayerSession
import com.godker.game.player.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

// Map Actor = Mapa
class MapActor(val mapId: Int, val mapData: MapData, scope: CoroutineScope) {

    //Canal de comunicación entre coroutines
    private val channel = scope.actor<MapCommand> {
        val players = mutableMapOf<Int, Player>()

        for (command in channel) {
            when (command) {
                is MapCommand.AddPlayer -> {
                    players[command.player.id] = command.player
                }
                is MapCommand.RemovePlayer -> {
                    players.remove(command.playerId)
                }
                is MapCommand.MovePlayer -> {
                    throw NotImplementedError("Not yet")
                }
            }
        }
    }

    suspend fun send(command: MapCommand) {
        channel.send(command)
    }
}