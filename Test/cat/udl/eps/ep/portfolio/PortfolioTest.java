package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.RatioDoesNotExistException;
import cat.udl.eps.ep.service.StockExchange;
import cat.udl.eps.ep.service.TicketDoesNotExistException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */
public class PortfolioTest {
    private static class MoneyExchangeImpl implements MoneyExchange {
        @Override
        public BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException {
            if (from.equals(to))
                throw new RatioDoesNotExistException("No es pot fer el canvi a una mateixa divisa");
            else
                return new BigDecimal("2.23");
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
                return new Money(new BigDecimal("4.37"), new Currency("euro"));
            }
        }
    }

    Portfolio portfolio;

    @Before
    public void init_portfolio_test() {
        portfolio = new Portfolio();

    }

    @Test(expected = IllegalArgumentException.class)
    public void add_null_investment() {
        portfolio.addInvestment(null);
    }

    @Test
    public void evalueate_portfolio_with_one_investment() throws EvaluationException {
        portfolio.addInvestment(
                new FutureBuy(
                        new Ticket("BBVA"),
                        12,
                        new Money(new BigDecimal("3"), new Currency("dollar")))
        );
        Money result = portfolio.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("-25.84"), new Currency("euro"));
        assertTrue(result.equals(expected));
    }

    @Test(expected = EvaluationException.class)
    public void evaluate_portfolio_with_two_investment_with_Ticket_no_exists() throws EvaluationException {
        portfolio.addInvestment(new FutureBuy(
                        new Ticket("BBVA"),
                        234,
                        new Money(new BigDecimal("1.334"), new Currency("euro"))
                )
        );
        portfolio.addInvestment(
                new FutureSell(
                        new Ticket("credit_Andorra"),
                        3,
                        new Money(new BigDecimal("1.3423"), new Currency("euro Andorra"))

                )
        );
        portfolio.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
    }

    @Test
    public void evaluate_portfolio_with_two_investment() throws EvaluationException {
        portfolio.addInvestment(new FutureBuy(
                        new Ticket("BBVA"),
                        8,
                        new Money(new BigDecimal("5.756"), new Currency("euro"))
                )
        );
        portfolio.addInvestment(
                new FutureSell(
                        new Ticket("ING"),
                        7,
                        new Money(new BigDecimal("8.12"), new Currency("euro Andorra"))

                )
        );
        Money result = portfolio.evaluate(new Currency("dollar"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("172.14"), new Currency("dollar"));
        assertTrue(result.equals(expected));
    }

}