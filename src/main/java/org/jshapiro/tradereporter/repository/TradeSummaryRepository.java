package org.jshapiro.tradereporter.repository;

import org.jshapiro.tradereporter.model.TradeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeSummaryRepository extends JpaRepository<TradeSummary, Long>, JpaSpecificationExecutor<TradeSummary> {

}
