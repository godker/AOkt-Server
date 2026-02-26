package com.godker.game.objects

import com.godker.game.player.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path

object ObjectLoader {

    fun load(basePath: Path): Map<Int, Object>? {
        val file = basePath.resolve("obj.json")

        if (!Files.exists(file)) return null

        val content = Files.readAllLines(file).joinToString("\n")

        return Json.decodeFromString<Map<Int, Object>>(content)
    }

}