package org.jshapiro.tradereporter.model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public record TradePayload (Document document, XPath xpath) {
    private static final String SELLER_PARTY_XPATH = "//sellerPartyReference/@href";
    private static final String BUYER_PARTY_XPATH = "//buyerPartyReference/@href";
    private static final String CURRENCY = "//paymentAmount/currency";
    private static final String AMOUNT = "//paymentAmount/amount";
    private static final String TRADE_DATE ="//tradeDate" ;
    private static final String CREATED_ON_TIMESTAMP = "//createdOnTimestamp";

    public String sellerParty() {
        return getText(SELLER_PARTY_XPATH);
    }

    public String buyerParty() {
        return getText(BUYER_PARTY_XPATH);
    }

    public Currency currency() {
        return Currency.valueOf(getText(CURRENCY));
    }

    public BigDecimal amount() {
        return new BigDecimal(Objects.requireNonNull(getText(AMOUNT)));
    }

    public LocalDate tradeDate() {
        return LocalDate.parse(Objects.requireNonNull(getText(TRADE_DATE)));
    }

    public Timestamp createdOnTimestamp() {
        return Timestamp.valueOf(Objects.requireNonNull(getText(CREATED_ON_TIMESTAMP)));
    }

    public TradeSummary tradeSummary() {
        return new TradeSummary(
                createdOnTimestamp(),
                tradeDate(),
                sellerParty(),
                buyerParty(),
                currency(),
                amount()
        );
    }

    public String getText(final String template, final Object... args) {
        return getText(String.format(template, args));
    }

    public String getText(final String xpath) {
        try {
            final Node node = (Node) this.xpath.compile(xpath).evaluate(document, XPathConstants.NODE);

            if (null != node) {
                return node.getTextContent();
            }
        } catch (final XPathExpressionException e) {
            // intentionally ignored
        }
        return null;
    }
}
