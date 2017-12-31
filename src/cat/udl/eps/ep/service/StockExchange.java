package cat.udl.eps.ep.service;

import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;

/**
 * Servei extern encarregat de retornar el Money d'una empresa en una divisa determianda
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public interface StockExchange {
    /**
     * Interficie del servei extern encarregat de retornar el Money d'una empresa en una divisa determianda
     * @param ticket Empresa de la qual volem mirar el Money
     * @return nova Money corresponent a la empresa
     * @throws TicketDoesNotExistException No existeix la empresa
     */
    Money value(Ticket ticket) throws TicketDoesNotExistException;
}
