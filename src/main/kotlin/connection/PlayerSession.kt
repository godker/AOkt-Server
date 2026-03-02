package com.godker.connection

import com.godker.connection.packets.IncomingPacket
import com.godker.connection.packets.OutgoingPacket
import com.godker.connection.packets.QuitPacket
import com.godker.game.player.MAX_ATTRIBUTES
import com.godker.game.player.Player
import com.godker.game.player.PlayerService
import io.netty.channel.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor

class PlayerSession (
    val sessionId: Long,
    private val channel: Channel,
    scope: CoroutineScope,
    private val playerService: PlayerService
) {

    // For the throwing dice part
    // any other action that needs server processing but not a Player instance yet should be here in Session
    //TODO: check if needed to be reassigned as null somewhere.
    //TODO: if there is more info that should go here, maybe move it to a dedicated class for this data.
    var pendingDiceThrowing = IntArray(MAX_ATTRIBUTES) { 0 }

    private val mailbox = scope.actor(capacity = kotlinx.coroutines.channels.Channel.UNLIMITED){
        for (packet in channel) {
            playerService.handleIncoming(sessionId, packet)
        }
    }

    fun receive(packet: IncomingPacket) {
        mailbox.trySend(packet)
    }

    fun send(packet: OutgoingPacket){
        channel.writeAndFlush(packet)
    }

    fun disconnect(){
        mailbox.close()
        channel.disconnect()
    }
}