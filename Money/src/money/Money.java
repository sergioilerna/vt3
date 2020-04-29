package money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa una cantidad monetaria en una determinada divisa
 * @author Ilerna
 */
public class Money {
    private BigDecimal quantity;
    private Currency currency;

    /**
     * Constructor
     *
     * @param quantity cantidad  monetaria en una divisa
     * @param currency divisa correspondiente a la moneda
     */
    public Money(BigDecimal quantity, Currency currency) {
        if (quantity == null || currency == null)
            throw new IllegalArgumentException("Los parametros del constructor no pueden ser nulos");
        this.quantity = quantity.setScale(2,RoundingMode.UP);
        this.currency = currency;
    }

    /**
     * Suma de dos operaciones monetarias (de la misma divisa).
     * Las monedes tienen que tener la misma divisa, en caso  que las divisas sean diferentes se producirá la excepcion 
     * IllegalArgumentException
     *
     * @param other Una moneda con una cantidad
     * @return Moneda resultado operación
     */
    public Money add(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Suma sobre divisas  diferentes");
        return new Money(this.quantity.add(other.quantity).setScale(2, RoundingMode.UP), this.currency);
    }

    /**
     * Resta de dos operaciones monetarias (de la misma divisa).
     * Las monedas tienen que tener la divisa, en caso que las divisas sean diferentes se produira la excepcion
     * IllegalArgumentException
     *
     * @param other Una moneda con una cantidad
     * @return Moneda resultado de la operacion
     */
    public Money subtract(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Resta sobre divisas diferentes");
        return new Money(this.quantity.subtract(other.quantity).setScale(2, RoundingMode.UP), this.currency);
    }

    /**
     * Productu de una cantidad monetaria por un entero
     *
     * @param multiplier entero multiplicador
     * @return Moneda resultado de la operacion
     */
    public Money multiply(int multiplier) {
        return new Money(this.quantity.multiply(new BigDecimal(multiplier)).setScale(2, RoundingMode.UP), this.currency);
    }

    /**
     * Ratio de cambio, expresando el resultado en la divisa dada
     *
     * @param ratio ratio multiplicador de la divisa
     * @param to    divisa a la que se pretende realizar el cambio
     * @return Moneda con la cantidad correspondiente al ratio y la divisa dada
     */
    public Money change(BigDecimal ratio, Currency to) {
        if (to.equals(this.currency))
            throw new IllegalArgumentException("No se puede hacer el cambio a la misma divisa");
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

