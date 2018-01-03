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
 * @DNI 53399228-J
 */
public class FutureBuy implements Investment {
    private Ticket ticket;
    private int numShares;
    private Money pricePerShare;

    /**
     * Contructor FutureSell
     *
     * @param ticket        Companyia
     * @param numShares     Número d'accions
     * @param pricePerShare preu de l'accio
     */
    public FutureBuy(Ticket ticket, int numShares, Money pricePerShare) {
        if (ticket == null || numShares < 1 | pricePerShare == null)
            throw new IllegalArgumentException("El constructur no pot contenir parametres nulls");
        this.ticket = ticket;
        this.numShares = numShares;
        this.pricePerShare = pricePerShare;
    }

    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        if (currencyTo == null || moneyEx == null || stockEx == null)
            throw new IllegalArgumentException("Els parametres no poden ser null");

        //ticket Value --> Valor actual
        Money ticketValue;
        try {
            ticketValue = stockEx.value(this.ticket);
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("La companyia no te accions");
        }
        BigDecimal ratio;
        if (!ticketValue.getCurrency().equals(currencyTo)) {
            ticketValue=obtainMoney(ticketValue,moneyEx,currencyTo);
        }
        ticketValue = ticketValue.multiply(numShares);

        //Price Value --> Valor pactat
        Money priceValue;
        if (!pricePerShare.getCurrency().equals(currencyTo)){
            priceValue=obtainMoney(pricePerShare,moneyEx,currencyTo);
        }
        else{
            priceValue=pricePerShare;
        }

        priceValue = priceValue.multiply(numShares);


        return ticketValue.subtract(priceValue);
    }

    private Money obtainMoney(Money result, MoneyExchange moneyEx, Currency to) throws EvaluationException{
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
