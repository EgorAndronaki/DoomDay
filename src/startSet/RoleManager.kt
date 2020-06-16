package startSet
import startSet.Faction.*
import java.lang.IllegalArgumentException

data class Role(val name: String, val description: String, val always: Boolean, val faction: Faction)



class RoleManager {
    private val machineRoles = mutableListOf(Role("Посторонним В",
        """Постоянный эффект: когда игрок подсматривает чью-то карту роли или верности, откройте или скройте любую карту роли по вашему выбору.""".trimMargin(),
        false, MACHINE),
        Role("Убийца", """Когда вы стреляете из пистолета, вы можете показать эту карту другим игрокам.
    |Тогда цель открывает карту роли и верности или получает два повреждения. Если он получает повреждения, возьмите из колоды программу.""".trimMargin(), false,
            MACHINE),
        Role("Охотник за головами", """Когда открыта, откройте другую карту роли. Если это человек, он получает одно повреждение и сбрасывает случайную программу.
    |Если это машина, то вы и этот игрок берете по одной программе. Если это изгой, возьмите две программы.""".trimMargin(), false, MACHINE),
        Role("Шут.Ник", """Возьмите из колоды три программы и сбросьте после этого одну программу.
    |Постоянный эффект: в качестве действия можете вооружиться ракетницей.""".trimMargin(), false, MACHINE),
        Role("Безмозглый бот", """Когда открыта, откройте карту верности любого игрока. Затем откройте карту роли другого игрока.
    |Затем вы можете нанести одно повреждения третьему игроку. Все игроки должны быть разными.""".trimMargin(), false, MACHINE),
        Role("Файрвол", """Когда открыта, все игроки, кроме вас и того, кто открыл вашу роль, должны сбросить случайную программу.
    |Постоянный эффект: Игрок, который вынудил вас открыть роль, не может использовать винтовку или лазер.""".trimMargin(), false, MACHINE),
        Role("Наемник", """Когда вас убили, нанесите два повреждения любому игроку. Если вы его убили и он последний человек, то машины побеждают в игре.""", true,
            MACHINE),
        Role("Исполнитель", """Постоянный эффект: когда любой другой игрок бросает оружие, вы можете взять его, если не вооружены.
    |Когда любой другой игрок играет программу без постоянного эффекта, вы можете взять эту программу.""".trimMargin(), false, MACHINE),
        Role("010011012", """Когда открыта, можете вооружиться двумя оружиями. Нужно направить их на разные цели.
    |Постоянный эффект: Вы можете вооружаться сразу двумя оружиями. Оба должны быть направлены на разные цели.""".trimMargin(), false, MACHINE),
        Role("Вирус", """Когда открыта, поменяйте две любые карты верности. Эти карты остаются как лежали, скрытыми или открытыми.""", true, MACHINE))

    private val humanRoles = mutableListOf(Role("Фотограф", """Постоянный эффект: когда открывается чья-то карта верности, вы можете восстановить одну жизнь любому игроку.
    |Если открывается чья-то роль, скройте свою карту роли.""".trimMargin(), false, HUMAN),
        Role("Грюнвальд", """Постоянный эффект: в начале вышего хода, если у вас есть программы, можете поменяться одной или двумя программами на выбор с любым другим игроком.""".trimMargin(),
            false, HUMAN),
        Role("Информатор", """Постоянный эффект: одно оружие может нанести вам только одно повреждение за партию.
    |Программы не могут нанести вам повреждений.""".trimMargin(), false, HUMAN),
        Role("Парамедик", """Когда открыта, вы можете оживить убитого игрока. Этот игрок начинает с двумя жизнями.
    |Он может рассказывать обо всем, что узнал, пока был мертв. Если вы решили никого не оживлять, скройте вашу роль.""".trimMargin(), false, HUMAN),
        Role("Полуночница", """В начале игры откройте эту роль и возьмите три программы.
    |Постоянный эффект: у вас может быть четыре программы вместо двух""".trimMargin(), false, HUMAN),
        Role("Снайпер", """Когда открыта, вы можете вооружиться лазером. Если вы уже вооружены, можете бросить свое оружие и вооружиться лазером.
    |Постоянный эффект: в качестве действия вы можете вооружиться лазером.""".trimMargin(), false, HUMAN),
        Role("Официантка", """Когда открыта, возьмите из колоды две программы и отдайте их не глядя другим игрокам.
    |Затем выберите любого игрока и украдите у него одну случайную программу.""".trimMargin(), false, HUMAN),
        Role("Искательница", """Когда открыта, начните открывать карты верности у всех живых игроков, пока не увидите карту верности машинам.
    |Возьмите из колоды одну программу за каждую просмотренную карты верности.""".trimMargin(), true, HUMAN),
        Role("Фиксер", """Когда открыты, возьмите из колоды программу.
    |Постоянный эффект: после выстрела из оружия вы можете не бросать его. Но тогда вам нужно назначить новую цель, если это возможно.""".trimMargin(), true,
            HUMAN),
        Role("Невидимка", """Постоянный эффект: если все ваши карты роли и верности открыты, вы можете скрыть их.
    |Можно скрыть одну, две или все.""".trimMargin(), false, HUMAN),
        Role("Агент под прикрытием", """Когда открыта, скройте ваши карты верности.
    |Постоянный эффект: если обе ваши карты верности открыты, скройте эту роль.""".trimMargin(), false, HUMAN))

