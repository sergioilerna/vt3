package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.RatioDoesNotExistException;
import cat.udl.eps.ep.service.StockExchange;
import cat.udl.eps.ep.service.TicketDoesNotExistException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */
public class FutureSellTest {
    private static class MoneyExchangeImpl implements MoneyExchange {
        @Override
        public BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException {
            if (from.equals(to))
                throw new RatioDoesNotExistException("No es pot fer el canvi a una mateixa divisa");
            else
                return new BigDecimal("0.006009976561091");
        }
    }

    private static class StockExchangeImpl implements StockExchange {
        private List<Ticket> values;

        public StockExchangeImpl() {
            values = new ArrayList<Ticket>();
            values.add(new Ticket("CBK"));
            values.add(new Ticket("BBVA"));
            values.add(new Ticket("ING"));
            values.add(new Ticket("BANKIA"));
        }


        @Override
        public Money value(Ticket ticket) throws TicketDoesNotExistException {
            if (!values.contains(ticket)) {
                throw new TicketDoesNotExistException("L'empresa esmentada no conté accions");
            } else {
                return new Money(new BigDecimal("10.37"), new Currency("euro"));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void future_sell_with_ticket_null_param() {
        new FutureSell(null, 20,
                new Money(new BigDecimal("20"),
                        new Currency("euro")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void future_sell_with_num_shares_negative_param() {
        new FutureSell(null, -1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void future_sell_with_price_per_share_null_params() {
        new FutureSell(null, -1, null);
    }

    @Test
    public void future_sell_with_gain_value() throws EvaluationException {
        FutureSell fs = new FutureSell(new Ticket("BBVA"), 10,
                new Money(new BigDecimal("9.45"),
                        new Currency("euro")));
        Money result = fs.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("-9.20"), new Currency("euro"));
        assertTrue(expected.equals(result));
    }

    @Test
    public void future_sell_with_lose_value() throws EvaluationException {
        FutureSell fs = new FutureSell(new Ticket("BBVA"), 10,
                new Money(new BigDecimal("12.23"),
                        new Currency("euro")));
        Money result = fs.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("18.60"), new Currency("euro"));

        assertTrue(expected.equals(result));
    }
}
