package org.poo.banking.user.tracking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackingNode {
    private final Integer timestamp;
    private final String description;
    @JsonProperty("senderIBAN")
    private final String senderIban;
    @JsonProperty("receiverIBAN")
    private final String receiverIban;
    private final String amount;
    private final String transferType;

    public TrackingNode(TrackingNodeBuilder builder) {
        this.timestamp = builder.timestamp;
        this.description = builder.description;
        this.senderIban = builder.senderIban;
        this.receiverIban = builder.receiverIban;
        this.amount = builder.amount;
        this.transferType = builder.transferType;
    }

    public static class TrackingNodeBuilder {
        private Integer timestamp;
        private String description;
        private String senderIban;
        private String receiverIban;
        private String amount;
        private String transferType;

        public TrackingNodeBuilder setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TrackingNodeBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public TrackingNodeBuilder setSenderIban(String senderIban) {
            this.senderIban = senderIban;
            return this;
        }

        public TrackingNodeBuilder setReceiverIban(String receiverIban) {
            this.receiverIban = receiverIban;
            return this;
        }

        public TrackingNodeBuilder setAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public TrackingNodeBuilder setTransferType(String transferType) {
            this.transferType = transferType;
            return this;
        }

        public TrackingNode build() {
            return new TrackingNode(this);
        }
    }
}
