package service;

public class TicketDoesNotExistException extends Exception {
    public TicketDoesNotExistException(String message) {
        super(message);
    }
}