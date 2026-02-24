package com.godker

import io.netty.buffer.ByteBuf

fun ByteBuf.readVBString(): String {
    val length = readShortLE().toInt()
    val bytes = ByteArray(length)
    readBytes(bytes)
    return String(bytes, Charsets.ISO_8859_1)
}

fun ByteBuf.writeVBString(value: String) {
    val bytes = value.toByteArray(Charsets.ISO_8859_1)
    writeShortLE(bytes.size)
    writeBytes(bytes)
}