import characters.*;
import characters.Character;
import characters.groups.*;
import characters.movement.*;
import exceptions.*;
import food.*;
import furniture.*;
import props.*;
import rooms.*;


public class Main {
    public static int methodTest() {
        try {
            throw new Exception("");
        } catch (Throwable e) {
            return 2;
        } finally {
            return 3;
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println(methodTest());

        Queen queen = new Queen("Королева");
        King king = new King("Король");
        Alice alice = new Alice("Алиса");
        Gryphon gryphon = new Gryphon("Грифон");
        KnaveOfHearts knaveOfHearts = new KnaveOfHearts("Червонный Валет");
        Furniture kingsThrone = new Throne("Трон");
        Furniture queensThrone = new Throne("Трон");
        king.sitOn(kingsThrone);
        queen.sitOn(queensThrone);

        int retryCount = 3; // Количество попыток для прибытия
        // Checked exception
        for (Character character : new Character[]{alice, gryphon}) {
            for (int i = 0; i < retryCount; i++) {
                try {
                    character.arrive();
                    break;
                } catch (ArrivalException e) {
                    // System.err.println(e.getMessage());
                    if (i < retryCount - 1) {
                        System.out.println(character.getName() + " ещё раз пытается найти путь");
                    } else {
                        System.out.println(character.getName() + " так и не смог прибежать");
                        System.exit(1);

                    }
                }
            }
        }


        // Толпа
        Crowd crowd = new Crowd();
        // Unchecked exception
        try {
            // Добавление участников толпы
            crowd.addMember(new Crowd.Member("Пичужка 1"));
            crowd.addMember(new Crowd.Member("Пичужка 2"));
            crowd.addMember(new Crowd.Member("Зверушка 1"));
            crowd.addMember(new Crowd.Member("Зверушка 2"));
            crowd.addMember(new Crowd.Member("Карта Валет"));
            crowd.addMember(new Crowd.Member("Карта Туз"));
            // Сбор толпы
            crowd.gather();
        } catch (CantAttendException e) {
            System.err.println(e.getMessage());
            System.err.println("В толпу был добавлен запрещённый участник. Выполнение программы завершено.");
            System.exit(1);
        }
        // Охрана
        KnaveOfHearts.Soldier guardLeft = knaveOfHearts.new Soldier("Левый солдат");
        KnaveOfHearts.Soldier guardRight = knaveOfHearts.new Soldier("Правый солдат");

        knaveOfHearts.assignGuardLeft(guardLeft);
        knaveOfHearts.assignGuardRight(guardRight);

        //=========================================================================
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
        //=========================================================================

        // Алиса
        alice.observeScene(new String[]{"судья", "трон", "зверюшки", "пичужки", "карты"});
        alice.think("Вот это судья. Кто в большом парике, тот и судья");
        // Анонимный класс рассказчика
        Character narrator = new Character("Рассказчик") {
            @Override
            public void say(String speach) {
                System.out.println(speach);
            }
        };
        narrator.say("Хотя Алиса раньше никогда не бывала в суде, она устала про суд в книжках, " +
                "и ей было очень приятно отметить, что она знает, как тут все - или почти все - называется.");

    }

}