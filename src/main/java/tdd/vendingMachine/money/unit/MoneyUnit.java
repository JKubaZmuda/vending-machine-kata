package tdd.vendingMachine.money.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUnit implements Comparable<MoneyUnit> {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigDecimal value;

    public MoneyUnit(String value) {
        this.value = new BigDecimal(value).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private MoneyUnit(BigDecimal value) {
        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyUnit moneyUnit = (MoneyUnit) o;

        return value.equals(moneyUnit.value);

    }

    public int getCents() {
        return value.multiply(new BigDecimal("100")).intValue();
    }

    @Override
    public int compareTo(MoneyUnit o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public MoneyUnit sum(MoneyUnit other) {
        return new MoneyUnit(this.value.add(other.value));
    }

    public MoneyUnit subtract(MoneyUnit other) {
        return new MoneyUnit(this.value.subtract(other.value));
    }

    public MoneyUnit multiply(BigDecimal other) {
        return new MoneyUnit(this.value.multiply(other));
    }

}
