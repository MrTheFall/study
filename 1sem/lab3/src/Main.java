import characters.Alice;
import characters.King;
import characters.WhiteRabbit;
import characters.movement.Direction;
import food.Pie;
import props.*;
import furniture.Table;
import rooms.Courtroom;


public class Main {
    public static void main(String[] args) {
        System.out.println(new String("123") == new String("123"));
        System.out.println(new String("123") == "123");
        System.out.println(("12"+"3").intern() == "123");
        System.out.println(("12" + "3") == "123");
        Alice alice = new Alice("Алиса");
        King king = new King("Король");
        WhiteRabbit whiteRabbit = new WhiteRabbit("Белый Кролик");
        Courtroom courtroom = new Courtroom("Судебный зал");
        Table table = new Table("Стол");
        Dish dish = new Dish("Большое блюдо с пирожками", 10);
        dish.addFood(new Pie("Аппетитный пирожок", "Курица"));
        dish.addFood(new Pie("Аппетитный пирожок", "Рыба"));
        dish.addFood(new Pie("Аппетитный пирожок", "Картошка"));


        whiteRabbit.standBy(king, Direction.BY_RIGHT_HAND);
        whiteRabbit.hold(new Trumpet("Труба"));
        whiteRabbit.hold(new Scroll("Пергаментный свиток"));
        courtroom.setCenterObject(table);
        table.setTopObject(dish);
        dish.lookLike("аппетитно");
        alice.salivateAt(dish);
        alice.think("Хорошо бы, суд уже кончился и позвали к столу!");
        //alice.walkTo(Direction.CENTER);
    }
}