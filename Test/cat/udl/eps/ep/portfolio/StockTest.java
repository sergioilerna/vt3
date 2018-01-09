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

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */
public class StockTest {
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

    @Test(expected = EvaluationException.class)
    public void does_not_exist_ticket() throws EvaluationException {
        Ticket ticket = new Ticket("BIBM");
        Stock stock = new Stock(ticket, 20);
        stock.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());

    }

    @Test
    public void exist_ticket_and_have_diferent_currency() throws EvaluationException {
        Ticket ticket = new Ticket("ING");
        Stock stock = new Stock(ticket, 8);
        Money m = stock.evaluate(new Currency("euro"),
                                new MoneyExchangeImpl(),
                                new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("95.60"),new Currency("euro"));

        assertEquals(expected,m);
    }
    @Test
    public void exist_ticket_and_have_same_currency() throws EvaluationException {
        Ticket ticket = new Ticket("BBVA");
        Stock stock = new Stock(ticket, 2);
        Money m = stock.evaluate(new Currency("dollar"),
                                new MoneyExchangeImpl(),
                                new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("20.68"),new Currency("dollar"));
        assertEquals(expected,m);
    }



}