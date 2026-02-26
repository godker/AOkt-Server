package com.godker.game.player

import kotlinx.serialization.Serializable

@Serializable
data class InventoryObject(val objId: Int, val quantity: Int = 1, val equipped: Boolean = false)

@Serializable
class Inventory(val size: Int) {
    private val objects: Array<InventoryObject> = Array(size) { InventoryObject(0) }

    var weaponObjId: Int = 0
    var weaponSlot: Int = 0
    var armourObjId: Int = 0
    var armourSlot: Int = 0
    var shieldObjId: Int = 0
    var shieldSlot: Int = 0
    var helmetObjId: Int = 0
    var helmetSlot: Int = 0
    var ammoObjId: Int = 0
    var ammoSlot: Int = 0
    var ringObjId: Int = 0
    var ringSlot: Int = 0
    var boatObjId: Int = 0
    var boatSlot: Int = 0
    var bagObjId: Int = 0
    var bagSlot: Int = 0

    //TODO: profiling. que tanto se usa?
    val amount: Int
        get() = objects.count { it.objId > 0 && it.quantity > 0 }

    /**
     * Adds an [InventoryObject] to the first available slot.
     * @return The index of the slot used. -1 if no available slot.
     */
    fun add(obj: InventoryObject): Int {
        val firstEmpty = objects.indexOfFirst { it.objId == 0 }

        if (firstEmpty != -1)
            objects[firstEmpty] = obj

        return firstEmpty
    }

    /**
     * Sets an [InventoryObject] at a specific [slot]
     */
    fun set(slot: Int, obj: InventoryObject) {
        //TODO: no estoy seguro de que tanto se deba usar, es mejor usar sólo add()
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