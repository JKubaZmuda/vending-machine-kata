package tdd.vendingMachine.engine;

import tdd.vendingMachine.engine.result.BuyResult;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.pocket.CoinPocket;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.product.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VendingMachineEngine implements VendingMachine {

    public static final String WRONG_SHELF_NUMBER = "Wrong shelf number.";
    public static final String UNABLE_TO_EJECT_CHANGE = "Unable to eject change.";
    public static final String NO_SUCH_PRODUCTS_LEFT = "There are no such products left.";
    public static final String INSUFFICIENT_FUNDS = "Inserted not enough money.";
    public static final String SUCCESS = "Success.";


    private final CoinPocket coinPocket;
    private final List<Shelf> shelves;

    public VendingMachineEngine(CoinPocket coinPocket) {
        this.coinPocket = coinPocket;
        this.shelves = new ArrayList<>();
    }

    public VendingMachineEngine(CoinPocket coinPocket, List<Shelf> shelves) {
        this.coinPocket = coinPocket;
        this.shelves = shelves;
    }

    @Override
    public void insertCoin(Coin coin) {
        coinPocket.insertCoin(coin);
    }

    @Override
    public List<Shelf> getShelves() {
        return new ArrayList<>(shelves);
    }

    @Override
    public MoneyUnit getBalance() {
        return coinPocket.getInsertedMoney();
    }

    @Override
    public Map<Coin, Integer> cancel() {
        return coinPocket.ejectMoney(getBalance()).orElseThrow(IllegalStateException::new);
    }

    @Override
    public BuyResult buy(int shelfNumber) {
        Optional<Shelf> chosenShelf = getShelfByNumber(shelfNumber);

        if (!chosenShelf.isPresent()) {
            return new BuyResult(false, WRONG_SHELF_NUMBER, null, null);
        }

        Shelf shelf = chosenShelf.get();
        if (getBalance().compareTo(shelf.getExposedProductPrice()) < 0) {
            return new BuyResult(false, INSUFFICIENT_FUNDS, null, null);
        }

        MoneyUnit potentialChange = getBalance().subtract(shelf.getExposedProductPrice());
        if (!coinPocket.isAbleToEject(potentialChange)) {
            return new BuyResult(false, UNABLE_TO_EJECT_CHANGE, null, null);
        }

        Optional<Product> product = shelf.obtainProduct();
        if (!product.isPresent()) {
            return new BuyResult(false, NO_SUCH_PRODUCTS_LEFT, null, null);
        }

        Map<Coin, Integer> actualChange = coinPocket.ejectMoney(potentialChange).orElseThrow(IllegalStateException::new);
        coinPocket.resetInsertedMoney();

        if (shelf.getQuantity() <= 0) {
            shelves.remove(shelf);
        }

        return new BuyResult(true, SUCCESS, product.get(), actualChange);
    }

    @Override
    public Optional<Shelf> getShelfByNumber(int shelfNumber) {
        return Optional.of(shelves.stream()
            .filter(p -> p.getShelfNumber() == shelfNumber)
            .findAny())
            .orElse(Optional.empty());
    }

}
