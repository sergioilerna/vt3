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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */
public class FutureSellTest {
    private static class MoneyExchangeImpl implements MoneyExchange {
        @Override
        public BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException {
            BigDecimal ratio = null;
            if (from.equals(to))
                throw new RatioDoesNotExistException("No es pot fer el canvi a una mateixa divisa");
            else {
                if (to.equals(new Currency("euro")))
                    ratio = new BigDecimal("1.4324");
                else if (to.equals(new Currency("euro_ES")))
                    ratio = new BigDecimal("1.213");
                else if (to.equals(new Currency("euro_AND")))
                    ratio = new BigDecimal("1.123");
                else if (to.equals(new Currency("dollar")))
                    ratio = new BigDecimal("2.23342");
            }
            return ratio;
        }
    }

    private static class StockExchangeImpl implements StockExchange {
        private List<Ticket> values;

        StockExchangeImpl() {
            values = new ArrayList<>();
            values.add(new Ticket("BBVA"));
            values.add(new Ticket("ING"));
            values.add(new Ticket("BANKIA"));
        }

        @Override
        public Money value(Ticket ticket) throws TicketDoesNotExistException {
            Money result = null;
            if (values.contains(ticket)) {
                switch (ticket.getName()) {
                    case "BBVA":
                        result = new Money(new BigDecimal("10.34"), new Currency("dollar"));
                        break;
                    case "ING":
                        result = new Money(new BigDecimal("8.34"), new Currency("euro_ES"));
                        break;
                    case "BANKIA":
                        result = new Money(new BigDecimal("7.23"), new Currency("euro_AND"));
                        break;
                }
            } else {
                throw new TicketDoesNotExistException("L'empresa esmentada no conté accions");
            }

            return result;
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
        Ticket ticketFS = new Ticket("BANKIA");
        Money moneyFS = new Money(new BigDecimal("13.45"), new Currency("euro"));
        FutureSell fs = new FutureSell(ticketFS, 12, moneyFS);

        Money result = fs.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("37.08"), new Currency("euro"));
        assertEquals(expected,result);
    }

    @Test
    public void future_sell_with_lose_value() throws EvaluationException {
        Ticket ticketFS = new Ticket("BBVA");
        Money moneyFS = new Money(new BigDecimal("3.23"), new Currency("euro"));
        FutureSell fs = new FutureSell(ticketFS, 8, moneyFS);
        Money result = fs.evaluate(new Currency("dollar"), new MoneyExchangeImpl(), new StockExchangeImpl());

        Money expected = new Money(new BigDecimal("-24.96"), new Currency("dollar"));
        assertEquals(expected,result);
    }
}
