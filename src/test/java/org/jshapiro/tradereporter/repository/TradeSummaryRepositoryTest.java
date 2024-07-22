package org.jshapiro.tradereporter.repository;

import org.jshapiro.tradereporter.model.Currency;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradeSummaryRepositoryTest {
    @Autowired TradeSummaryRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void testCrudOperations() {
        List<TradeSummary> tradeSummaries = repository.findAll();
        assertTrue(tradeSummaries.isEmpty());

        TradeSummary tradeSummary = TradeSummary.builder()
                .sellerParty("SELLER")
                .buyerParty("BUYER")
                .currency(Currency.AUD)
                .amount(BigDecimal.valueOf(100).setScale(2, RoundingMode.UP))
                .build();

        repository.save(tradeSummary);

        tradeSummaries = repository.findAll();
        assertFalse(tradeSummaries.isEmpty());
        TradeSummary tradeSummaryStored = tradeSummaries.get(0);
        assertNotNull(tradeSummaryStored.getId());
        assertEquals("SELLER", tradeSummaryStored.getSellerParty());
        assertEquals("BUYER", tradeSummaryStored.getBuyerParty());
        assertEquals(Currency.AUD, tradeSummaryStored.getCurrency());
        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.UP), tradeSummaryStored.getAmount());

        repository.delete(tradeSummaryStored);
        tradeSummaries = repository.findAll();
        assertTrue(tradeSummaries.isEmpty());
    }

}