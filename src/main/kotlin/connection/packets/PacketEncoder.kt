package com.godker.connection.packets

import com.godker.writeVBString
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.nio.ByteOrder

class PacketEncoder : MessageToByteEncoder<OutgoingPacket>() {

    override fun encode(ctx: ChannelHandlerContext, packet: OutgoingPacket, data: ByteBuf) {
        val buf = data.order(ByteOrder.LITTLE_ENDIAN)

        //Packet Id
        buf.writeByte(packet.packetId.ordinal)

        when (packet) {
            is LoggedOk -> {
                buf.writeByte(packet.archetype.ordinal)
            }
            is DiceRoll -> {
                buf.writeByte(packet.stats[0])
                buf.writeByte(packet.stats[1])
                buf.writeByte(packet.stats[2])
                buf.writeByte(packet.stats[3])
                buf.writeByte(packet.stats[4])
            }
            is SendUserIdInServer -> {
                buf.writeShort(packet.playerId)
            }
            is SendChangeMap -> {
                buf.writeShort(packet.map)
                buf.writeShort(packet.version)
            }
            is InventoryUpdate -> {
                buf.writeByte(packet.slot)
                buf.writeShort(packet.item.objId)
                buf.writeVBString(packet.objInfo.name)
                buf.writeShort(packet.item.quantity)
                buf.writeBoolean(packet.item.equipped)
                buf.writeShort(packet.objInfo.grhId ?: 0)
                buf.writeByte(packet.objInfo.type.id)
                buf.writeShort(packet.objInfo.maxHit ?: 0)
                buf.writeShort(packet.objInfo.minHit ?: 0)
                buf.writeShort(packet.objInfo.maxDefense ?: 0)
                buf.writeShort(packet.objInfo.minDefense ?: 0)
                buf.writeInt(packet.objInfo.saleValue)
            }
            is SpellListUpdate -> {
                buf.writeByte(packet.slot)
                buf.writeShort(packet.spell)
                buf.writeVBString(packet.spellName)
            }
            is ParalyzeOk -> {}

            is SendMultiMessage -> {
                val msgId = packet.messageId
                buf.writeByte(msgId.ordinal)

                when (msgId) {
                    MessageType.NPC_HIT_USER -> {
                        buf.writeByte(packet.args?.getOrNull(0) as? Int ?: 0) //Target
                        buf.writeShort(packet.args?.getOrNull(1) as? Int ?: 0) //Damage
                    }

                    MessageType.USER_HIT_NPC -> {
                        buf.writeInt(packet.args?.getOrNull(0) as? Int ?: 0) //Damage
                    }

                    MessageType.USER_ATTACKED_SWING -> {
                        buf.writeShort(packet.args?.getOrNull(0) as? Int ?: 0) //CharIndex
                    }

                    MessageType.USER_HITTED_BY_USER, MessageType.USER_HITTED_USER -> {
                        buf.writeShort(packet.args?.getOrNull(0) as? Int ?: 0) //AttackerId
                        buf.writeByte(packet.args?.getOrNull(1) as? Int ?: 0) //Target
                        buf.writeShort(packet.args?.getOrNull(2) as? Int ?: 0) //Damage
                    }

                    MessageType.WORK_REQUEST_TARGET -> {
                        buf.writeByte(packet.args?.getOrNull(0) as? Int ?: 0) //SkillId
                    }

                    MessageType.HAVE_KILLED_USER -> {
                        buf.writeShort(packet.args?.getOrNull(0) as? Int ?: 0) //Victim char index
                        buf.writeInt(packet.args?.getOrNull(1) as? Int ?: 0) //XP
                    }

                    MessageType.USER_KILL -> {
                        buf.writeShort(packet.args?.getOrNull(0) as? Int ?: 0) //Attacker char index
                    }

                    MessageType.HOME -> {
                        buf.writeByte(packet.args?.getOrNull(0) as? Int ?: 0)
                        buf.writeShort(packet.args?.getOrNull(1) as? Int ?: 0)
                        buf.writeVBString(packet.args?.getOrNull(2) as? String ?: "")
                    }

                    else -> {}
                }
            }
        }
    }
}