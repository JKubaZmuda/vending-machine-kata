package tdd.vendingMachine;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Shelf;
import tdd.vendingMachine.result.BuyResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VendingMachine {

    void insertCoin(Coin coin);

    List<Shelf> getShelves();

    MoneyUnit getBalance();

    boolean isAbleToEject(MoneyUnit amount);

    Map<Coin, Integer> cancel();

    Optional<BuyResult> buy(int shelfNumber);

}
