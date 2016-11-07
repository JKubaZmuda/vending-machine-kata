package tdd.vendingMachine.display;

import tdd.vendingMachine.engine.VendingMachine;
import tdd.vendingMachine.engine.VendingMachineEngine;
import tdd.vendingMachine.engine.result.BuyResult;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.coin.NotACoinException;
import tdd.vendingMachine.money.pocket.SimpleCoinPocket;
import tdd.vendingMachine.product.Shelf;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static tdd.vendingMachine.display.InitialDataGenerator.getInitialCoins;
import static tdd.vendingMachine.display.InitialDataGenerator.getInitialShelves;

public class VendingMachineDisplay {

    private static final String machineName = "Ultimate Vending Machine 9000";
    private static final String separator = "--------------------";
    private static Scanner scanner = new Scanner(System.in);
    private VendingMachine engine = new VendingMachineEngine(new SimpleCoinPocket(getInitialCoins()), getInitialShelves());

    public static void main(String[] args) {
        VendingMachineDisplay dsp = new VendingMachineDisplay();
        while (true) {
            System.out.println();
            System.out.println(machineName);
            System.out.println(separator);
            dsp.printProducts();
            System.out.println(separator);
            System.out.println("Enter shelf number: ");
            System.out.println(dsp.enterSelectedProductMode(scanner.nextLine()));
        }
    }

    private String enterSelectedProductMode(String shelfNumber) {
        Shelf shelf;
        try {
            shelf = engine.getShelfByNumber(Integer.parseInt(shelfNumber)).orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            return "Wrong shelf number!";
        }
        while (true) {
            System.out.println("Chosen product: " + shelf.getExposedProductName() + "[" + shelf.getExposedProductPrice() + "]");
            System.out.println("You still need to insert " + "[" + shelf.getExposedProductPrice().subtract(engine.getBalance()) + "]" + " more");
            System.out.println("Enter coin {coin_value} or cancel {C}. Skip brackets.");

            String userAction = scanner.nextLine();
            if (userAction.equals("C")) {
                return enterCancelMode();
            }

            try {
                Coin coin = Coin.of(userAction);
                engine.insertCoin(coin);
                if (engine.getBalance().compareTo(shelf.getExposedProductPrice()) > 0) {
                    return enterBuyMode(shelf.getShelfNumber());
                }
            } catch (NotACoinException e) {
                System.out.println("Wrong coin!");
            }
        }

    }

    private String enterBuyMode(int shelfNumber) {
        BuyResult result = engine.buy(shelfNumber);

        if (!result.isSuccess()) {
            return "Error! " + result.getMessage();
        }
        return result.getMessage() + " Product bought: " + result.getProduct().getName() + " change: " + result.getChange();
    }

    private String enterCancelMode() {
        Map<Coin, Integer> ejectedMoney = engine.cancel();
        return "Canceled. Ejected money: " + ejectedMoney;
    }


    private void printProducts() {
        List<Shelf> shelves = engine.getShelves();
        for (Shelf shelf : shelves) {
            System.out.println(shelf.getShelfNumber() + ". " + shelf.getExposedProductName() + " [" + shelf.getExposedProductPrice() + "]");
        }
    }

}
