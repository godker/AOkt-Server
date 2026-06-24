package com.godker.game.player

import com.godker.game.City
import com.godker.game.Class
import com.godker.game.Gender
import com.godker.game.Race
import kotlinx.serialization.Serializable

const val MAX_PETS: Int = 3

data class Player (
    var id: Int,
    val name: String,
    val mail: String,
    var char: Appearance,
    var origChar: Appearance,
    var description: String = "",
    var descRM: String = "", //TODO: para copiar desc, posiblemente no se use?
    val archetype: Class,
    val race: Race,
    val gender: Gender,
    var home: City,
    var position: Triple<Int, Int, Int>,
    val inventory: Inventory? = null,
    val charMimetized: Appearance? = null,
    val validConnectionId: Boolean = false, //TODO: ??
    val idConnection: Int = 0,
    val bankInventory: Inventory? = null,
    val crafting: Pair<Int, Int>? = null, //TODO: chequear cdo se implemente. Cantidad, Ciclo
    val pets: ArrayList<Pets>? = null,
    val counters: Counters,
    val stats: Stats,
    val flags: Flags,

    var privileges: PlayerType = PlayerType.USER,

    //TODO: reputation
    //TODO: factions

    var ip: String = "",

    //TODO: commerce

    var guildId: Int? = null,
    var partyId: Int? = null,
    )