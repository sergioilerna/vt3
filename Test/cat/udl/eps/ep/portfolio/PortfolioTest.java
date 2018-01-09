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

    private Portfolio portfolio;

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
        Ticket ticketFB = new Ticket("BBVA");
        Money moneyFB = new Money(new BigDecimal("3"), new Currency("dollar"));
        FutureBuy first_futureBuy = new FutureBuy(ticketFB,7,moneyFB);

        portfolio.addInvestment(first_futureBuy);
        Money result = portfolio.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("75.64"), new Currency("euro"));
        assertTrue(result.equals(expected));
    }

    @Test(expected = EvaluationException.class)
    public void evaluate_portfolio_with_two_investment_with_Ticket_no_exists() throws EvaluationException {
        Ticket ticketFS = new Ticket("credit_Andorra");
        Money moneyFS =new Money(new BigDecimal("1.3423"), new Currency("euro Andorra"));
        FutureSell first_futureSell = new FutureSell(ticketFS,3,moneyFS);
        portfolio.addInvestment(first_futureSell);
        portfolio.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
    }

    @Test
    public void evaluate_portfolio_with_two_investment() throws EvaluationException {
        Ticket ticketFB = new Ticket("BBVA");
        Money moneyFB = new Money(new BigDecimal("8.756"), new Currency("euro"));
        FutureBuy first_futureBuy = new FutureBuy(ticketFB,21,moneyFB);


        Ticket ticketFS = new Ticket("ING");
        Money moneyFS =new Money(new BigDecimal("15.12"), new Currency("euro"));
        FutureSell first_futureSell = new FutureSell(ticketFS,3,moneyFS);

        portfolio.addInvestment(first_futureBuy);
        portfolio.addInvestment(first_futureSell);
        Money result = portfolio.evaluate(new Currency("euro"), new MoneyExchangeImpl(), new StockExchangeImpl());
        Money expected = new Money(new BigDecimal("138.77"), new Currency("euro"));
        assertTrue(result.equals(expected));
    }

}