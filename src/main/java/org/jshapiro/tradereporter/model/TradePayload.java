package org.jshapiro.tradereporter.model;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

import static org.jshapiro.tradereporter.XmlUtils.XPATH_FACTORY;


public class TradePayload {
    private static XPath XPATH = XPATH_FACTORY.newXPath();
    private final XPathExpression SELLER_PARTY_XPATH;
    private final XPathExpression BUYER_PARTY_XPATH;
    private final XPathExpression CURRENCY_XPATH;
    private final XPathExpression AMOUNT_XPATH;
    private final XPathExpression TRADE_DATE_XPATH;

    private Document document;

    @SneakyThrows
    public TradePayload(Document payloadXmlDocument) {
        this.document = payloadXmlDocument;
        SELLER_PARTY_XPATH = XPATH.compile("//sellerPartyReference/@href");
        BUYER_PARTY_XPATH = XPATH.compile("//buyerPartyReference/@href");
        CURRENCY_XPATH = XPATH.compile("//paymentAmount/currency");
        AMOUNT_XPATH = XPATH.compile("//paymentAmount/amount");
        TRADE_DATE_XPATH = XPATH.compile("//tradeDate");
    }

    public String sellerParty() {
        return getText(SELLER_PARTY_XPATH);
    }

    public String buyerParty() {
        return getText(BUYER_PARTY_XPATH);
    }

    public Currency currency() {
        return Currency.valueOf(getText(CURRENCY_XPATH));
    }

    public BigDecimal amount() {
        return new BigDecimal(Objects.requireNonNull(getText(AMOUNT_XPATH)));
    }

    public LocalDate tradeDate() {
        return LocalDate.parse(Objects.requireNonNull(getText(TRADE_DATE_XPATH)));
    }

    public TradeSummary tradeSummary() {
        return new TradeSummary(
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

    public String getText(final XPathExpression xpath) {
        try {
            final Node node = (Node) xpath.evaluate(document, XPathConstants.NODE);

            if (null != node) {
                return node.getTextContent();
            }
        } catch (final XPathExpressionException e) {
            // intentionally ignored
        }
        return null;
    }
}
