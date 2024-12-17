package org.poo.banking.user.tracking;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public final class TrackingNodeSerializer extends JsonSerializer<TrackingNode> {
    @Override
    public void serialize(final TrackingNode trackingNode, final JsonGenerator gen,
                          final SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        if (trackingNode.getTimestamp() != null) {
            gen.writeNumberField("timestamp", trackingNode.getTimestamp());
        }
        if (trackingNode.getDescription() != null) {
            gen.writeStringField("description", trackingNode.getDescription());
        }
        if (trackingNode.getSenderIban() != null) {
            gen.writeStringField("senderIBAN", trackingNode.getSenderIban());
        }
        if (trackingNode.getReceiverIban() != null) {
            gen.writeStringField("receiverIBAN", trackingNode.getReceiverIban());
        }
        if (trackingNode.getAmountLiteral() != null) {
            gen.writeStringField("amount", trackingNode.getAmountLiteral());
        }
        if (trackingNode.getAmount() != null) {
            gen.writeNumberField("amount", trackingNode.getAmount());
        }
        if (trackingNode.getTransferType() != null) {
            gen.writeStringField("transferType", trackingNode.getTransferType());
        }
        if (trackingNode.getAccount() != null) {
            gen.writeStringField("account", trackingNode.getAccount());
        }
        if (trackingNode.getCard() != null) {
            gen.writeStringField("card", trackingNode.getCard());
        }
        if (trackingNode.getCardHolder() != null) {
            gen.writeStringField("cardHolder", trackingNode.getCardHolder());
        }
        if (trackingNode.getSeller() != null) {
            gen.writeStringField("commerciant", trackingNode.getSeller());
        }
        if (trackingNode.getCurrency() != null) {
            gen.writeStringField("currency", trackingNode.getCurrency());
        }
        if (trackingNode.getError() != null) {
            gen.writeStringField("error", trackingNode.getError());
        }
        if (trackingNode.getInvolvedAccounts() != null) {
            gen.writeObjectField("involvedAccounts", trackingNode.getInvolvedAccounts());
        }
        gen.writeEndObject();
    }
}
