package tdd.vendingMachine.money.pocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(Parameterized.class)
public class CoinStorageTest {

    private CoinStorage coinStorage;
    private MoneyUnit amount;
    private int numberOfCoins;
    private boolean result;

    public CoinStorageTest(TestCase testCase) {
        coinStorage = new CoinStorage(testCase);
        amount = testCase.getAmountToWithdraw();
        numberOfCoins = testCase.getNumberOfCoins();
        this.result = testCase.getResult();
    }

    @Test
    public void parameterizedTest() {
        Optional<Map<Coin, Integer>> withdrawn = coinStorage.withdraw(amount);
        assertThat(withdrawn.isPresent()).isEqualTo(result);
        assertThat(withdrawn.map(Map::values).map(this::sum).orElse(0)).isEqualTo(numberOfCoins);
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {
                new TestCase()
                    .coin(Coin.COIN_10, 1)
                    .amount("0.1")
                    .numberOfCoins(1)
                    .result(true)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_50, 1)
                    .coin(Coin.COIN_20, 3)
                    .amount("0.6")
                    .numberOfCoins(3)
                    .result(true)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_500, 1)
                    .coin(Coin.COIN_200, 3)
                    .amount("6")
                    .numberOfCoins(3)
                    .result(true)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_50, 4)
                    .coin(Coin.COIN_20, 3)
                    .amount("2.1")
                    .numberOfCoins(6)
                    .result(true)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_500, 4)
                    .coin(Coin.COIN_200, 3)
                    .amount("21")
                    .numberOfCoins(6)
                    .result(true)
            },
            {
                new TestCase()
                    .amount("0")
                    .numberOfCoins(0)
                    .result(true)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_10, 100)
                    .amount("5.3")
                    .numberOfCoins(53)
                    .result(true)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_500, 1)
                    .coin(Coin.COIN_200, 1)
                    .coin(Coin.COIN_100, 1)
                    .amount("8")
                    .numberOfCoins(3)
                    .result(true)
            },
            {
                new TestCase()
                    .amount("0.1")
                    .numberOfCoins(0)
                    .result(false)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_10, 1)
                    .amount("10")
                    .numberOfCoins(0)
                    .result(false)
            },
            {
                new TestCase()
                    .coin(Coin.COIN_500, 20)
                    .amount("21")
                    .numberOfCoins(0)
                    .result(false)
            }
        });
    }

    private int sum(Iterable<Integer> toSum) {
        int result = 0;
        for(int i : toSum) {
            result += i;
        }
        return result;
    }

}

class TestCase extends HashMap<Coin, Integer> {

    private MoneyUnit amountToWithdraw = new MoneyUnit("0");
    private boolean result;
    private int numberOfCoins;

    public TestCase coin(Coin coin, Integer quantity) {
        put(coin, quantity);
        return this;
    }

    public TestCase amount(String amount) {
        amountToWithdraw = new MoneyUnit(amount);
        return this;
    }

    public TestCase result(boolean result) {
        this.result = result;
        return this;
    }

    public MoneyUnit getAmountToWithdraw() {
        return amountToWithdraw;
    }

    public boolean getResult() {
        return result;
    }

    public TestCase numberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
        return this;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }
}
