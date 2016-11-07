package tdd.vendingMachine.money.pocket;

import org.junit.Test;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class SimpleCoinPocketTest {


    @Test
    public void shouldShowProperAmountOfInsertedMoney() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.insertCoin(Coin.COIN_10);
        coinPocket.insertCoin(Coin.COIN_10);
        coinPocket.insertCoin(Coin.COIN_20);
        //then
        assertThat(coinPocket.getInsertedMoney()).isEqualTo(new MoneyUnit("0.4"));
    }

    @Test
    public void shouldShowProperAmountOfMoneyAfterSuccessFulEjection() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.insertCoin(Coin.COIN_10);
        coinPocket.insertCoin(Coin.COIN_10);
        coinPocket.insertCoin(Coin.COIN_20);
        coinPocket.setStorage(spySuccessfulWithdraw());
        coinPocket.ejectMoney(new MoneyUnit("0.3"));
        //then
        assertThat(coinPocket.getInsertedMoney()).isEqualTo(new MoneyUnit("0.1"));
    }

    @Test
    public void shouldShowProperAmountOfMoneyAfterFailedEjection() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.insertCoin(Coin.COIN_100);
        coinPocket.insertCoin(Coin.COIN_50);
        coinPocket.insertCoin(Coin.COIN_20);
        coinPocket.setStorage(spyFailedWithdraw());
        coinPocket.ejectMoney(new MoneyUnit("1.4"));
        //then
        assertThat(coinPocket.getInsertedMoney()).isEqualTo(new MoneyUnit("1.7"));
    }

    @Test
    public void shouldEjectCoinsIfItsAbleToWithdrawFromStorage() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.setStorage(spySuccessfulWithdraw());
        //then
        assertThat(coinPocket.ejectMoney(new MoneyUnit("543"))).isPresent();
    }

    @Test
    public void shouldEjectNoCoinsIfItIsntAbleToWithdrawFromStorage() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.setStorage(spyFailedWithdraw());
        //then
        assertThat(coinPocket.ejectMoney(new MoneyUnit("0.1"))).isEmpty();
    }

    @Test
    public void shouldShowThatItsUnableToWithdraw() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.setStorage(spyFailedWithdraw());
        //then
        assertThat(coinPocket.isAbleToEject(new MoneyUnit("20"))).isFalse();
    }

    @Test
    public void shouldShowThatItsAbleToWithdraw() {
        //given
        SimpleCoinPocket coinPocket = new SimpleCoinPocket();
        //when
        coinPocket.setStorage(spySuccessfulWithdraw());
        //then
        assertThat(coinPocket.isAbleToEject(new MoneyUnit("20"))).isTrue();
    }

    private CoinStorage spyFailedWithdraw() {
        CoinStorage spyStorage = spy(CoinStorage.class);
        doReturn(Optional.empty()).when(spyStorage).withdraw(any());
        doReturn(false).when(spyStorage).isAbleToWithdraw(any());
        return spyStorage;
    }

    private CoinStorage spySuccessfulWithdraw() {
        CoinStorage spyStorage = spy(CoinStorage.class);
        doReturn(Optional.of(new HashMap<>())).when(spyStorage).withdraw(any());
        doReturn(true).when(spyStorage).isAbleToWithdraw(any());
        return spyStorage;
    }

}
