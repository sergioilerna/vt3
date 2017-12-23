package service;

import cat.udl.eps.ep.data.Currency;

import java.math.BigDecimal;

public interface MoneyExchange {
    BigDecimal exchangeRatio(Currency from, Currency to) throws RatioDoesNotExistException;
}
