package programs

import Player
import startSet.Time

interface BaseProgram {
    val name: String
    val timeOfPlay: Time
    val description: String
    val const: Boolean
    fun execute(caster: Player, target: Player? = null, targets: List<Player>? = null)
}