
package money;

/**
 * Representa nombre de una moneda
 * @author Ilerna
 */
public class Currency {

    private String name;

    /**
     * Constructor
     *
     * @param name nombre de la divisa
     */
    public Currency(String name) {
        if (name == null)
            throw new IllegalArgumentException("El nombre de la divisa no puede ser null");
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
        return "\t Currency='" + name+"'";
    }
}

