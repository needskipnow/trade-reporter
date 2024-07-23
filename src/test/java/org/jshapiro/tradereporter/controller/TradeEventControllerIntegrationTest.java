package org.jshapiro.tradereporter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeEventControllerIntegrationTest {

    final String EXPECTED_JSON = """
            [{"sellerParty":"EMU_BANK","buyerParty":"LEFT_BANK","amount":100.00,"currency":"AUD"},{"sellerParty":"EMU_BANK","buyerParty":"LEFT_BANK","amount":200.00,"currency":"AUD"},{"sellerParty":"BISON_BANK","buyerParty":"EMU_BANK","amount":500.00,"currency":"USD"},{"sellerParty":"BISON_BANK","buyerParty":"EMU_BANK","amount":600.00,"currency":"USD"}]
            """;

    MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    void testEventLoad() throws Exception {
        mockMvc.perform(post("/trade/load-events")).andExpect(status().isOk()).andExpect(content().json(EXPECTED_JSON));//"[{},{},{},{}]"));
    }


}
