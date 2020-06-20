package startSet
import roles.BaseRole
import roles.human.*
import roles.machine.*
import roles.rogue.*
import startSet.Faction.*
import java.lang.IllegalArgumentException
import kotlin.random.Random


class RoleManager {

    private val humanRoles = mutableListOf(UndercoverAgent("Агент под прикрытием", HUMAN, false, "Когда открыта, скройте ваши карты верности.",
        "Если обе ваши карты верности открыты, скройте эту роль.", true),
        Invisible("Невидимка", HUMAN, false, null, "Если все ваши карты роли и верности открыты, " +
            "можете скрыть их обратно. Можно скрыть одну, две или все.", true),
        Fixer("Фиксер", HUMAN, true, null, "После выстрела из оружия можете не бросать его. Но тогда вам нужно назначить новую цель.", true),
        Sniper("Снайпер", HUMAN, false, "Когда открыта, вы можете вооружиться лазером. Если вы уже вооружены, вы можете бросить свое оружие и взять лазер.",
            "В качестве действия можете вооружиться лазером.", true),
        Seeker("Искательница", HUMAN, true, "Когда открыта, начните открывать карты верности у всех живых игроков, пока не увидите карту верности машинам.", null, true),
        Informant("Информатор", HUMAN, false, null, "Вы можете получить лишь одно повреждение за раз.", true))

    private val machineRoles = mutableListOf(PostoronnimV("Посторонним В", MACHINE, false, null, "Когда игрок подсматривает чью-то карту роли или верности, " +
            "откройте или скройте карту роли по вашему выбору.", true),
        Merc("Наемник", MACHINE, true, "Когда вас убили, нанесите два повреждения любому игроку. Если вы его убили и он последний человек, то машины побеждают в игре.",
            null, true),
        Joker("Шут.Ник", MACHINE, false, null, "В качестве действия вы можете вооружиться ракетницей.", true),
        Executor("Исполнитель", MACHINE, false, null, "Когда любой другой игрок бросает оружие, вы можете взять его, если сейчас безоружны.", true),
        BrainlessBot("Безмозглый бот", MACHINE, false, """Когда открыта, откройте карту верности любого игрока. Затем откройте карту роли другого игрока.
    |Потом вы можете нанести одно повреждение третьему игроку. Все трое должны быть разными игроками.""".trimMargin(), null, true),
        Assassin("Убийца", MACHINE, false, null, """Когда вы стреляете из пистолета, вы можете показать эту карту другим игрокам. 
    |Тогда цель открывает карту верности и роль или получает два повреждения.""".trimMargin(), true))

    private val rogueRoles = mutableListOf(Thief("Вор", ROGUE, false, "Когда открыта, выберите игрока. Украдите у него оружие.", null, true),
        Seer("Провидица", ROGUE, false, """Когда открыта, подсмотрите две карты верности и одну карту роли.
            |Вы можете подсмотреть их у одного игрока или разных.""".trimMargin(), null, true),
        SloyWatson("Слой Ватсон", ROGUE, true, """Когда открыта, игроки слева и справа от вас должны открыть ближайшую к вам карту верности.
            |Иначе они получают по одному повреждению.""".trimMargin(), null, true),
        Mole("Крот", ROGUE, false, null, "В начале хода каждого игрока вы можете подсмотреть одну его карту верности или роли.", true),
        FortuneTeller("Гадалка", ROGUE, false, null, "Каждый раз, когда игрок вооружается, вы можете сбросить это оружие. Игрок пропускает этот ход.", true))

    private val roles = mapOf(4 to pickRoles(2, 2, 1), 5 to pickRoles(2, 2, 2), 6 to pickRoles(3, 2, 2), 7 to pickRoles(3, 3, 2), 8 to pickRoles(4, 3, 2),
        9 to pickRoles(4, 3, 3), 10 to pickRoles(4, 4, 3), 11 to pickRoles(5, 4, 3), 12 to pickRoles(5, 5, 3), 13 to pickRoles(6, 6, 4),
        14 to pickRoles(6, 6, 4), 15 to pickRoles(6, 6, 4), 16 to pickRoles(6, 6, 5))

    private fun pickRoles(human: Int, machine: Int, rogue: Int): List<BaseRole> {
        val shuffledHuman = humanRoles.shuffle()
        val shuffledMachine = machineRoles.shuffle()
        val shuffledRogue = rogueRoles.shuffle()
        return shuffledHuman.take(human) + shuffledMachine.take(machine) + shuffledRogue.take(rogue)
    }

    fun getRoles(numPlayers: Int): MutableList<BaseRole> {
        if(!roles.containsKey(numPlayers)) {
            throw IllegalArgumentException("Wrong number of players.")
        }
        println(roles)
        return roles.getOrElse(numPlayers) {throw  NullPointerException("No roles found.")}.shuffled().toMutableList()
    }
}

private fun MutableList<BaseRole>.shuffle(): MutableList<BaseRole> {
    val res = mutableListOf<BaseRole>()
    while(this.isNotEmpty()) {
        val current = this[Random.nextInt(0, size)]
        res.add(current)
        remove(current)
    }
    return res
}
