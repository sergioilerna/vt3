package cat.udl.eps.ep.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private BigDecimal quantity;
    private Currency currency;

    public Money(BigDecimal quantity, Currency currency) {
        this.quantity = quantity;
        this.currency = currency;
    }

    public Money add(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Suma sobre divisies diferents");
        return new Money(this.quantity.add(other.quantity).setScale(2, RoundingMode.UP), this.currency);
    }

    public Money subtract(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Resta sobre divisies diferents");
        return new Money(this.quantity.subtract(other.quantity).setScale(2, RoundingMode.UP), this.currency);
    }

    public Money multiply(int multiplier) {
        return new Money(this.quantity.multiply(new BigDecimal(multiplier)).setScale(2, RoundingMode.UP), this.currency);
    }

    public Money change(BigDecimal ratio, Currency to) {
        if (to.equals(this.currency))
            throw new IllegalArgumentException("No es pot fer el canvi a la mateixa moneda");
        return new Money(this.quantity.multiply(ratio).setScale(2, RoundingMode.UP), to);
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
        return "Money " +
                "quantity=" + quantity +
                ", currency=" + currency;
    }
}
