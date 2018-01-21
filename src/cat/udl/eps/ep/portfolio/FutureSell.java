package cat.udl.eps.ep.portfolio;

import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;
import cat.udl.eps.ep.service.MoneyExchange;
import cat.udl.eps.ep.service.StockExchange;

/**
 * Compra futura d'una inversió
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class FutureSell extends Future implements Investment {

    /**
     * Contructor FutureSell
     *
     * @param ticket        Companyia
     * @param numShares     Número d'accions
     * @param pricePerShare preu de l'accio
     */
    public FutureSell(Ticket ticket, int numShares, Money pricePerShare) {
        super(ticket, numShares, pricePerShare);
    }

    /**
     * Venda que es realitzarà en una data futura d'una determinada
     * quantitat d'accions d'una companyia a un preu pactat.
     *
     * @param currencyTo Divisa a la qual es fara el canvi
     * @param moneyEx    Conversor de divises
     * @param stockEx    Quantitat d'accions d'una companyia
     * @return Moneda amb el resultat de l'operacio
     * @throws EvaluationException error en l'evaluacio
     */
    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        Money actual = super.calculateActualValue(currencyTo, moneyEx, stockEx);
        Money agreed = super.calculateAgreedValue(currencyTo, moneyEx);
        return agreed.subtract(actual);
    }

}
