package money;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MoneyTest {
    
    Money money;
    
    // Antes de cada test se insertarán los nuevos valores
    @Before
    public void init_money(){
       money = new Money(new BigDecimal("20.5312321"), new Currency("euro"));
    }
    
    // Todos los atributos sean nulos
    @Test(expected = IllegalArgumentException.class)
    public void contructor_con_atributos_nulos() {
        new Money(null,null);
    }
    
    // Atributo cantidad nulo
    @Test(expected = IllegalArgumentException.class)
    public void contructor_con_atributo_cantidad_nulo() {
        new Money(null,new Currency("euro"));
    }
    
    // Atributo tipo moneda nulo
    @Test(expected = IllegalArgumentException.class)
    public void contructor_con_atributo_currency_nulo() {
        new Money(new BigDecimal("20.5312321"),null);
    }
    
    // Sumar monedas de tipos diferentes
    @Test(expected = IllegalArgumentException.class)
    public void sumar_moneda_currency_diferente() {
        Money m2 = new Money(new BigDecimal("20.5312321"), new Currency("dolar"));
        money.add(m2);
    }
    
    // Sumar monedas de tipos iguales
    @Test
    public void sumar_moneda_currency_igual() {
        Money m2 = new Money(new BigDecimal("20.5312321"), new Currency("euro"));
        Money expected = new Money(new BigDecimal("41.08"), new Currency("euro"));
        assertEquals(expected, money.add(m2));
    }
}
