package cat.udl.eps.ep.service;

import cat.udl.eps.ep.data.Currency;

import java.math.BigDecimal;

/**
 * Canvi d'una divisa a una altra
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public interface MoneyExchange {
    /**
     * Servei extern per saber el ratio de conversio entre divisis
     * @param from divisa que tenim
     * @param to divisa a la que volem convertir
     * @return ratio de conversio
     * @throws RatioDoesNotExistException Currency from i Currency to son la iguals.
     */
    BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException;
}
