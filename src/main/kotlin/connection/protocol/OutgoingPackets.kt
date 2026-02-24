package com.godker.connection.protocol

import com.godker.connection.packets.OutgoingPacket
import com.godker.connection.packets.ServerPackets
import com.godker.game.Class
import io.netty.buffer.ByteBuf

class LoggedPacket(private val archetype: Class) : OutgoingPacket {

    override fun write(data: ByteBuf) {
        data.writeByte(ServerPackets.LOGGED.ordinal)
        data.writeByte(archetype.ordinal)
    }
}

class DiceRollPacket(private val stats: IntArray) : OutgoingPacket {
    override fun write(data: ByteBuf) {
        data.writeByte(ServerPackets.DICEROLL.ordinal)

        data.writeByte(stats[0])
        data.writeByte(stats[1])
        data.writeByte(stats[2])
        data.writeByte(stats[3])
        data.writeByte(stats[4])
    }
}

class SendUserIdInServer(private val playerId: Int) : OutgoingPacket {
    override fun write(data: ByteBuf) {
        data.writeByte(ServerPackets.USERINDEXINSERVER.ordinal)

        data.writeShort(playerId)
    }
}

class SendChangeMap(private val map: Int, private val version: Int) : OutgoingPacket {
    override fun write(data: ByteBuf) {
        data.writeByte(ServerPackets.CHANGEMAP.ordinal)

        data.writeShort(map)
        data.writeShort(version)
    }
}