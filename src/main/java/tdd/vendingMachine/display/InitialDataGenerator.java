package tdd.vendingMachine.display;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.product.Shelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialDataGenerator {

    public static Map<Coin, Integer> getInitialCoins() {
        Map<Coin, Integer> coins = new HashMap<>();
        coins.put(Coin.COIN_10, 10);
        coins.put(Coin.COIN_20, 10);
        coins.put(Coin.COIN_50, 10);
        coins.put(Coin.COIN_100, 10);
        coins.put(Coin.COIN_200, 10);
        coins.put(Coin.COIN_500, 10);
        return coins;
    }

    public static List<Shelf> getInitialShelves() {
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, new Product("cola", new MoneyUnit("4.50")), 1));
        shelves.add(new Shelf(2, new Product("chocolate bar", new MoneyUnit("3.20")), 1));
        shelves.add(new Shelf(3, new Product("mineral water", new MoneyUnit("1.70")), 1));
        shelves.add(new Shelf(4, new Product("candies", new MoneyUnit("1.10")), 1));
        shelves.add(new Shelf(5, new Product("skittles", new MoneyUnit("3.90")), 1));
        shelves.add(new Shelf(6, new Product("big chips", new MoneyUnit("11.60")), 1));
        return shelves;
    }

}
