package com.godker.game.world

import com.godker.connection.PlayerSession
import com.godker.game.player.Player
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

// Map Actor = Mapa
class MapActor(val mapId: Int, val mapData: MapData, parentScope: CoroutineScope) {

    private val scope = CoroutineScope(parentScope.coroutineContext + SupervisorJob())

    //Canal de comunicación entre coroutines
    private val mailbox = scope.actor<MapCommand> {
        val players = mutableMapOf<Int, Player>()

        for (command in channel) {
            when (command) {
                is AddPlayer -> {
                    players[command.player.id] = command.player
                }
                is RemovePlayer -> {
                    players.remove(command.playerId)
                }
                is MovePlayer -> {
                    throw NotImplementedError("Not yet")
                }
                is GetPlayer -> {
                    command.reply.complete(
                        players[command.playerId]
                    )
                }
            }
        }
    }

    suspend fun getPlayer(playerId: Int): Player? {
        val deferred = CompletableDeferred<Player?>()
        mailbox.send(GetPlayer(playerId, deferred))
        return deferred.await()
    }

    suspend fun send(command: MapCommand) {
        mailbox.send(command)
    }
}