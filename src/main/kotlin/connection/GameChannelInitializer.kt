package com.godker.connection

import com.godker.connection.packets.PacketDecoder
import com.godker.connection.packets.PacketEncoder
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class GameChannelInitializer(private val sessionRegistry: SessionRegistry, private val sessionFactory: PlayerSessionFactory) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()

        //Incoming bytes into packets
        pipeline.addLast("packetDecoder", PacketDecoder())

        //Packets into outgoing bytes
        pipeline.addLast("packetEncoder", PacketEncoder())

        pipeline.addLast("handler", GameChannelHandler(sessionRegistry, sessionFactory))
    }
}