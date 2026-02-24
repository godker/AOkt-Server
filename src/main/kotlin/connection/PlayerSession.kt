package com.godker.connection

import com.godker.connection.packets.OutgoingPacket
import com.godker.game.player.Attributes
import com.godker.game.player.MAX_ATTRIBUTES
import com.godker.game.player.Player
import com.godker.server.ServerContext
import io.netty.channel.Channel

class PlayerSession (val sessionId: Long, private val channel: Channel, val context: ServerContext) {
    @Volatile
    var player: Player? = null

    // For the throwing dice part
    // any other action that needs server processing but not a Player instance yet should be here in Session
    //TODO: check if needed to be reassigned as null somewhere.
    //TODO: if there is more info that should go here, maybe move it to a dedicated class for this data.
    var pendingDiceThrowing = IntArray(MAX_ATTRIBUTES) { 0 }

    fun send(packet: OutgoingPacket){
        channel.writeAndFlush(packet)
    }

    fun disconnect(){
        //TODO
    }
}