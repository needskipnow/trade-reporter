package org.jshapiro.tradereporter.controller;

import lombok.RequiredArgsConstructor;
import org.jshapiro.tradereporter.XmlUtils;
import org.jshapiro.tradereporter.model.TradeDigest;
import org.jshapiro.tradereporter.model.TradePayload;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.jshapiro.tradereporter.reports.ReportFilter;
import org.jshapiro.tradereporter.repository.TradeSummaryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeEventController {

    private final ReportFilter reportFilter;
    private final TradeSummaryRepository repository;

    @PostMapping(path = "/events", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TradeDigest> processEvents(List<String> events) {
        return events.parallelStream()
                .map(this::processEvent)
                .filter(reportFilter::isReportable)
                .collect(Collectors.toList());
    }

    protected TradeDigest processEvent(String eventXmlString) {
        Document event = XmlUtils.buildDocument(eventXmlString);

        TradePayload tradePayload = new TradePayload(event);
        TradeSummary tradeSummary = tradePayload.tradeSummary();
        repository.saveAsync(tradeSummary);
        return tradeSummary.toTradeDigest();
    }
}
