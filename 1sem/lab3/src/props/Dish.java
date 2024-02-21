package props;


import food.Food;

import java.util.Arrays;

public class Dish extends Prop {
    private Food[] foodItems;
    private int itemCount;

    public Dish(String name, int maxItemCount) {
        super(name);
        this.foodItems = new Food[maxItemCount];
        this.itemCount = 0;
    }

    public void addFood(Food foodItem) {
        if (itemCount < foodItems.length) {
            foodItems[itemCount] = foodItem;
            itemCount++;
        } else {
            System.out.println("На блюде больше нет места.");
        }
    }

    public Food[] getFoodItems() {
        return foodItems;
    }
}