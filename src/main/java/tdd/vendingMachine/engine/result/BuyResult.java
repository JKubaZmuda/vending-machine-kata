package tdd.vendingMachine.engine.result;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.product.Product;

import java.util.Map;

public class BuyResult {

    private final boolean success;
    private final String message;
    private final Product product;
    private final Map<Coin, Integer> change;

    public BuyResult(boolean success, String message, Product product, Map<Coin, Integer> change) {
        this.success = success;
        this.message = message;
        this.product = product;
        this.change = change;
    }

    public Product getProduct() {
        return product;
    }

    public Map<Coin, Integer> getChange() {
        return change;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
