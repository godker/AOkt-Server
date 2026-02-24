package com.godker.connection.packets

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.nio.ByteOrder

class PacketEncoder : MessageToByteEncoder<OutgoingPacket>() {

    override fun encode(ctx: ChannelHandlerContext, packet: OutgoingPacket, data: ByteBuf) {
        val buf = data.order(ByteOrder.LITTLE_ENDIAN)
        packet.write(buf)
    }
}