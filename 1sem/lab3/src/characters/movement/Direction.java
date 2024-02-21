package characters.movement;

public enum Direction {
    CENTER("в центр"),
    LEFT("налево"),
    RIGHT("направо"),
    UP("вверх"),
    DOWN("вниз"),
    FRONT("вперед"),
    BACK("назад"),
    RIGHT_SIDE("с правой стороны от"),
    LEFT_SIDE("с левой стороны от"),
    BY_RIGHT_HAND("по правую руку от"),
    BY_LEFT_HAND("по левую руку от"),
    BEHIND("сзади"),
    AHEAD("спереди");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

}