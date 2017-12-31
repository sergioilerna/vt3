package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.StockExchange;

/**
 * Tractament dels diferents tipus d'inversions.
 *
 * @author Alvaro Ortega Marmol
 * @DNI  53399228-J
 */
public interface Investment {
    Money evaluate(Currency currencyTo,
                   MoneyExchange moneyEx,
                   StockExchange stockEx) throws EvaluationException;
}