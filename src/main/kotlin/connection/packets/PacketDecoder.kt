package com.godker.connection.packets

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.nio.ByteOrder

class PacketDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, data: ByteBuf, out: MutableList<Any>) {

        val buf = data.order(ByteOrder.LITTLE_ENDIAN)
        if (buf.readableBytes() <= 0) return

        buf.markReaderIndex()

        val packetId = buf.readUnsignedByte().toInt()

        val payload = buf.readRetainedSlice(buf.readableBytes())

        out.add(Pair(packetId, payload))
    }
}