package com.godker.connection.packets

import com.godker.connection.PlayerSession
import io.netty.buffer.ByteBuf

object IncomingHandler {

    private val handlers = arrayOfNulls<IncomingPacket>(ClientPackets.entries.size)

    fun count(): Int = handlers.size

    fun register(packetId: ClientPackets, packet: IncomingPacket) {
        handlers[packetId.ordinal] = packet
    }

    suspend fun handle(packetId: Int, session: PlayerSession, data: ByteBuf) {
        val packet = handlers[packetId] ?: error("Unknown packet $packetId.")

        packet.handle(session, data)
    }
}