package com.godker.game.player

import kotlinx.serialization.Serializable

const val MAX_NORMAL_INVENTORY_SLOTS: Int = 20
const val MAX_INVENTORY_SLOTS: Int = 30

@Serializable
data class InventoryObject(val objId: Int, val quantity: Int = 0, val equipped: Boolean = false)

@Serializable
class Inventory(private val size: Int) {
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

    var limit: Int = size
        private set

    //TODO: profiling. que tanto se usa?
    val amount: Int
        get() = objects.count { it.objId > 0 && it.quantity > 0 }

    /**
     * Adds an [InventoryObject] to the first available slot.
     * @return The index of the slot used. -1 if no available slot.
     */
    fun add(obj: InventoryObject): Int {
        val firstEmpty = objects.indexOfFirst { it.objId == 0 }

        if (firstEmpty != -1 && firstEmpty < limit)
            objects[firstEmpty] = obj

        return firstEmpty
    }

    /**
     * @return [InventoryObject] at the specified slot (even if its empty).
     */
    operator fun get(slot: Int): InventoryObject = objects[validateSlot(slot)]

    /**
     * @return readonly array containing the whole inventory.
     */
    fun getAll(): List<InventoryObject> = objects.toList()

    fun updateInventoryLimit(bagType: Int) {
        limit = if (bagType > 0) MAX_NORMAL_INVENTORY_SLOTS + bagType * 5 else MAX_NORMAL_INVENTORY_SLOTS
    }

    private fun validateSlot(slot: Int): Int =
        if (slot in 0..< limit) slot
        else throw IndexOutOfBoundsException("Slot $slot not in 0..$limit")
}