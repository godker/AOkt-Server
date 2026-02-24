package com.godker.connection

import com.godker.connection.packets.IncomingHandler
import com.godker.server.ServerContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.AttributeKey
import kotlinx.coroutines.launch

class GameChannelHandler(
    private val serverContext: ServerContext,
    private val sessionRegistry: SessionRegistry
) : SimpleChannelInboundHandler<Pair<Int, ByteBuf>>() {

    val SESSION_KEY = AttributeKey.valueOf<PlayerSession>("session")

    override fun channelActive(ctx: ChannelHandlerContext) {
        val sessionId = serverContext.sessionIdGenerator.incrementAndGet()

        val session = PlayerSession(sessionId, ctx.channel(), serverContext)
        ctx.channel().attr(SESSION_KEY).set(session)

        println("Client connected: ${ctx.channel().remoteAddress()}")
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        val session = ctx.channel().attr(SESSION_KEY).get() ?: error("Session not connected")
        val player = session.player

        if (player != null) {
            sessionRegistry.unregisterSession(player.id)
            println("Client disconnected: ${player.id}")
        }else{
            print("Client disconnected before login.")
        }

        session.disconnect()
    }

    override fun channelRead0(ctx: ChannelHandlerContext, packet: Pair<Int, ByteBuf>) {
        val session = ctx.channel().attr(SESSION_KEY).get() ?: error("Session not connected")
        val (packetId, data) = packet

        session.context.scope.launch {
            try {
                IncomingHandler.handle(packetId, session, data)
            } finally {
                data.release()
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}