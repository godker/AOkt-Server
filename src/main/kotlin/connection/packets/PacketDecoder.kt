package com.godker.connection.packets

import com.godker.readVBString
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.nio.ByteOrder

class PacketDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, data: ByteBuf, out: MutableList<Any>) {

        val buf = data.order(ByteOrder.LITTLE_ENDIAN)
        if (buf.readableBytes() <= 0) return

        buf.markReaderIndex()

        val packet = when(val packetId = buf.readUnsignedByte().toInt()) {
            ClientPackets.LOGINEXISTINGCHAR.packetId -> {
                val userName = buf.readVBString()
                val password = buf.readVBString()

                println("Login of $userName identified by $password with client version ${data.readUnsignedByte()}.${data.readUnsignedByte()}.${data.readUnsignedByte()}")

                LoginPacket(userName, password)
            }
            ClientPackets.THROWDICES.packetId -> ThrowDicePacket
            ClientPackets.LOGINNEWCHAR.packetId -> {
                val userName = buf.readVBString()
                val password = buf.readVBString()

                //TODO: version validation
                data.readUnsignedByte()
                data.readUnsignedByte()
                data.readUnsignedByte()

                val userRace = data.readByte()
                val userGender = data.readByte()
                val userClass = data.readByte()
                val userHead = data.readShort().toInt()
                val userMail = data.readVBString()

                val userHome = data.readByte()

                LoginCreatePacket(userName, password, userRace, userGender, userClass, userHead, userMail, userHome)
            }
            else -> throw  IllegalArgumentException("Unknown packet id: $packetId")
        }

        //TODO: check, this should be done automatically by Netty
        //buf.release()

        out.add(packet)
    }
}