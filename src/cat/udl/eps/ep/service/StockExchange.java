package cat.udl.eps.ep.service;

import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public interface StockExchange {
    Money value(Ticket ticket) throws TicketDoesNotExistException;
}
