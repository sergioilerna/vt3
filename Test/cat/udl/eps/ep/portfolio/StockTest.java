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
public class StockTest {
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
                return new Money(new BigDecimal("500"), new Currency("pesetas"));
            }
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
        Ticket ticket = new Ticket("CBK");
        Stock stock = new Stock(ticket, 20);
        Money m = stock.evaluate(new Currency("euro"),
                                new MoneyExchangeImpl(),
                                new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("60.20"),new Currency("euro"));

        assertTrue(expected.equals(m));
    }
    @Test
    public void exist_ticket_and_have_same_currency() throws EvaluationException {
        Ticket ticket = new Ticket("BBVA");
        Stock stock = new Stock(ticket, 2);
        Money m = stock.evaluate(new Currency("pesetas"),
                                new MoneyExchangeImpl(),
                                new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("1000.00"),new Currency("pesetas"));
        assertTrue(expected.equals(m));
    }



}