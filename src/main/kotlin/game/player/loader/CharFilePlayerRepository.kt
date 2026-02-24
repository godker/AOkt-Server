package com.godker.game.player.loader

import com.godker.game.player.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path

class CharFilePlayerRepository(private val basePath: Path) : PlayerRepository {
    override suspend fun loadPlayerByName(name: String): Player?{
        val file = basePath.resolve("$name.chr")

        if (!Files.exists(file)) return null

        val content = withContext(Dispatchers.IO) {
            Files.readAllLines(file)
        }.joinToString("\n")

        return Json.decodeFromString<Player>(content)
    }

    override suspend fun save(player: Player){
        val file = basePath.resolve("${player.name}.chr")
        withContext(Dispatchers.IO) {
            Files.writeString(file, Json.encodeToString(player))
        }
    }
}