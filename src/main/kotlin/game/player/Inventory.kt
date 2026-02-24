package com.godker.game.player

import kotlinx.serialization.Serializable

@Serializable
data class InventoryObject(val objId: Int, val quantity: Int = 1, val equipped: Boolean = false)

@Serializable
data class Inventory(val size: Int) {
    private val objects: Array<InventoryObject> = Array(size) { InventoryObject(0) }

    //TODO: profiling. que tanto se usa?
    val amount: Int
        get() = objects.count { it.objId > 0 && it.quantity > 0 }

    /**
     * Adds an [InventoryObject] to the first available slot.
     * @return false if no available slot.
     */
    fun add(obj: InventoryObject): Boolean {
        val firstEmpty = objects.indexOfFirst { it.objId == 0 }

        return if (firstEmpty == -1) {
            false
        } else {
            objects[firstEmpty] = obj
            true
        }
    }

    /**
     * Sets an [InventoryObject] at a specific [slot]
     */
    fun set(slot: Int, obj: InventoryObject) {
        objects[validateSlot(slot)] = obj
    }

    /**
     * @return [InventoryObject] at the specified slot (even if its empty).
     */
    fun get(slot: Int): InventoryObject = objects[validateSlot(slot)]

    private fun validateSlot(slot: Int): Int =
        if (slot in 0..< size) slot
        else throw IndexOutOfBoundsException("Slot $slot not in 0..$size")
}