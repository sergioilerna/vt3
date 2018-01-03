package cat.udl.eps.ep.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa una quantitat monetaria en una determinada divisa
 *
 * @author Alvaro Ortega Marmol
 * @DNI 53399228-J
 */
public class Money {
    private BigDecimal quantity;
    private Currency currency;

    /**
     * Constructor
     *
     * @param quantity quantitat monteria en una divisa
     * @param currency divisa corresponent a la moneda
     */
    public Money(BigDecimal quantity, Currency currency) {
        if (quantity == null || currency == null)
            throw new IllegalArgumentException("Els parametres del constructor no poden ser nulls");
        this.quantity = quantity.setScale(2,RoundingMode.UP);
        this.currency = currency;
    }

    /**
     * Suma de dos operacions monetaries (de la mateixa divisa).
     * Les monedes han de tenir la mateixa divisa, en cas de que les divises siguin diferents es produira la excepcio
     * IllegalArgumentException
     *
     * @param other Una moneda amb una quantitat
     * @return Moneda resultant de la operacio
     */
    public Money add(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Suma sobre divisies diferents");
        return new Money(this.quantity.add(other.quantity).setScale(2, RoundingMode.UP), this.currency);
    }

    /**
     * Resta de dos operacions monetaries (de la mateixa divisa).
     * Les monedes han de tenir la mateixa divisa, en cas de que les divises siguin diferents es produira la excepcio
     * IllegalArgumentException
     *
     * @param other Una moneda amb una quantitat
     * @return Moneda resultant de la operacio
     */
    public Money subtract(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Resta sobre divisies diferents");
        return new Money(this.quantity.subtract(other.quantity).setScale(2, RoundingMode.UP), this.currency);
    }

    /**
     * Producte d'na quantitat monetaria por un enter
     *
     * @param multiplier enter multiplicador
     * @return Moneda resultant de la operacio
     */
    public Money multiply(int multiplier) {
        return new Money(this.quantity.multiply(new BigDecimal(multiplier)).setScale(2, RoundingMode.UP), this.currency);
    }

    /**
     * Producte d'una quantitat monetaria per un rati de canvi, expressant el resultat en la divisa donada
     *
     * @param ratio rati multiplicador de la divisa
     * @param to    divisa a la qual es vol realitzar el canvi
     * @return Moneda amb la quantitat corresponent al rati i la divisa donada
     */
    public Money change(BigDecimal ratio, Currency to) {
        if (to.equals(this.currency))
            throw new IllegalArgumentException("No es pot fer el canvi a la mateixa moneda");
        return new Money(this.quantity.multiply(ratio).setScale(2, RoundingMode.UP), to);
    }

    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;
        return (quantity != null ? quantity.equals(money.quantity) : money.quantity == null) &&
                (currency != null ? currency.equals(money.currency) : money.currency == null);
    }

    @Override
    public int hashCode() {
        int result = quantity != null ? quantity.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Money \t Value=" + quantity + currency;
    }
}
