package tdd.vendingMachine.engine;

import tdd.vendingMachine.engine.result.BuyResult;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Shelf;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VendingMachine {

    void insertCoin(Coin coin);

    List<Shelf> getShelves();

    MoneyUnit getBalance();

    Map<Coin, Integer> cancel();

    BuyResult buy(int shelfNumber);

    Optional<Shelf> getShelfByNumber(int shelfNumber);
}
