package com.godker.game.player

import com.godker.game.Gender
import com.godker.game.Heading
import com.godker.game.Race
import kotlinx.serialization.Serializable

//TODO: this is awful
const val MALE_HUMAN_FIRST_HEAD = 1
const val MALE_HUMAN_LAST_HEAD = 40
const val MALE_ELF_FIRST_HEAD = 101
const val MALE_ELF_LAST_HEAD = 122
const val MALE_DROW_FIRST_HEAD = 201
const val MALE_DROW_LAST_HEAD = 221
const val MALE_GNOME_FIRST_HEAD = 401
const val MALE_GNOME_LAST_HEAD = 416
const val MALE_DWARF_FIRST_HEAD = 301
const val MALE_DWARF_LAST_HEAD = 319

const val FEMALE_HUMAN_FIRST_HEAD = 70
const val FEMALE_HUMAN_LAST_HEAD = 89
const val FEMALE_ELF_FIRST_HEAD = 170
const val FEMALE_ELF_LAST_HEAD = 188
const val FEMALE_DROW_FIRST_HEAD = 270
const val FEMALE_DROW_LAST_HEAD = 288
const val FEMALE_GNOME_FIRST_HEAD = 470
const val FEMALE_GNOME_LAST_HEAD = 484
const val FEMALE_DWARF_FIRST_HEAD = 370
const val FEMALE_DWARF_LAST_HEAD = 384

@Serializable
data class Appearance(
    val charIndex: Int = 0, //TODO: no idea
    val head: Int = 0,
    val body: Int = 0,
    val weapon: Int = 0,
    val shield: Int = 0,
    val helmet: Int = 0,
    val fx: Int = 0,
    val loops: Int = 0,
    val heading: Heading = Heading.SOUTH,
){
    companion object {
        fun default(race: Race, gender: Gender, head: Int): Appearance? {
            val (validHead, body) =
                when (race) {
                    Race.HUMAN -> when (gender) {
                        Gender.MALE -> (head in MALE_HUMAN_FIRST_HEAD..MALE_HUMAN_LAST_HEAD) to 21
                        Gender.FEMALE -> (head in FEMALE_HUMAN_FIRST_HEAD..FEMALE_HUMAN_LAST_HEAD) to 39
                    }

                    Race.ELF -> when (gender) {
                        Gender.MALE -> (head in MALE_ELF_FIRST_HEAD..MALE_ELF_LAST_HEAD) to 210
                        Gender.FEMALE -> (head in FEMALE_ELF_FIRST_HEAD..FEMALE_ELF_LAST_HEAD) to 259
                    }

                    Race.DROW -> when (gender) {
                        Gender.MALE -> (head in MALE_DROW_FIRST_HEAD..MALE_DROW_LAST_HEAD) to 32
                        Gender.FEMALE -> (head in FEMALE_DROW_FIRST_HEAD..FEMALE_DROW_LAST_HEAD) to 40
                    }

                    Race.GNOME -> when (gender) {
                        Gender.MALE -> (head in MALE_GNOME_FIRST_HEAD..MALE_GNOME_LAST_HEAD) to 222
                        Gender.FEMALE -> (head in FEMALE_GNOME_FIRST_HEAD..FEMALE_GNOME_LAST_HEAD) to 260
                    }

                    Race.DWARF -> when (gender) {
                        Gender.MALE -> (head in MALE_DWARF_FIRST_HEAD..MALE_DWARF_LAST_HEAD) to 53
                        Gender.FEMALE -> (head in FEMALE_DWARF_FIRST_HEAD..FEMALE_DWARF_LAST_HEAD) to 60
                    }
                }

            return if (!validHead)
                null
            else
                Appearance(head = head, body = body)
        }
    }
}
