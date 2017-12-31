package cat.udl.eps.ep.portfolio;

/**
 * Indicar que hi ha hagut un error al evaluar la inversio.
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class EvaluationException extends Exception {
    public EvaluationException(String message) {
        super(message);
    }
}
