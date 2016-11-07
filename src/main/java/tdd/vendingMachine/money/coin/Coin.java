package tdd.vendingMachine.money.coin;

import tdd.vendingMachine.money.unit.MoneyUnit;

public enum Coin {

    COIN_500(new MoneyUnit("5.0")),
    COIN_200(new MoneyUnit("2.0")),
    COIN_100(new MoneyUnit("1.0")),
    COIN_50(new MoneyUnit("0.5")),
    COIN_20(new MoneyUnit("0.2")),
    COIN_10(new MoneyUnit("0.1"));

    private final MoneyUnit value;

    Coin(MoneyUnit value) {
        this.value = value;
    }

    public static Coin of(String value) throws NotACoinException {
        switch (value) {
            case "5":
            case "5.0":
                return COIN_500;
            case "2":
            case "2.0":
                return COIN_200;
            case "1":
            case "1.0":
                return COIN_100;
            case "0.5":
                return COIN_50;
            case "0.2":
                return COIN_20;
            case "0.1":
                return COIN_10;
            default:
                throw new NotACoinException();
        }
    }

    @Override
    public String toString() {
        return "Coin [" + value.toString() + "]";
    }

    public MoneyUnit getMoneyValue() {
        return value;
    }
}
