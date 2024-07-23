package org.jshapiro.tradereporter.repository;

import org.jshapiro.tradereporter.model.Currency;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
class TradeSummaryRepositoryTest {
    @Autowired TradeSummaryRepository repository;

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

    @Test
    void testSaveAsync() throws ExecutionException, InterruptedException {
        // setup
        List<TradeSummary> tradeSummaries = repository.findAll();
        assertTrue(tradeSummaries.isEmpty());

        TradeSummary tradeSummary = TradeSummary.builder()
                .sellerParty("SELLER")
                .buyerParty("BUYER")
                .currency(Currency.AUD)
                .amount(BigDecimal.valueOf(100).setScale(2, RoundingMode.UP))
                .build();

        //execute
        CompletableFuture<TradeSummary> future = repository.saveAsync(tradeSummary);
        TradeSummary tradeSummaryStored = future.get();
        assertNotNull(tradeSummaryStored.getId());
        assertEquals("SELLER", tradeSummaryStored.getSellerParty());
        assertEquals("BUYER", tradeSummaryStored.getBuyerParty());
        assertEquals(Currency.AUD, tradeSummaryStored.getCurrency());
        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.UP), tradeSummaryStored.getAmount());
    }

}