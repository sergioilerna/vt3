package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.StockExchange;


/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class FutureBuy extends Future implements Investment {
    /**
     * Contructor FutureBuy
     *
     * @param ticket        Companyia
     * @param numShares     Número d'accions
     * @param pricePerShare preu de l'accio
     */
    public FutureBuy(Ticket ticket, int numShares, Money pricePerShare) {
        super(ticket,numShares,pricePerShare);
    }

    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {

        Money actual = super.calculateActualValue(currencyTo,moneyEx,stockEx);
        Money agreed = super.calculateAgreedValue(currencyTo,moneyEx);
        return actual.subtract(agreed);
    }

}
