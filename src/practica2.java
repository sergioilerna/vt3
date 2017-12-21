import cat.udl.eps.ep.data.Currency;
import cat.udl.eps.ep.data.Money;
import cat.udl.eps.ep.data.Ticket;

import java.math.BigDecimal;

public class practica2 {
    public static void main(String args[]) {
        Currency c = new Currency("euro");
        Currency c2 = new Currency("libra");
        Ticket t = new Ticket("CBK");
        Money m = new Money(new BigDecimal("20"), c);
        Money m2 = new Money(new BigDecimal("20"), c2);
        m.add(m2);
        System.out.println(c.toString());
        System.out.println(t.toString());
    }
}
