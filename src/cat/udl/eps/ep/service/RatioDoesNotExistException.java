package cat.udl.eps.ep.service;

/**
 * Exception quan les divises son iguals
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class RatioDoesNotExistException extends Exception {
    /**
     * Exception quan les divises son iguals
     * @param message String que es vol mostrar quan es produeix la excepcio
     */
    public RatioDoesNotExistException(String message) {
        super(message);
    }
}
