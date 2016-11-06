package tdd.vendingMachine.product;

import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.Optional;

public class Shelf {

    private final Product pattern;

    private int quantity;

    public Shelf(Product productType, int quantity) {
        this.pattern = productType;
        this.quantity = quantity;
    }

    public Optional<Product> obtainProduct() {
        if (quantity > 0) {
            --quantity;
            return Optional.of(new Product(pattern));
        }
        return Optional.empty();
    }

    public String getExposedProductName() {
        return pattern.getName();
    }

    public MoneyUnit getExposedProductPrice() {
        return pattern.getPrice();
    }

}
