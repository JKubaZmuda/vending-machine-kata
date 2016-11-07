package tdd.vendingMachine.result;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.product.Product;

import java.util.Map;

public class BuyResult {

    private final Product product;
    private final Map<Coin, Integer> change;

    public BuyResult(Product product, Map<Coin, Integer> change) {
        this.product = product;
        this.change = change;
    }

    public Product getProduct() {
        return product;
    }

    public Map<Coin, Integer> getChange() {
        return change;
    }
}
