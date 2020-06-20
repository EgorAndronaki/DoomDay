package listeners

import Player

interface OnKilledListener {
    fun onKilled(caster: Player)
}