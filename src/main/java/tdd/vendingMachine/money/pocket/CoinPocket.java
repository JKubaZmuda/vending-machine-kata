package tdd.vendingMachine.money.pocket;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.Map;
import java.util.Optional;

public interface CoinPocket {

    void insertCoin(Coin coin);

    MoneyUnit getInsertedMoney();

    Optional<Map<Coin, Integer>> ejectMoney(MoneyUnit amount);
}
