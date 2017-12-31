package cat.udl.eps.ep.service;

import cat.udl.eps.ep.data.Currency;

import java.math.BigDecimal;

/**
 * Canvi d'una divisa a una altra
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public interface MoneyExchange {
    BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException;
}
