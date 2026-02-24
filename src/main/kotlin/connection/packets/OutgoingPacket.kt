package com.godker.connection.packets

import io.netty.buffer.ByteBuf

interface OutgoingPacket {
    fun write(data: ByteBuf)
}