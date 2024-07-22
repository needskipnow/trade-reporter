package org.jshapiro.tradereporter.controller;

import org.jshapiro.tradereporter.model.TradeDigest;
import org.jshapiro.tradereporter.reports.ReportFilter;
import org.jshapiro.tradereporter.repository.TradeSummaryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradeEventControllerTest {
    static final String DIRECTORY_PATH = "classpath:events/";

    @Autowired
    private ResourceLoader resourceLoader = null;

    @Autowired
    private ReportFilter reportFilter;

    @Autowired
    private TradeEventController controller;

    @Autowired
    TradeSummaryRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void shouldProcessEvents() throws IOException {
        // setup
        // read event xml files
        List<String> eventsPayload = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            eventsPayload.add(resourceLoader.getResource( "%s/event%d.xml".formatted(DIRECTORY_PATH, i)).getContentAsString(StandardCharsets.UTF_8));
        }

        // execute
        List<TradeDigest> result = controller.processEvents(eventsPayload);

        //verify
        assertFalse(result.isEmpty());
        result.forEach(tradeDigest -> assertTrue(reportFilter.isReportable(tradeDigest)));
    }
}