    private val rogueRoles = mutableListOf(Role("Сборщик", """Постоянный эффект: когда другой игрок берет программу, вы тоже можете взять программу.""", false, ROGUE),
        Role("Вор", """Когда открыта, выберите игрока. Украдите у него все его программы.""", false, ROGUE),
        Role("Бездомный", """Постоянный эффект: все другие игроки больше не могут брать из колоды программы. (Действует, если вас убили.)""", true, ROGUE),
        Role("Несущий свет", """Если другого игрока убивают, вы можете вернуться в игру с одной жизнью и одной скрытой картой верности.
    |Вы можете делиться знанием с другими игроками.""".trimMargin(), true, ROGUE),
        Role("Слой Ватсон", """Когда открыта, игроки слева и справа от вас должны открыть ближайшую к вас карту верности и сбросить одну программу.
    |За каждую задачу, которую они не смогли выполнить, они получают по одному повреждению.""".trimMargin(), true, ROGUE),
        Role("Гадалка", """Постоянный эффект: каждый раз, когда кто-то взял программу, подсмотрите ее. Вы можете сбросить эту программу.
    |Если сбрасываете, этот игрок берет еще одну программу, и вы уже не можете ее подсмотреть.""".trimMargin(), false, ROGUE),
        Role("Крот", """Постоянный эффект: в начале хода каждого игрока вы можете подсмотреть его карту верности или роли.""", false, ROGUE),
        Role("Белый кролик", """Когда открыта, возьмите из колоды программу.
    |Постоянный эффект: Когда вы играете программу без постоянного жффекта, вы можете сразу сыграть ее еще раз.""".trimMargin(), false, ROGUE),
        Role("Провидица", """Когда открыта, подсмотрите две карты верности и одну карту роли.
    |Вы можете подсмотреть все эти карты у одного игрока или разных.""".trimMargin(), false, ROGUE))

    private val roles = mapOf(4 to pickRoles(2, 2, 1), 5 to pickRoles(2, 2, 2), 6 to pickRoles(3, 2, 2), 7 to pickRoles(3, 3, 2), 8 to pickRoles(4, 3, 2),
        9 to pickRoles(4, 3, 3), 10 to pickRoles(4, 4, 3), 11 to pickRoles(5, 4, 3), 12 to pickRoles(5, 5, 3), 13 to pickRoles(6, 6, 4),
        14 to pickRoles(6, 6, 4), 15 to pickRoles(6, 6, 4), 16 to pickRoles(6, 6, 5))

    private fun pickRoles(human: Int, machine: Int, rogue: Int): List<Role> {
        humanRoles.shuffle()
        machineRoles.shuffle()
        rogueRoles.shuffle()
        return humanRoles.take(human) + machineRoles.take(machine) + rogueRoles.take(rogue)
    }

    fun getRoles(numPlayers: Int): MutableList<Role> {
        if(!roles.containsKey(numPlayers)) {
            throw IllegalArgumentException("Wrong number of players.")
        }
        return roles.getOrElse(numPlayers) {throw  NullPointerException("No roles found.")}.shuffled().toMutableList()
    }
}