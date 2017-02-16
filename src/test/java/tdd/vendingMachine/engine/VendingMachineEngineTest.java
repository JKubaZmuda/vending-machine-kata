package tdd.vendingMachine.engine;

import org.junit.Test;
import tdd.vendingMachine.engine.result.BuyResult;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.pocket.CoinPocket;
import tdd.vendingMachine.money.unit.MoneyUnit;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.product.Shelf;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VendingMachineEngineTest {

    @Test
    public void shouldEjectInsertedMoneyWhenCancelled() {
        //given
        Map<Coin, Integer> change = new HashMap<>();
        change.put(Coin.COIN_50, 1);

        CoinPocket mockPocket = mockAbleToEjectPocket();
        when(mockPocket.ejectMoney(new MoneyUnit("0.5"))).thenReturn(Optional.of(change));
        when(mockPocket.getInsertedMoney()).thenReturn(new MoneyUnit("0.5"));

        VendingMachineEngine vendingMachine = new VendingMachineEngine(mockPocket);
        //when
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

        VendingMachineEngine vendingMachine = new VendingMachineEngine(mockAbleToEjectPocket(), shelves);
        //when
        //then
        BuyResult result = vendingMachine.buy(3);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).isEqualTo(VendingMachineEngine.WRONG_SHELF_NUMBER);
    }

    @Test
    public void shouldNotProceedBuyingForInsufficientFunds() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("0.5"), 1));

        CoinPocket mockPocket = mockAbleToEjectPocket();
        when(mockPocket.getInsertedMoney()).thenReturn(new MoneyUnit("0.4"));

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockPocket, shelves);
        //when
        //then
        BuyResult result = vendingMachineEngine.buy(1);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).isEqualTo(VendingMachineEngine.INSUFFICIENT_FUNDS);
    }

    @Test
    public void shouldNotProceedBuyingForNoMoreProductsLeft() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("0.5"), 1));
        CoinPocket mockPocket = mockAbleToEjectPocket();
        when(mockPocket.getInsertedMoney()).thenReturn(new MoneyUnit("50"));
        when(mockPocket.ejectMoney(any())).thenReturn(Optional.of(new HashMap<>()));
        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockPocket, shelves);
        //when
        vendingMachineEngine.buy(1);
        //then
        BuyResult result = vendingMachineEngine.buy(1);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).isEqualTo(VendingMachineEngine.WRONG_SHELF_NUMBER);
    }

    @Test
    public void shouldNotProceedBuyingWhenNotAbleToReturnChangeAfterwards() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("1.4"), 1));

        CoinPocket mockPocket = mockUnableToEjectPocket();
        when(mockPocket.getInsertedMoney()).thenReturn(new MoneyUnit("20"));

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockPocket, shelves);
        //when
        //then
        BuyResult result = vendingMachineEngine.buy(1);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessage()).isEqualTo(VendingMachineEngine.UNABLE_TO_EJECT_CHANGE);
    }

    @Test
    public void shouldProceedBuyingAndReturnChange() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, productCosting("5.50"), 1));

        CoinPocket mockPocket = mockAbleToEjectPocket();
        HashMap<Coin, Integer> change = new HashMap<>();
        change.put(Coin.COIN_10, 45);
        when(mockPocket.ejectMoney(any())).thenReturn(Optional.of(change));
        when(mockPocket.getInsertedMoney()).thenReturn(new MoneyUnit("10"));

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockPocket, shelves);
        //when
        //then
        BuyResult result = vendingMachineEngine.buy(1);
        assertThat(result.isSuccess()).isTrue();
        int numberOfEjectedCoins = result.getChange().values().stream().mapToInt(Integer::intValue).sum();
        assertThat(numberOfEjectedCoins).isGreaterThan(0);
    }

    @Test
    public void shouldGetShelfFromList() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(2, dummyProduct(), 1));

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockAbleToEjectPocket(), shelves);
        //when
        //then
        assertThat(vendingMachineEngine.getShelfByNumber(2)).isPresent();
    }

    @Test
    public void shouldNotGetShelfFromList() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(2, dummyProduct(), 1));

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockAbleToEjectPocket(), shelves);
        //when
        //then
        assertThat(vendingMachineEngine.getShelfByNumber(3)).isEmpty();
    }

    @Test
    public void shouldRemoveEmptyShelf() {
        //given
        List<Shelf> shelves = new ArrayList<>();
        shelves.add(new Shelf(1, dummyProduct(), 1));

        VendingMachineEngine vendingMachineEngine = new VendingMachineEngine(mockFullSuccessPocket(), shelves);
        //when
        vendingMachineEngine.buy(1);
        //then
        assertThat(vendingMachineEngine.getShelves()).isEmpty();
    }

    public Product dummyProduct() {
        return new Product("dummy", new MoneyUnit("10"));
    }

    public Product productCosting(String cost) {
        return new Product("someSnack", new MoneyUnit(cost));
    }

    public CoinPocket mockFullSuccessPocket() {
        CoinPocket mockPocket = mock(CoinPocket.class);
        when(mockPocket.isAbleToEject(any())).thenReturn(true);
        when(mockPocket.getInsertedMoney()).thenReturn(new MoneyUnit("100"));
        HashMap<Coin, Integer> change = new HashMap<>();
        change.put(Coin.COIN_20, 10);
        when(mockPocket.ejectMoney(any())).thenReturn(Optional.of(change));
        return mockPocket;
    }

    public CoinPocket mockAbleToEjectPocket() {
        CoinPocket mockPocket = mock(CoinPocket.class);
        when(mockPocket.isAbleToEject(any())).thenReturn(true);
        return mockPocket;
    }

    public CoinPocket mockUnableToEjectPocket() {
        CoinPocket mockPocket = mock(CoinPocket.class);
        when(mockPocket.isAbleToEject(any())).thenReturn(false);
        return mockPocket;
    }
}
