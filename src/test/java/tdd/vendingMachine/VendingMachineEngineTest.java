package tdd.vendingMachine;

import org.junit.Test;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.product.Shelf;
import tdd.vendingMachine.result.BuyResult;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class VendingMachineEngineTest {

    @Test
    public void shouldShowProperBalanceAfterInsertingCoins() {
        //given
        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine();
        //when
        vendingMachineEngine.insertCoin(Coin.COIN_10);
        vendingMachineEngine.insertCoin(Coin.COIN_500);
        vendingMachineEngine.insertCoin(Coin.COIN_100);
        //then
        assertThat(vendingMachineEngine.getBalance()).isEqualTo(new MoneyUnit("6.10"));
    }

    @Test
    public void shouldStartWithZeroBalance() {
        //given
        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine();
        //when
        //then
        assertThat(vendingMachineEngine.getBalance()).isEqualTo(new MoneyUnit("0"));
    }

    @Test
    public void shouldEjectInsertedMoneyWhenCancelled() {
        //given
        VendingMachineEngine vendingMachine = new VendingMachineEngine();
        //when
        vendingMachine.insertCoin(Coin.COIN_50);
        //then
        Map<Coin, Integer> coins = vendingMachine.cancel();
        assertThat(coins.get(Coin.COIN_50)).isEqualTo(1);
    }

    @Test
    public void shouldNotProceedBuyingForWrongShelfNumber() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, dummyProduct(), 1));
        shelves.add(new Shelf(2, dummyProduct(), 1));
        VendingMachineEngine vendingMachine = new VendingMachineEngine(shelves);
        //when
        //then
        assertThat(vendingMachine.buy(3)).isEmpty();
    }

    @Test
    public void shouldNotProceedBuyingForInsufficientFunds() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("0.5"), 1));
        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(shelves);
        //when
        vendingMachineEngine.insertCoin(Coin.COIN_20);
        vendingMachineEngine.insertCoin(Coin.COIN_20);
        //then
        assertThat(vendingMachineEngine.buy(1)).isEmpty();
    }

    @Test
    public void shouldNotProceedBuyingWhenNotAbleToReturnChangeAfterwards() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("1.4"), 1));

        Map<Coin, Integer> initialCoins = new HashMap<>();
        initialCoins.put(Coin.COIN_500, 12);
        initialCoins.put(Coin.COIN_200, 9);

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(shelves, initialCoins);
        //when
        vendingMachineEngine.insertCoin(Coin.COIN_200);
        //then
        assertThat(vendingMachineEngine.isAbleToEject(new MoneyUnit("0.6"))).isFalse();
        assertThat(vendingMachineEngine.buy(1)).isEmpty();
    }

    @Test
    public void shouldProceedBuyingAndReturnChange() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("5.50"), 1));

        Map<Coin, Integer> initialCoins = new HashMap<>();
        initialCoins.put(Coin.COIN_10, 100);
        initialCoins.put(Coin.COIN_20, 100);
        initialCoins.put(Coin.COIN_50, 100);

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(shelves, initialCoins);
        //when
        vendingMachineEngine.insertCoin(Coin.COIN_500);
        vendingMachineEngine.insertCoin(Coin.COIN_500);
        //then
        Optional<BuyResult> result = vendingMachineEngine.buy(1);
        assertThat(result).isPresent();
        int numberOfEjectedCoins = result.map(p -> p.getChange().values().stream().mapToInt(Integer::intValue).sum()).orElse(0);
        assertThat(numberOfEjectedCoins).isGreaterThan(0);
    }

    @Test
    private void shouldShowZeroBalanceAfterCancel() {
        //given
        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine();
        //when
        vendingMachineEngine.insertCoin(Coin.COIN_10);
        vendingMachineEngine.cancel();
        //then
        assertThat(vendingMachineEngine.getBalance()).isEqualTo(new MoneyUnit("0"));
    }

    @Test
    private void shouldShowZeroBalanceAfterBuying() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("0.1"), 1));
        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(shelves);
        //when
        vendingMachineEngine.insertCoin(Coin.COIN_10);
        vendingMachineEngine.buy(1);
        //then
        assertThat(vendingMachineEngine.getBalance()).isEqualTo(new MoneyUnit("0"));
    }

    private Product dummyProduct() {
        return new Product("dummy", new MoneyUnit("10"));
    }

    private Product productCosting(String cost) {
        return new Product("someSnack", new MoneyUnit(cost));
    }
}
