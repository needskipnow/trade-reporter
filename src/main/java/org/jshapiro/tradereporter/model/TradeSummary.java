package org.jshapiro.tradereporter.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public record TradeSummary(Timestamp timestamp, LocalDate tradeDate, String sellerParty, String buyerParty, Currency currency, BigDecimal amount) {
}
