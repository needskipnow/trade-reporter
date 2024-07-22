package org.jshapiro.tradereporter.model;

import java.math.BigDecimal;

public record TradeDigest(String sellerParty, String buyerParty, BigDecimal amount, Currency currency) {
}
