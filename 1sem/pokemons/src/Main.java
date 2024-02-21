import mypokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    int qwe;
    int ewq;
    static {
        System.out.println("Static");

    }

    {
        System.out.println(qwe);
        System.out.println("Non-static block");
    }
    public Main(int q) {
        this.qwe = q;
        System.out.println(qwe);
        System.out.println(ewq);
    }
    public static void main(String[] args) {
        Main main = new Main(123);

//        Battle b = new Battle();
//        b.addAlly(new Sealeo("Силео", 1));
//        b.addAlly(new Skarmory("Скармори", 1));
//        b.addAlly(new Houndour("Хандур", 1));
//        b.addFoe(new Oddish("Оддиш", 1));
//        b.addFoe(new Gloom("Глум", 1));
//        b.addFoe(new Totodile("Тотодил", 1));
//
//        Sealeo s = new Sealeo("name", 1);
//        Pokemon s2=  new Sealeo("name", 1);
//
//        b.go();
    }
}