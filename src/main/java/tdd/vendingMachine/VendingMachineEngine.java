package tdd.vendingMachine;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Shelf;
import tdd.vendingMachine.result.BuyResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VendingMachineEngine implements VendingMachine {

    public VendingMachineEngine() {

    }

    public VendingMachineEngine(List<Shelf> shelves) {

    }

    public VendingMachineEngine(List<Shelf> shelves, Map<Coin, Integer> initialCoins) {

    }

    @Override
    public void insertCoin(Coin coin) {

    }

    @Override
    public List<Shelf> getShelves() {
        return null;
    }

    @Override
    public MoneyUnit getBalance() {
        return null;
    }

    @Override
    public boolean isAbleToEject(MoneyUnit amount) {
        return false;
    }

    @Override
    public Map<Coin, Integer> cancel() {
        return null;
    }

    @Override
    public Optional<BuyResult> buy(int shelfNumber) {
        return null;
    }

}
