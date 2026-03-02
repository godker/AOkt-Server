package com.godker.connection

import com.godker.connection.packets.OutgoingPacket
import com.godker.game.player.Player
import com.godker.game.player.PlayerService
import io.netty.channel.Channel
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class SessionRegistry {
    data class SessionEntry(
        val session: PlayerSession,
        var playerId: Int? = null
    )

    //SessionId -> SessionEntry
    private val sessions = ConcurrentHashMap<Long, SessionEntry>()

    //PlayerId -> SessionId
    private val playerToSession = ConcurrentHashMap<Int, Long>()

    fun registerSession(session: PlayerSession){
        sessions[session.sessionId] = SessionEntry(session)
    }

    fun getSession(sessionId: Long) = sessions[sessionId]?.session

    fun bindPlayer(sessionId: Long, playerId: Int) {
        val entry = sessions[sessionId] ?: return
        entry.playerId = playerId
        playerToSession[playerId] = sessionId
    }

    fun unbindPlayer(sessionId: Long) {
        val entry = sessions[sessionId] ?: return
        val playerId = entry.playerId ?: return

        playerToSession.remove(playerId)
        entry.playerId = null
    }

    fun getSessionByPlayer(playerId: Int): PlayerSession? {
        val sessionId = playerToSession[playerId] ?: return null
        return sessions[sessionId]?.session
    }

    fun getPlayerBySession(sessionId: Long): Int? {
        return sessions[sessionId]?.playerId
    }

    fun destroySession(sessionId: Long) {
        val entry = sessions.remove(sessionId) ?: return

        entry.session.disconnect()

        entry.playerId?.let { playerId ->
            playerToSession.remove(playerId)
        }
    }

    fun send(playerId: Int, packet: OutgoingPacket) {
        getSessionByPlayer(playerId)?.send(packet)
    }
}