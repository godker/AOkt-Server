package com.godker.game.world

import com.godker.connection.PlayerSession
import com.godker.connection.SessionRegistry
import com.godker.game.player.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.collections.forEach

class WorldActor(mapsData: Map<Int, MapData>, parentScope: CoroutineScope) {
    private val maps = mutableMapOf<Int, MapActor>()

                                         //id, map
    private val playerMaps = mutableMapOf<Int, Int>()

    private val scope = CoroutineScope(parentScope.coroutineContext + SupervisorJob())

    init {
        mapsData.forEach { (id, data) ->
            maps[id] = MapActor(id, data, scope)
        }
    }

    private val mailbox = scope.actor<WorldCommand>{

        for (command in channel) {
            when (command) {
                is RegisterPlayer -> {
                    val player = command.player
                    val mapId = player.position.first
                    playerMaps[player.id] = mapId

                    //Notify the map
                    maps[mapId]?.send(AddPlayer(player))
                }
                is UnregisterPlayer -> {
                    val playerId = command.playerId
                    val mapId = playerMaps.remove(playerId)

                    if (mapId != null) {
                        //Notify
                        maps[mapId]?.send(RemovePlayer(playerId))
                    }
                }
                is ChangeMap -> {
                    //Remove the player from the previous map
                    maps[command.fromMapId]?.send(RemovePlayer(command.player.id))

                    //Update the global location
                    playerMaps[command.player.id] = command.toMapId

                    //Adds to the new map
                    maps[command.toMapId]?.send(AddPlayer(command.player))
                }
            }
        }
    }

    suspend fun getPlayer(playerId: Int): Player? {
        val mapId = playerMaps[playerId] ?: return null
        return maps[mapId]?.getPlayer(playerId)
    }

    suspend fun registerPlayer(player: Player) {
        send(RegisterPlayer(player))
    }

    suspend fun unregisterPlayer(playerId: Int) {
        send(UnregisterPlayer(playerId))
    }

    suspend fun send(command: WorldCommand) {
        mailbox.send(command)
    }
}