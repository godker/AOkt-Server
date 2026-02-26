package com.godker.game.objects


class ObjectRegistry(private val objects: Map<Int, Object>) {
    fun getOrNull(id: Int): Object? {
        return objects[id]
    }
}