package cat.udl.eps.ep.data;

/**
 * Representacio del nom d'una divisa
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class Currency {

    private String name;

    /**
     * Constructor
     *
     * @param name nom de la divisa
     */
    public Currency(String name) {
        this.name = name;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return name != null ? name.equals(currency.name) : currency.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "\t Divisa='" + name+"'";
    }
}
