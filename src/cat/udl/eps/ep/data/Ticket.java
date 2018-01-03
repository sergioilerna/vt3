package cat.udl.eps.ep.data;

/**
 * Representa el nom d'un ticket (nom d'una empresa en un mercat).
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class Ticket {
    private String name;

    /**
     * Constructor
     *
     * @param name Nom d'una empresa en un mercat
     */
    public Ticket(String name) {
        if (name == null)
            throw new IllegalArgumentException("El Ticket ha de contenir un nom");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return name != null ? name.equals(ticket.name) : ticket.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ticket nom = " + name;
    }
}
