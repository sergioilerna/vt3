package cat.udl.eps.ep.service;

/**
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class TicketDoesNotExistException extends Exception {
    public TicketDoesNotExistException(String message) {
        super(message);
    }
}