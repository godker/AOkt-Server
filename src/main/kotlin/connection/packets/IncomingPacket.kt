package com.godker.connection.packets

import com.godker.connection.PlayerSession
import io.netty.buffer.ByteBuf

interface IncomingPacket {
    suspend fun handle(session: PlayerSession, data: ByteBuf)
}
