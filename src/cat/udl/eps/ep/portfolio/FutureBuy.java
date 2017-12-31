package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.StockExchange;
/**
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class FutureBuy implements Investment {
    public FutureBuy(Ticket ticket, int numShares, Money pricePerShare) {

    }

    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        return null;
    }
}
