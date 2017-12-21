package cat.udl.eps.ep.data;

import java.math.BigDecimal;

public class Money {
    private BigDecimal quantity;
    private Currency currency;

    public Money(BigDecimal quantity, Currency currency) {
        this.quantity = quantity;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Suma sobre divisies diferents");

        /*TODO*/
        return this;
    }

    public Money subtract(Money other) {
        if (!(other.currency.equals(currency)))
            throw new IllegalArgumentException("Resta sobre divisies diferents");
        /*TODO*/
        return null;
    }

    public Money multiply(int multiplier) {
        /*TODO*/
        return null;
    }

    public Money change(BigDecimal ratio, Currency to) {
        if (to.equals(currency))
            throw new IllegalArgumentException("No es pot fer el canvi a la mateixa moneda");
        /*TODO*/
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return currency != null ? currency.equals(money.currency) : money.currency == null;
    }

    @Override
    public int hashCode() {
        return currency != null ? currency.hashCode() : 0;
    }
}
