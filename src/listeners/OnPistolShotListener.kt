package listeners

import Player

interface OnPistolShotListener {
    fun onPistolShot(caster: Player)
}