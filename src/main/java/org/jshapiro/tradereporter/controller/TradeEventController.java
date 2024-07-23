package org.jshapiro.tradereporter.controller;

import lombok.RequiredArgsConstructor;
import org.jshapiro.tradereporter.XmlUtils;
import org.jshapiro.tradereporter.model.TradeDigest;
import org.jshapiro.tradereporter.model.TradePayload;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.jshapiro.tradereporter.reports.ReportFilter;
import org.jshapiro.tradereporter.repository.TradeSummaryRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor

public class TradeEventController {
    static final String EVENT_DIRECTORY_PATH = "classpath:events/";

    private final ReportFilter reportFilter;
    private final TradeSummaryRepository repository;
    private final ResourceLoader resourceLoader;

    @PostMapping(path = "/events", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TradeDigest>> processEvents(List<String> events) {
        List<TradeDigest> result = events.parallelStream()
                .map(this::processEvent)
                .filter(reportFilter::isReportable)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/load-events", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TradeDigest>> loadEvents() throws IOException {
        // read event xml files from FS
        List<String> eventsPayload = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            eventsPayload.add(resourceLoader.getResource( "%s/event%d.xml".formatted(EVENT_DIRECTORY_PATH, i)).getContentAsString(StandardCharsets.UTF_8));
        }

        List<TradeDigest> result = eventsPayload.parallelStream()
                .map(this::processEvent)
                .filter(reportFilter::isReportable)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    protected TradeDigest processEvent(String eventXmlString) {
        Document event = XmlUtils.buildDocument(eventXmlString);

        TradePayload tradePayload = new TradePayload(event);
        TradeSummary tradeSummary = tradePayload.tradeSummary();
        repository.saveAsync(tradeSummary);
        return tradeSummary.toTradeDigest();
    }
}
