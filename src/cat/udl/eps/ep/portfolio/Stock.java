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

    /**
     * Numero d'accions d'una determinada companyia
     *
     * @param ticket    Nom de l'empresa
     * @param numShares quantitat d'accions
     */
    public Stock(Ticket ticket, int numShares) {
        this.ticket = ticket;
        this.numShares = numShares;
    }

    /**
     * Metode que retorna el preu de les accions d'una empresa en una determinada divisa.
     *
     * @param currencyTo Divisa a la qual volem transormar
     * @param moneyEx    Servei extern encarregat de retornar el ratio per el qual
     *                   s'ha de multiplicar per poder fer la conversio.
     * @param stockEx    Servei exern encarregat de veure les accions d'una empresa.
     * @return Moneda en la divisa indicada a currencyTo amb la quanitat corresponent a les accions de la empresa
     * @throws EvaluationException Exceptions No existeix el Ticket o les divises son iguals
     */
    @Override
    public Money evaluate(Currency currencyTo, MoneyExchange moneyEx, StockExchange stockEx) throws EvaluationException {
        if (currencyTo == null || moneyEx == null || stockEx == null)
            throw new IllegalArgumentException("Els parametres no poden ser null");
        Money result;
        try {
            result = stockEx.value(ticket);
        } catch (TicketDoesNotExistException e) {
            throw new EvaluationException("Error: No existeix el Ticket al StockExchange.");
        }

        if (!result.getCurrency().equals(currencyTo)) {
            BigDecimal ratio;
            try {
                ratio = moneyEx.exchangeRatio(result.getCurrency(), currencyTo);
            } catch (RatioDoesNotExistException e) {
                throw new EvaluationException("Error: No es pot canviar d'una divisa a la mateixa divisa.");
            }

            result = result.change(ratio, currencyTo);
        }
        return result.multiply(numShares);
    }
}