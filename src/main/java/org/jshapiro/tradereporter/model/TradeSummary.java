package org.jshapiro.tradereporter.model;

import java.math.BigDecimal;

public record TradeSummary(String fromBank, String toBank, BigDecimal amount) {
}
