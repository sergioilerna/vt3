package Test;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

public class MoneyTest {
    @Test(expected = IllegalArgumentException.class)
    public void suma_de_Money_amb_currency_diferent() {
        Money m = new Money(new BigDecimal("20"), new Currency("euro"));
        Money m2 = new Money(new BigDecimal("20"), new Currency("dollar"));
        m.add(m2);
    }

    @Test
    public void suma_de_Money_amb_igual_currency() {
        Money m = new Money(new BigDecimal("20.35"), new Currency("euro"));
        Money m2 = new Money(new BigDecimal("20.451324234"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("40.81"), new Currency("euro"));
        assertTrue(expected.equals(m.add(m2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resta_de_Money_amb_currency_diferent() {
        Money m = new Money(new BigDecimal("20"), new Currency("euro"));
        Money m2 = new Money(new BigDecimal("20"), new Currency("dollar"));
        m.subtract(m2);
    }

    @Test
    public void resta_de_Money_amb_igual_currency() {
        Money m = new Money(new BigDecimal("20.35"), new Currency("euro"));
        Money m2 = new Money(new BigDecimal("20.451"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("-00.11"), new Currency("euro"));
        assertTrue(expected.equals(m.subtract(m2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canviar_currency_a_una_currency_igual() {
        Money m = new Money(new BigDecimal("20"), new Currency("euro"));
        m.change(new BigDecimal(2.5), new Currency("euro"));
    }

    @Test
    public void canviar_currency_a_una_currency_diferent() {
        Money m = new Money(new BigDecimal("20"), new Currency("euro"));
        m = m.change(new BigDecimal("1.1850"), new Currency("dollar"));
        Money expected = new Money(new BigDecimal("23.70"), new Currency("dollar"));
        assertTrue(expected.equals(m));
    }


}