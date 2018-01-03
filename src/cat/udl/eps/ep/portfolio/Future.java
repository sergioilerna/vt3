package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.RatioDoesNotExistException;
import cat.udl.eps.ep.service.StockExchange;
import cat.udl.eps.ep.service.TicketDoesNotExistException;

import java.math.BigDecimal;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228J
 */

class Future {
    private Ticket ticket;
    private int numShares;
    private Money pricePerShare;

    /**
     * Contructor Future
     *
     * @param ticket        Companyia
     * @param numShares     Número d'accions
     * @param pricePerShare preu de l'accio
     */
    public Future(Ticket ticket, int numShares, Money pricePerShare) {
        if (ticket == null || numShares < 1 | pricePerShare == null)
            throw new IllegalArgumentException("El constructur no pot contenir parametres nulls");
        this.ticket = ticket;
        this.numShares = numShares;
        this.pricePerShare = pricePerShare;
    }

    public Money calculateActualValue(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        Money value;
        try {
            value = stockEx.value(this.ticket);
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("La companyia no te accions");
        }
        if (!value.getCurrency().equals(currencyTo)) {
            value = obtainMoney(value, moneyEx, currencyTo);
        }

        return value.multiply(numShares);
    }

    public Money calculateAgreedValue(Currency currencyTo, MoneyExchange moneyEx) throws EvaluationException {
        //Price Value --> Valor pactat
        Money agreedValue;
        if (!pricePerShare.getCurrency().equals(currencyTo)) {
            agreedValue = obtainMoney(pricePerShare, moneyEx, currencyTo);
        } else {
            agreedValue = pricePerShare;
        }

        return agreedValue.multiply(numShares);
    }

    private Money obtainMoney(Money result, MoneyExchange moneyEx, Currency to) throws EvaluationException {
        BigDecimal ratio;
        try {
            ratio = moneyEx.exchangeRatio(result.getCurrency(), to);
            result = result.change(ratio, to);
        } catch (RatioDoesNotExistException e) {
            throw new EvaluationException("No es pot fer el canvi de la divisa");
        }
        return result;
    }
}
