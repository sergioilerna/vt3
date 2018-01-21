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
 * Cartera de inversions
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class Portfolio implements Investment {
    private LinkedList<Investment> cartera;

    public Portfolio() {
        cartera = new LinkedList<>();
    }

    /**
     * Afegir una nova inversio
     * @param investment FutureSell, FutureBuy, Money, etc.
     */

    public void addInvestment(Investment investment) {
        if (investment == null)
            throw new IllegalArgumentException("El métode investment ha de contenir una inversió.");
        cartera.add(investment);
    }

    /**
     * Suma de les valoracions de les inversions que conté
     *
     * @param currencyTo Divisa a la qual es fara el canvi
     * @param moneyEx    Conversor de divises
     * @param stockEx    Quantitat d'accions d'una companyia
     * @return Moneda amb el resultat de l'operacio
     * @throws EvaluationException Error en l'evaluacio
     */
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
