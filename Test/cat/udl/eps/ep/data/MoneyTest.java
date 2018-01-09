package cat.udl.eps.ep.data;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */
public class MoneyTest {
    Money money;

    @Before
    public void init_money() {
        money = new Money(new BigDecimal("20.3512321"), new Currency("euro"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_with_null_quantity() {
        new Money(null, new Currency("euro"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_with_all_attributes_null() {
        new Money(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_with_null_currency() {
        new Money(null, new Currency("euro"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void suma_de_Money_amb_currency_diferent() {
        Money moneyToAdd = new Money(new BigDecimal("20"), new Currency("dollar"));
        money.add(moneyToAdd);
    }

    @Test
    public void suma_de_Money_amb_igual_currency() {
        Money m2 = new Money(new BigDecimal("20.451324234"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("40.82"), new Currency("euro"));
        assertEquals(expected,m2.add(money));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resta_de_Money_amb_currency_diferent() {

        Money moneyToSubs = new Money(new BigDecimal("20"), new Currency("dollar"));
        money.subtract(moneyToSubs);
    }

    @Test
    public void resta_de_Money_amb_igual_currency() {
        Money moneyToSubs = new Money(new BigDecimal("20.451"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("-00.10"), new Currency("euro"));
        assertEquals(expected,money.subtract(moneyToSubs));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canviar_currency_a_una_currency_igual() {
        money.change(new BigDecimal(2.5), new Currency("euro"));
    }

    @Test
    public void canviar_currency_a_una_currency_diferent() {
        money = money.change(new BigDecimal("1.1850"), new Currency("dollar"));
        Money expected = new Money(new BigDecimal("24.13"), new Currency("dollar"));
        assertEquals(expected,money);
    }

    @Test
    public void multiplicar_una_quantitat_per_un_enter() {
        Money expected = new Money(new BigDecimal("61.08"), new Currency("euro"));
        assertEquals(expected,money.multiply(3));
    }


}