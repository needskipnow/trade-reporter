package org.jshapiro.tradereporter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data @Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TradeSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Timestamp timestamp;
        private LocalDate tradeDate;
        private String sellerParty;
        private String buyerParty;
        private Currency currency;
        private BigDecimal amount;

        public TradeSummary(Timestamp timestamp, LocalDate tradeDate, String sellerParty, String buyerParty, Currency tradeCurrency, BigDecimal transationAmount) {
            this.timestamp = timestamp;
            this.tradeDate = tradeDate;
            this.currency = tradeCurrency;
            this.sellerParty = sellerParty;
            this.buyerParty = buyerParty;
            this.amount = transationAmount;
        }
}
