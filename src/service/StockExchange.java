package service;

import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;

public interface StockExchange {
    Money value(Ticket ticket) throws TicketDoesNotExistException;
}
