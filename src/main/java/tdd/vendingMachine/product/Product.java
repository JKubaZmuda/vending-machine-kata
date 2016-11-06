package tdd.vendingMachine.product;

import tdd.vendingMachine.money.unit.MoneyUnit;

public class Product {

    private final String name;

    private final MoneyUnit price;

    public Product(String name, MoneyUnit price) {
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public String getName() {
        return name;
    }

    public MoneyUnit getPrice() {
        return price;
    }
}
