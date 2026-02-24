package com.godker.game.world

import com.godker.connection.PlayerSession
import com.godker.connection.SessionRegistry
import com.godker.game.player.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlin.collections.forEach

class WorldActor(mapsData: Map<Int, MapData>, private val sessionRegistry: SessionRegistry, scope: CoroutineScope) {
    val maps = mutableMapOf<Int, MapActor>()

                                         //id, map
    private val playerMaps = mutableMapOf<Int, Int>()

    //TODO: check
    private var nextPlayerId = 0

    init {
        mapsData.forEach { (id, data) ->
            maps[id] = MapActor(id, data, scope)
        }
    }

    private val channel = scope.actor<WorldCommand>{

        for (command in channel) {
            when (command) {
                is WorldCommand.RegisterPlayer -> {
                    val player = command.player
                    val mapId = player.position.first
                    playerMaps[player.id] = mapId

                    //Notify the map
                    maps[mapId]?.send(MapCommand.AddPlayer(player))
                }
                is WorldCommand.UnregisterPlayer -> {
                    val playerId = command.playerId
                    val mapId = playerMaps.remove(playerId)

                    if (mapId != null) {
                        //Notify
                        maps[mapId]?.send(MapCommand.RemovePlayer(playerId))
                    }
                }
                is WorldCommand.ChangeMap -> {
                    //Remove the player from the previous map
                    maps[command.fromMapId]?.send(MapCommand.RemovePlayer(command.player.id))

                    //Update the global location
                    playerMaps[command.player.id] = command.toMapId

                    //Adds to the new map
                    maps[command.toMapId]?.send(MapCommand.AddPlayer(command.player))
                }
            }
        }
    }

    suspend fun registerPlayer(player: Player) {
        send(WorldCommand.RegisterPlayer(player))
    }

    suspend fun unregisterPlayer(playerId: Int) {
        send(WorldCommand.UnregisterPlayer(playerId))
    }

    suspend fun send(command: WorldCommand) {
        channel.send(command)
    }
}