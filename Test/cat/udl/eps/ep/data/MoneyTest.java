package cat.udl.eps.ep.data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

public class MoneyTest {
    Money money;

    @Before
    public void init_money(){
        money = new Money(new BigDecimal("20.35"), new Currency("euro"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_with_null_quantity(){
        Money null_quantity=new Money(null,new Currency("euro"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructor_with_all_attributes_null(){
        Money null_quantity=new Money(null,null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructor_with_null_currency(){
        Money null_currency=new Money(null,new Currency("euro"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void suma_de_Money_amb_currency_diferent() {
        Money moneyToAdd = new Money(new BigDecimal("20"), new Currency("dollar"));
        money.add(moneyToAdd);
    }

    @Test
    public void suma_de_Money_amb_igual_currency() {
        Money m2 = new Money(new BigDecimal("20.451324234"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("40.81"), new Currency("euro"));
        assertTrue(expected.equals(money.add(m2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resta_de_Money_amb_currency_diferent() {

        Money moneyToSubs = new Money(new BigDecimal("20"), new Currency("dollar"));
        money.subtract(moneyToSubs);
    }

    @Test
    public void resta_de_Money_amb_igual_currency() {
        Money moneyToSubs = new Money(new BigDecimal("20.451"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("-00.11"), new Currency("euro"));
        assertTrue(expected.equals(money.subtract(moneyToSubs)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canviar_currency_a_una_currency_igual() {
        money.change(new BigDecimal(2.5), new Currency("euro"));
    }

    @Test
    public void canviar_currency_a_una_currency_diferent() {
        money = money.change(new BigDecimal("1.1850"), new Currency("dollar"));
        Money expected = new Money(new BigDecimal("24.12"), new Currency("dollar"));
        assertTrue(expected.equals(money));
    }

    @Test
    public void multiplicar_una_quantitat_per_un_enter() {
        Money expected = new Money(new BigDecimal("61.05"), new Currency("euro"));
        assertTrue(expected.equals(money.multiply(3)));
    }


}