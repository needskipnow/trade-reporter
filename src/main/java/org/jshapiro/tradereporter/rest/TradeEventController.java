package org.jshapiro.tradereporter.rest;

import lombok.RequiredArgsConstructor;
import org.jshapiro.tradereporter.XmlUtils;
import org.jshapiro.tradereporter.model.TradePayload;
import org.jshapiro.tradereporter.model.TradeSummary;
import org.jshapiro.tradereporter.reports.ReportFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import static org.jshapiro.tradereporter.XmlUtils.XPATH_FACTORY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeEventController {

    private final ReportFilter reportFilter;

    @PostMapping(path = "/event", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public TradeSummary processEvent(String eventXmlString) {
        Document event = XmlUtils.buildDocument(eventXmlString);

        TradePayload tradePayload = new TradePayload(event, XPATH_FACTORY.newXPath());
        TradeSummary tradeSummary = tradePayload.tradeSummary();

        return reportFilter.isReportable(tradeSummary) ? tradeSummary : null;
    }

}
