package com.godker.game.world

import java.io.DataInputStream
import java.io.FileInputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import kotlin.experimental.and
import kotlin.math.floor

private val MAP_REGEX = Regex("""Mapa(\d+).map""")

object MapLoader {
    fun loadAll(path: Path): Map<Int, MapData> {
        val maps = scanMaps(path)

        return maps.associateWith { id -> loadMap(path, id) }
    }


    /**
     * Scan for maps in [path]
     *
     * @param path
     * @return A List containing the map number in its filename.
     */
    private fun scanMaps(directory: Path): List<Int> {
        require(Files.isDirectory(directory)) {
            "Map directory $directory is not a directory"
        }

        val mapList = mutableListOf<Int>()

        Files.list(directory).use { stream ->
            stream.forEach { path ->
                val fileName = path.fileName.toString()
                val match = MAP_REGEX.matchEntire(fileName)

                if (match != null) {
                    val mapNumber = match.groupValues[1].toInt()

                    //Check if the map has all the required information
                    val mapDat = directory.resolve("Mapa$mapNumber.dat")
                    val mapInf = directory.resolve("Mapa$mapNumber.inf")

                    if (Files.exists(mapDat) && Files.exists(mapInf)) {
                        mapList += mapNumber
                    }else{
                        println("Map $mapNumber is incomplete and will not be loaded (missing .dat or .inf file).")
                    }
                }
            }
        }

        println("${mapList.size} maps found.")

        return mapList
    }

    private fun loadMap(path: Path, id: Int): MapData {
        //Load the Map layout information

        val mapBuffer = mapFile("$path/Mapa$id.map")
        val infBuffer = mapFile("$path/Mapa$id.inf")
        //dat

        val mapData = runCatching {
            MapData().apply {
                mapVersion = mapBuffer.getShort().toInt()

                //Skip useless bytes
                mapBuffer.position(mapBuffer.position() + 271)
                infBuffer.position(10)

                for (i in 0..<mapSize) {
                    var flag = mapBuffer.get().toInt()

                    blocked[i] = (flag and 1) != 0

                    layer1[i] = mapBuffer.getShort().toInt()

                    if ((flag and 2) != 0) layer2[i] = mapBuffer.getShort().toInt()
                    if ((flag and 4) != 0) layer3[i] = mapBuffer.getShort().toInt()
                    if ((flag and 8) != 0) layer4[i] = mapBuffer.getShort().toInt()

                    if ((flag and 16) != 0) trigger[i] = mapBuffer.getShort().toInt()

                    flag = infBuffer.get().toInt()

                    if((flag and 1) != 0) {
                        exitTo[i] = Triple(
                            infBuffer.getShort().toInt(), //Map
                            infBuffer.getShort().toInt(), //X
                            infBuffer.getShort().toInt()) //Y
                    }

                    if ((flag and 2) != 0) {
                        npcId[i] = infBuffer.getShort().toInt()

                        if (npcId[i] > 0) {
                            //TODO: init NPC
                        }
                    }

                    if ((flag and 4) != 0) {
                        //TODO: objects
                        infBuffer.getShort()
                        infBuffer.getShort()
                    }
                }
            }
        }.getOrThrow()

        return mapData
    }

    private fun mapFile(path: String): ByteBuffer {
        RandomAccessFile(path, "r").use { file ->
            file.channel.use { channel ->
                return channel
                    .map(FileChannel.MapMode.READ_ONLY, 0, channel.size())
                    .order(ByteOrder.LITTLE_ENDIAN)
            }
        }
    }
}