package org.jshapiro.tradereporter.rest;

import org.jshapiro.tradereporter.model.TradeSummary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
@RequestMapping("/trade")
public class TradeEventController {

    @PostMapping(path = "/event", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public TradeSummary processEvent(String eventXmlString) {
        return null;
    }
}
