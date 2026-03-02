package com.godker.connection

import com.godker.connection.packets.IncomingPacket
import com.godker.connection.packets.QuitPacket
import com.godker.game.player.PlayerService
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.AttributeKey
import java.util.concurrent.atomic.AtomicLong

class GameChannelHandler(
    private val sessionRegistry: SessionRegistry,
    private val sessionFactory: PlayerSessionFactory
) : SimpleChannelInboundHandler<IncomingPacket>() {

    val SESSION_KEY = AttributeKey.valueOf<PlayerSession>("session")

    override fun channelActive(ctx: ChannelHandlerContext) {
        val session = sessionFactory.createSession(ctx.channel())

        sessionRegistry.registerSession(session)
        ctx.channel().attr(SESSION_KEY).set(session)

        println("Client connected: ${ctx.channel().remoteAddress()}")
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        val session = ctx.channel().attr(SESSION_KEY).get() ?: error("Session not connected")

        session.receive(QuitPacket)

        println("Client disconnected: ${ctx.channel().remoteAddress()}")
    }

    override fun channelRead0(ctx: ChannelHandlerContext, packet: IncomingPacket) {
        val session = ctx.channel().attr(SESSION_KEY).get() ?: error("Session not connected")

        session.receive(packet)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}