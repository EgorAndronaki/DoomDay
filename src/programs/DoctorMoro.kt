package programs

import Player
import startSet.Time

class DoctorMoro(override val name: String, override val timeOfPlay: Time, override val description: String,
    override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        target!!.programList.shuffle()
        caster.programList.add(target.programList[0])
        target.programList.removeAt(0)
    }
}