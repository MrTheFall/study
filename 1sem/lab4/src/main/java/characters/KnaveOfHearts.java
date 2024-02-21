package characters;

import characters.movement.Direction;

public class KnaveOfHearts extends Character {
    private Soldier guardLeft;
    private Soldier guardRight;

    public KnaveOfHearts(String name) {
        super(name);
    }

    // Вложенный нестатический класс Солдата
    public class Soldier extends Character {
        public Soldier(String name) {
            super(name);
        }

        public void guard() {
            System.out.println(getName() + " охраняет " + KnaveOfHearts.this.getName());
        }
    }

    // Методы для создания солдат-охранников
    public void assignGuardLeft(Soldier guard) {
        this.guardLeft = guard;
        guard.guard();
        guard.standBy(this, Direction.BY_LEFT_HAND);
    }

    public void assignGuardRight(Soldier guard) {
        this.guardRight = guard;
        guard.guard();
        guard.standBy(this, Direction.BY_RIGHT_HAND);
    }
}
