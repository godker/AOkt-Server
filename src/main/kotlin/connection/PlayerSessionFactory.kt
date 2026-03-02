package com.godker.connection

import com.godker.game.player.PlayerService
import io.netty.channel.Channel
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.atomic.AtomicLong

class PlayerSessionFactory(
    private val playerService: PlayerService,
    private val scope: CoroutineScope
) {
    private val sessionIdGenerator = AtomicLong(0)

    fun createSession(channel: Channel): PlayerSession {
        return PlayerSession(
            sessionIdGenerator.incrementAndGet(),
            channel,
            scope,
            playerService
        )
    }
}