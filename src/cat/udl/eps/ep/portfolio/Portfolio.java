package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.RatioDoesNotExistException;
import cat.udl.eps.ep.service.StockExchange;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class Portfolio implements Investment {
    private LinkedList<Investment> cartera;

    public Portfolio() {
        cartera = new LinkedList<Investment>();

    }

    public void addInvestment(Investment investment) {
        if (investment == null)
            throw new IllegalArgumentException("El métode investment ha de contenir una inversió.");
        cartera.add(investment);
    }

    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        Money total = new Money(new BigDecimal("2"), new Currency("euro"));
        Iterator iter = cartera.iterator();
        BigDecimal ratio;
        while (iter.hasNext()) {
            Investment investment = (Investment) iter.next();
            Money result = investment.evaluate(currencyTo, moneyEx, stockEx);
            if (!result.getCurrency().equals(total.getCurrency())) {
                try {
                    ratio = moneyEx.exchangeRatio(result.getCurrency(), total.getCurrency());
                    result = result.change(ratio, total.getCurrency());
                } catch (RatioDoesNotExistException e) {
                    throw new EvaluationException("No es pot fer el canvi a la divisa");
                }
            }
            total = total.add(result);
        }
        if (!total.getCurrency().equals(currencyTo)) {
            try {
                ratio = moneyEx.exchangeRatio(total.getCurrency(), currencyTo);
                total = total.change(ratio, currencyTo);
            } catch (RatioDoesNotExistException e) {
                throw new EvaluationException("No es pot fer el canvi a la divisa");
            }
        }
        return total;
    }
}
