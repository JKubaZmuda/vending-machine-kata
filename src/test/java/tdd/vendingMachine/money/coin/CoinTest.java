package tdd.vendingMachine.money.coin;

import org.junit.Test;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.coin.NotACoinException;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinTest {

    @Test
    public void shouldCreateCoinOfSupportedStringValue() throws NotACoinException {
        //given
        //when
        //then
        assertThat(Coin.of("5")).isEqualTo(Coin.COIN_500);
        assertThat(Coin.of("5.0")).isEqualTo(Coin.COIN_500);
        assertThat(Coin.of("2")).isEqualTo(Coin.COIN_200);
        assertThat(Coin.of("2.0")).isEqualTo(Coin.COIN_200);
        assertThat(Coin.of("1")).isEqualTo(Coin.COIN_100);
        assertThat(Coin.of("1.0")).isEqualTo(Coin.COIN_100);
        assertThat(Coin.of("0.5")).isEqualTo(Coin.COIN_50);
        assertThat(Coin.of("0.2")).isEqualTo(Coin.COIN_20);
        assertThat(Coin.of("0.1")).isEqualTo(Coin.COIN_10);
    }

    @Test(expected = NotACoinException.class)
    public void shouldThrowExceptionOnInvalidStringValue() throws NotACoinException {
        //given
        //when
        //then
        assertThat(Coin.of("4.5")).isNotNull();
    }

}
