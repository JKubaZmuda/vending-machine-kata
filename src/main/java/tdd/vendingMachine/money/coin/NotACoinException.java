package tdd.vendingMachine.money.coin;

public class NotACoinException extends Exception {

    @Override
    public String getMessage() {
        return "Could not construct a Coin for invalid value.";
    }
}
