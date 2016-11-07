package tdd.vendingMachine.money.pocket;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.Map;
import java.util.Optional;

public class SimpleCoinPocket implements CoinPocket {

    private CoinStorage storage;
    private MoneyUnit insertedMoney = new MoneyUnit("0");

    public SimpleCoinPocket(Map<Coin, Integer> initialCoins) {
        storage = new CoinStorage(initialCoins);
    }

    public SimpleCoinPocket() {
        storage = new CoinStorage();
    }

    @Override
    public void insertCoin(Coin coin) {
        storage.store(coin);
        insertedMoney = insertedMoney.sum(coin.getMoneyValue());
    }

    @Override
    public Optional<Map<Coin, Integer>> ejectMoney(MoneyUnit amount) {
        Optional<Map<Coin, Integer>> withdrawnCoins = storage.withdraw(amount);
        if (withdrawnCoins.isPresent()) {
            insertedMoney = insertedMoney.subtract(amount);
        }
        return withdrawnCoins;
    }

    @Override
    public MoneyUnit getInsertedMoney() {
        return insertedMoney;
    }

    @Override
    public boolean isAbleToEject(MoneyUnit amount) {
        return storage.isAbleToWithdraw(amount);
    }

    public void setStorage(CoinStorage storage) {
        this.storage = storage;
    }
}
