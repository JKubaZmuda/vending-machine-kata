package tdd.vendingMachine.product;

import org.junit.Test;
import tdd.vendingMachine.money.unit.MoneyUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ShelfTest {

    @Test
    public void shouldObtainProjectForSufficientSupply() {
        //given
        Shelf shelf = new Shelf(dummyProduct(), 2);
        //when
        //then
        assertThat(shelf.obtainProduct()).isPresent();
        assertThat(shelf.obtainProduct()).isPresent();
    }

    @Test
    public void shouldObtainNoProductForInsufficientSupplies() {
        //given
        Shelf shelf = new Shelf(dummyProduct(), 1);
        //when
        shelf.obtainProduct();
        //then
        assertThat(shelf.obtainProduct()).isEmpty();
    }

    private Product dummyProduct() {
        return new Product("dummy", new MoneyUnit("10"));
    }

}
