package cat.udl.eps.ep.service;

/**
 * Exception quan no existeix l'empresa
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class TicketDoesNotExistException extends Exception {
    /**
     * Exception quan no existeix l'empresa
     * @param message String que es vol mostrar quan es produeix la excepcio
     */
    public TicketDoesNotExistException(String message) {
        super(message);
    }
}