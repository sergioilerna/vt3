package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.RatioDoesNotExistException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */
public class CashTest {
    private Money money;
    private Currency currency;
    private BigDecimal bigDecimal;
    private Cash cash;

    private static class MoneyExchangeImpl implements MoneyExchange {
        @Override
        public BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException {
            if (from.equals(to))
                throw new RatioDoesNotExistException("No es pot fer el canvi a una mateixa divisa");
            else
                return new BigDecimal("0.006009976561091");
        }
    }

    @Test(expected = EvaluationException.class)
    public void evaluate_with_the_same_currency() throws EvaluationException {
        currency = new Currency("dollar");
        bigDecimal = new BigDecimal("20");
        money = new Money(bigDecimal, currency);
        cash = new Cash(money);

        cash.evaluate(money.getCurrency(), new MoneyExchangeImpl(), null);
    }

    @Test
    public void evaluate_with_diferent_currency() throws EvaluationException {
        currency = new Currency("pesatas");
        bigDecimal = new BigDecimal("500");
        money = new Money(bigDecimal, currency);
        cash = new Cash(money);
        Money result = cash.evaluate(new Currency("euro"), new MoneyExchangeImpl(), null);
        Money expected = new Money(new BigDecimal("3.01"), new Currency("euro"));
        assertTrue(expected.equals(result));
    }
}
