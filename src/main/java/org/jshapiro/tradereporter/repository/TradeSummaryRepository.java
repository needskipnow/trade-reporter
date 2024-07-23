package org.jshapiro.tradereporter.repository;

import org.jshapiro.tradereporter.model.TradeSummary;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public interface TradeSummaryRepository extends JpaRepository<TradeSummary, Long>, JpaSpecificationExecutor<TradeSummary> {
    Logger log = getLogger(TradeSummaryRepository.class);

    @Async
    default CompletableFuture<TradeSummary> saveAsync(TradeSummary entity) {
        try {
            return CompletableFuture.completedFuture(save(entity));
        } catch (Throwable throwable) {
            log.error("Failed to store TradeSummary: %s".formatted(entity), throwable);
            return CompletableFuture.completedFuture(null);
        }
    }
}
