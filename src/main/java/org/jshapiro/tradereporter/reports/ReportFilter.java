package org.jshapiro.tradereporter.reports;

import org.jshapiro.tradereporter.AnagramCheck;
import org.jshapiro.tradereporter.model.Currency;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ReportFilter {
    protected static final Set<Pair<String, Currency>> REPORTABLE_SALES = Set.of(
            Pair.of("EMU_BANK", Currency.AUD),
            Pair.of("BISON_BANK", Currency.USD));

    private final AnagramCheck anagramChecker;

    public ReportFilter(AnagramCheck anagramChecker) {
        this.anagramChecker = anagramChecker;
    }

    public boolean isReportable(TradeSummary trade) {
        if (trade == null || anagramChecker.isAnagram(trade.getBuyerParty(), trade.getSellerParty())) {
            return false;
        }
        return REPORTABLE_SALES.contains(Pair.of(trade.getSellerParty(), trade.getCurrency()));
    }
}
