package org.jshapiro.tradereporter.reports;

import org.jshapiro.tradereporter.model.Currency;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.jshapiro.tradereporter.reports.ReportFilter.REPORTABLE_SALES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ReportFilterTest {

    @Autowired
    private ReportFilter reportFilter;

    @Test
    void isReportableShouldReturnTrue() {
        REPORTABLE_SALES.stream().forEach(sellerPartyCurrencyPair -> {

            TradeSummary summary = TradeSummary.builder()
                    .sellerParty(sellerPartyCurrencyPair.getFirst())
                    .currency(sellerPartyCurrencyPair.getSecond())
                    .build();
            assertTrue(reportFilter.isReportable(summary.toTradeDigest()));
        });
    }

    @Test
    void isReportableShouldReturnFalse() {
        REPORTABLE_SALES.stream().forEach(sellerPartyCurrencyPair -> {

            TradeSummary summary = TradeSummary.builder()
                    .sellerParty(sellerPartyCurrencyPair.getFirst())
                    .currency(Currency.JPY)
                    .build();
            assertFalse(reportFilter.isReportable(summary.toTradeDigest()));
        });

        TradeSummary summary = TradeSummary.builder()
                .buyerParty("fooBar")
                .sellerParty("bar")
                .currency(Currency.USD)
                .build();
        assertFalse(reportFilter.isReportable(summary.toTradeDigest()));
    }
}