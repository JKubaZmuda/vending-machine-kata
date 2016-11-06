package tdd.vendingMachine.money.unit;


import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyUnitTest {

    @Test
    public void shouldConstructWithScaleAndRoundingHalfUp() {
        //given
        //when
        //then
        assertThat(new MoneyUnit("5.555")).isEqualTo(new MoneyUnit("5.56"));
        assertThat(new MoneyUnit("4444.44444")).isEqualTo(new MoneyUnit("4444.4444"));
    }

    @Test
    public void shouldAddUnits() {
        //given
        String a = "2";
        String b = "3";
        String result = "5";
        //when
        //then
        assertThat(new MoneyUnit(a).sum(new MoneyUnit(b))).isEqualTo(new MoneyUnit(result));
    }

    @Test
    public void shouldSubtractUnits() {
        //given
        String a = "10";
        String b = "5";
        String result = "5";
        //when
        //then
        assertThat(new MoneyUnit(a).subtract(new MoneyUnit(b))).isEqualTo(new MoneyUnit(result));
    }

    @Test
    public void shouldMultiplyUnit() {
        //given
        String a = "12";
        String b = "5";
        String result = "60";
        //when
        //then
        assertThat(new MoneyUnit(a).multiply(new BigDecimal(b))).isEqualTo(new MoneyUnit(result));
    }

    @Test
    public void shouldCompareUnequalUnitsByTheirValues() {
        //given
        String a = "2222.42";
        String b = "2222.43";
        //when
        //then
        assertThat(new MoneyUnit(a).compareTo(new MoneyUnit(b))).isEqualTo(-1);
    }

    @Test
    public void shouldCompareEqualUnitsByTheirValues() {
        //given
        String a = "2222.43";
        String b = "2222.43";
        //when
        //then
        assertThat(new MoneyUnit(a).compareTo(new MoneyUnit(b))).isEqualTo(0);
    }

    @Test
    public void shouldShowProperNumberOfCents() {
        //given
        String a = "10.32";
        //when
        //then
        assertThat(new MoneyUnit(a).getCents()).isEqualTo(1032);
    }
}
