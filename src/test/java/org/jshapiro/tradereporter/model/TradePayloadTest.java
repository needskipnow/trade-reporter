package org.jshapiro.tradereporter.model;

import org.jshapiro.tradereporter.XmlUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.jshapiro.tradereporter.XmlUtils.XPATH_FACTORY;
import static org.jshapiro.tradereporter.model.Currency.AUD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TradePayloadTest {
    static final String XML = """
<?xml version="1.0" encoding="utf-8"?>
                                            <!--View is confirmation-->
                                            <!--Version is 5-0-->
                                            <!--NS is http://www.fpml.org/FpML-5/confirmation-->
                                            <!--
                                            == Copyright (c) 2002-2010. All rights reserved.
                                            == Financial Products Markup Language is subject to the FpML public license.
                                            == A copy of this license is available at http://www.fpml.org/license/license.html-->
                                            <!--5.0:Message type is a Root of the message-->
                                            <!--5.0 Messaging: changed <requestTradeConfirmation> -><requestConfirmation>-->
                                            <requestConfirmation xmlns="http://www.fpml.org/FpML-5/confirmation"
                                                                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" fpmlVersion="5-0"
                                                                 xsi:schemaLocation="http://www.fpml.org/FpML-5/confirmation ../../fpml-main-5-0.xsd">
                                                <header>
                                                    <messageId messageIdScheme="http://www.fpml.org/msg-id">123</messageId>
                                                    <sentBy>BIC1</sentBy>
                                                    <sendTo>BIC16C</sendTo>
                                                    <creationTimestamp>2009-01-27T15:38:00-00:00</creationTimestamp>
                                                </header>
                                                <!--5.0 Messaging: added
                                                                <isCorrection>
                                                                <correlationId>
                                                                <sequenceNumber>
                                                        -->
                                                <isCorrection>false</isCorrection>
                                                <correlationId correlationIdScheme="http://www.example.com/conversationId">CW/2009/01/27/123</correlationId>
                                                <sequenceNumber>1</sequenceNumber>
                                                <trade>
                                                    <tradeHeader>
                                                        <partyTradeIdentifier>
                                                            <partyReference href="partyA"/>
                                                            <tradeId tradeIdScheme="http://www.swapswire.com/spec/2001/trade-id-1-0">166555</tradeId>
                                                        </partyTradeIdentifier>
                                                        <partyTradeIdentifier>
                                                            <partyReference href="partyB"/>
                                                            <tradeId tradeIdScheme="http://www.swapswire.com/spec/2001/trade-id-1-0">166555</tradeId>
                                                        </partyTradeIdentifier>
                                                        <tradeDate>2017-06-01</tradeDate>
                                                    </tradeHeader>
                                                    <varianceOptionTransactionSupplement>
                                                        <buyerPartyReference href="LEFT_BANK"/>
                                                        <sellerPartyReference href="EMU_BANK"/>
                                                        <optionType>Call</optionType>
                                                        <equityPremium>
                                                            <payerPartyReference href="partyA"/>
                                                            <receiverPartyReference href="partyB"/>
                                                            <paymentAmount>
                                                                <currency>AUD</currency>
                                                                <amount>100.00</amount>
                                                            </paymentAmount>
                                                            <paymentDate>
                                                                <unadjustedDate>2009-01-29</unadjustedDate>
                                                                <dateAdjustments>
                                                                    <businessDayConvention>NotApplicable</businessDayConvention>
                                                                </dateAdjustments>
                                                            </paymentDate>
                                                        </equityPremium>
                                                        <equityExercise>
                                                            <equityEuropeanExercise>
                                                                <expirationDate>
                                                                    <adjustableDate>
                                                                        <unadjustedDate>2015-03-20</unadjustedDate>
                                                                        <dateAdjustments>
                                                                            <businessDayConvention>NotApplicable</businessDayConvention>
                                                                        </dateAdjustments>
                                                                    </adjustableDate>
                                                                </expirationDate>
                                                                <equityExpirationTimeType>OSP</equityExpirationTimeType>
                                                            </equityEuropeanExercise>
                                                            <automaticExercise>true</automaticExercise>
                                                            <equityValuation id="valuationDate">
                                                                <futuresPriceValuation>true</futuresPriceValuation>
                                                            </equityValuation>
                                                            <settlementCurrency>GBP</settlementCurrency>
                                                            <settlementType>Cash</settlementType>
                                                        </equityExercise>
                                                        <varianceSwapTransactionSupplement>
                                                            <varianceLeg>
                                                                <payerPartyReference href="partyA"/>
                                                                <receiverPartyReference href="partyB"/>
                                                                <underlyer>
                                                                    <singleUnderlyer>
                                                                        <index>
                                                                            <instrumentId
                                                                                    instrumentIdScheme="http://www.fpml.org/spec/2003/instrument-id-Reuters-RIC-1-0">
                                                                                .FTSE
                                                                            </instrumentId>
                                                                            <description>FTSE 100 INDEX</description>
                                                                            <exchangeId exchangeIdScheme="http://www.fpml.org/spec/2002/exchange-id-REC-1-0">LSE
                                                                            </exchangeId>
                                                                            <relatedExchangeId exchangeIdScheme="http://www.fpml.org/spec/2002/exchange-id-REC-1-0">
                                                                                LIF
                                                                            </relatedExchangeId>
                                                                        </index>
                                                                    </singleUnderlyer>
                                                                </underlyer>
                                                                <settlementType>Cash</settlementType>
                                                                <settlementDate>
                                                                    <relativeDate>
                                                                        <periodMultiplier>2</periodMultiplier>
                                                                        <period>D</period>
                                                                        <dayType>CurrencyBusiness</dayType>
                                                                        <businessDayConvention>NotApplicable</businessDayConvention>
                                                                        <dateRelativeTo href="valuationDate"/>
                                                                    </relativeDate>
                                                                </settlementDate>
                                                                <settlementCurrency>GBP</settlementCurrency>
                                                                <valuation>
                                                                    <valuationDate>
                                                                        <adjustableDate>
                                                                            <unadjustedDate>2011-03-18</unadjustedDate>
                                                                            <dateAdjustments>
                                                                                <businessDayConvention>NotApplicable</businessDayConvention>
                                                                            </dateAdjustments>
                                                                        </adjustableDate>
                                                                    </valuationDate>
                                                                    <futuresPriceValuation>true</futuresPriceValuation>
                                                                </valuation>
                                                                <amount>
                                                                    <observationStartDate>
                                                                        <adjustableDate>
                                                                            <unadjustedDate>2009-01-27</unadjustedDate>
                                                                            <dateAdjustments>
                                                                                <businessDayConvention>NotApplicable</businessDayConvention>
                                                                            </dateAdjustments>
                                                                        </adjustableDate>
                                                                    </observationStartDate>
                                                                    <variance>
                                                                        <closingLevel>true</closingLevel>
                                                                        <expectedN>542</expectedN>
                                                                        <varianceAmount>
                                                                            <currency>AUD</currency>
                                                                            <amount>51.99</amount>
                                                                        </varianceAmount>
                                                                        <varianceStrikePrice>225</varianceStrikePrice>
                                                                        <varianceCap>false</varianceCap>
                                                                        <vegaNotionalAmount>1000000</vegaNotionalAmount>
                                                                    </variance>
                                                                </amount>
                                                            </varianceLeg>
                                                            <multipleExchangeIndexAnnexFallback>false</multipleExchangeIndexAnnexFallback>
                                                        </varianceSwapTransactionSupplement>
                                                    </varianceOptionTransactionSupplement>
                                                    <documentation>
                                                        <masterConfirmation>
                                                            <masterConfirmationType>ISDA2007VarianceSwapEuropeanRev1</masterConfirmationType>
                                                            <masterConfirmationDate>2009-01-07</masterConfirmationDate>
                                                            <masterConfirmationAnnexDate>2009-01-07</masterConfirmationAnnexDate>
                                                            <masterConfirmationAnnexType>ISDA2007VarianceOptionEuropean</masterConfirmationAnnexType>
                                                        </masterConfirmation>
                                                    </documentation>
                                                </trade>
                                                <party id="partyA">
                                                    <partyId>BIC1</partyId>
                                                    <partyName>SwapsWire Ltd (LE)</partyName>
                                                </party>
                                                <party id="partyB">
                                                    <partyId>BIC6</partyId>
                                                    <partyName>Rusty</partyName>
                                                </party>
                                            </requestConfirmation>
          """;

    @Test
    void shouldParseEventXml() {
        Document event = XmlUtils.buildDocument(XML);

        TradePayload tradePayload = new TradePayload(event);
        TradeSummary tradeSummary = tradePayload.tradeSummary();
        assertNotNull(tradeSummary);
        assertEquals("EMU_BANK", tradeSummary.getSellerParty());
        assertEquals("LEFT_BANK", tradeSummary.getBuyerParty());
        assertEquals(AUD, tradeSummary.getCurrency());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.UP), tradeSummary.getAmount());
    }

    @Test
    void shouldParseXml() throws Exception {
        Document doc = XmlUtils.buildDocument(XML);
        XPath xpath = XPATH_FACTORY.newXPath();
        XPathExpression xpathExpr = xpath.compile("//sentBy");
        String result = getText(xpathExpr, doc);
        assertEquals("BIC1", result);
    }

    private String getText(final XPathExpression xPathExpression, Document document) {
        try {
            final Node node = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);

            if (null != node) {
                return node.getTextContent();
            }
        } catch (final XPathExpressionException e) {
            // intentionally ignored
        }
        return null;
    }
}