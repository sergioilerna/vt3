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
 * Representa una inversión en un número de acciones de una determinada compañía
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */

/* Se invocarán, en algunos casos, ambos servicios externos MoneyExchange y StockExchange */
public class Stock implements Investment {
    private Ticket ticket;
    private int numShares;

    public Stock(Ticket ticket, int numShares) {
        this.ticket = ticket;
        this.numShares = numShares;
    }

    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        Money m;
        try {
            m = stockEx.value(ticket);
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("Error: No existeix el Ticket al StockExchange.");
        }
        if (!m.getCurrency().equals(currencyTo)){
            BigDecimal ratio;
            try {
                ratio = moneyEx.exchangeRatio(m.getCurrency(), currencyTo);
            } catch (RatioDoesNotExistException e) {
                throw new EvaluationException("Error: No es pot canviar d'una divisa a la mateixa divisa.");
            }

            m = m.change(ratio, currencyTo);
        }
        return m.multiply(numShares);
    }
}