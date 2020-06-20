package listeners

import Player

interface OnShotListener {
    fun onShot(caster: Player, targets: List<Player>)
}