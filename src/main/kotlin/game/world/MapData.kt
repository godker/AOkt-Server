package com.godker.game.world

// Esta clase sería un equivalente al MapBlock, pero genérico para todos los mapas
class MapData (val width: Int = 100, val height: Int = 100) {
    val mapSize = width * height

    var layer1 = IntArray(mapSize)
    var layer2 = IntArray(mapSize)
    var layer3 = IntArray(mapSize)
    var layer4 = IntArray(mapSize)

    var blocked = BooleanArray(mapSize)
    var userId = IntArray(mapSize) { 0 }
    var npcId = IntArray(mapSize) { 0 }

    //TODO: Object
    var exitTo: Array<Triple<Int, Int, Int>> = Array(mapSize) { Triple(0, 0, 0) }
    var trigger = IntArray(mapSize) { 0 }

    //Map info
    //TODO: check if needed
    var numUsers = 0
    var music = ""
    var name = ""

    var startPosition = Triple(0, 0, 0)
    var onDeathGoTo = Triple(0, 0, 0)
    var mapVersion = 0
    var unsafe = true
    var magicDisabled = false
    var invisibilityDisabled = false
    var resurrectionDisabled = false
    var hidingDisabled = false
    var summonDisabled = false
    var npcStealing = false

    var terrain = ""
    var zone = ""
    var restricted = false
    var backup = false

    fun index(x: Int, y: Int): Int {
        return y * width + x
    }
}