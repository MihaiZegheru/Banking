package org.poo.banking.user.tracking;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.poo.banking.user.account.Account;

import java.util.List;

@Getter
@JsonSerialize(using = TrackingNodeSerializer.class, as=TrackingNode.class)
public class TrackingNode {
    private final Integer timestamp;
    private final String description;
    private final String senderIban;
    private final String receiverIban;
    private final String amountLiteral;
    private final Double amount;
    private final String transferType;
    private final String account;
    private final String card;
    private final String cardHolder;
    private final String seller;
    private final String currency;
    private final String error;
    private final List<String> involvedAccounts;

    private final Account producer;

    public TrackingNode(TrackingNodeBuilder builder) {
        this.timestamp = builder.timestamp;
        this.description = builder.description;
        this.senderIban = builder.senderIban;
        this.receiverIban = builder.receiverIban;
        this.amountLiteral = builder.amountLiteral;
        this.amount = builder.amount;
        this.transferType = builder.transferType;
        this.account = builder.account;
        this.card = builder.card;
        this.cardHolder = builder.cardHolder;
        this.seller = builder.seller;
        this.involvedAccounts = builder.involvedAccounts;
        this.currency = builder.currency;
        this.error= builder.error;

        this.producer = builder.producer;
    }

    public static class TrackingNodeBuilder {
        private Integer timestamp;
        private String description;
        private String senderIban;
        private String receiverIban;
        private String amountLiteral;
        private Double amount;
        private String transferType;
        private String account;
        private String card;
        private String cardHolder;
        private String seller;
        private String currency;
        private String error;
        private List<String> involvedAccounts;

        private Account producer;

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

        public TrackingNodeBuilder setAmountLiteral(String amountLiteral) {
            this.amountLiteral = amountLiteral;
            return this;
        }

        public TrackingNodeBuilder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TrackingNodeBuilder setTransferType(String transferType) {
            this.transferType = transferType;
            return this;
        }

        public TrackingNodeBuilder setAccount(String account) {
            this.account = account;
            return this;
        }

        public TrackingNodeBuilder setCard(String card) {
            this.card = card;
            return this;
        }

        public TrackingNodeBuilder setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public TrackingNodeBuilder setSeller(String seller) {
            this.seller = seller;
            return this;
        }

        public TrackingNodeBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public TrackingNodeBuilder setError(String error) {
            this.error = error;
            return this;
        }

        public TrackingNodeBuilder setProducer(Account producer) {
            this.producer = producer;
            return this;
        }

        public TrackingNodeBuilder setInvolvedAccounts(List<String> involvedAccounts) {
            this.involvedAccounts = involvedAccounts;
            return this;
        }

        public TrackingNode build() {
            return new TrackingNode(this);
        }
    }
}
