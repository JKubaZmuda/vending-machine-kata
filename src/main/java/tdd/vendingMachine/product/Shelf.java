package tdd.vendingMachine.product;

import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.Optional;

public class Shelf {

    private final Product pattern;
    private final int number;
    private int quantity;

    public Shelf(int number, Product productType, int quantity) {
        this.number = number;
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

    public int getShelfNumber() {
        return number;
    }
}
