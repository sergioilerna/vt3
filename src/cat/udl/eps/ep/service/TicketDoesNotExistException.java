package cat.udl.eps.ep.service;

public class TicketDoesNotExistException extends Exception {
    public TicketDoesNotExistException(String message) {
        super(message);
    }
}