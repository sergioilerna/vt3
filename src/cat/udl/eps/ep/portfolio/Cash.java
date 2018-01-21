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

public class Cash implements Investment {
    private Money money;

    /**
     * Constructor
     *
     * @param money Moneda en la qual es farà la inversio
     */
    public Cash(Money money) {
        this.money = money;
    }

    /**
     * Metode que retorna la quantitat moneda en una altra divisa.
     *
     * @param currencyTo Divisa a la qual volem transormar
     * @param moneyEx    Servei extern encarregat de retornar el ratio per el qual
     *                   s'ha de multiplicar per poder fer la conversio.
     * @param stockEx    Servei exern encarregat de veure les accions d'una empresa.
     * @return Moneda en la divisa especificada a currencyTo
     * @throws EvaluationException Exception quan currencyTo i this.getCurrency() es la mateixa
     */
    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        if (currencyTo == null || moneyEx == null)
            throw new IllegalArgumentException("Els parametres no poden ser null");
        BigDecimal ratio;
        try {
            ratio = moneyEx.exchangeRatio(money.getCurrency(), currencyTo);

        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("Error: No es pot canviar d'una divisa a la mateixa divisa.");
        }
        return money.change(ratio, currencyTo);
    }

}
