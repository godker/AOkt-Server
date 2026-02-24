package com.godker.connection

import com.godker.connection.packets.OutgoingPacket
import java.util.concurrent.ConcurrentHashMap

class SessionRegistry {

    //This binds the playerId and its playerSession
    private val sessions = ConcurrentHashMap<Int, PlayerSession>()

    fun registerSession(playerId: Int, session: PlayerSession) {
        sessions[playerId] = session
    }

    fun unregisterSession(playerId: Int) {
        sessions.remove(playerId)
    }

    fun send(playerId: Int, packet: OutgoingPacket) {
        sessions[playerId]?.send(packet)
    }

    fun broadcast(packet: OutgoingPacket) {
        sessions.values.forEach { it.send(packet) }
    }
}