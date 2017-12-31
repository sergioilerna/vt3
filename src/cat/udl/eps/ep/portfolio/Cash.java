package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.RatioDoesNotExistException;
import cat.udl.eps.ep.service.StockExchange;

import java.math.BigDecimal;

/**
 * Representa una inversio en una quantitat d'una moneda concreta
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
/*Como mucho invoca a uno de los servicios externos MoneyExchange*/

public class Cash implements Investment {
    private Money money;

    public Cash(Money money) {
        this.money = money;
    }

    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        BigDecimal ratio = null;
        try {
            ratio = moneyEx.exchangeRatio(money.getCurrency(), currencyTo);

        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("No hi ha ratio per a la mateixa divisa.");
        }
        return money.change(ratio, currencyTo);
    }

}
