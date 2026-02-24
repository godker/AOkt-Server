package com.godker.server

import com.godker.game.player.PlayerService
import com.godker.game.world.WorldActor
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.atomic.AtomicLong

class ServerContext(
    val playerService: PlayerService,
    val worldActor: WorldActor,
    val scope: CoroutineScope
) {

    val sessionIdGenerator = AtomicLong(0)
}