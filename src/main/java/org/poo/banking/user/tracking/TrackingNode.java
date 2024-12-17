package org.poo.banking.user.tracking;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.poo.banking.user.account.Account;

import java.util.List;

@Getter
@JsonSerialize(using = TrackingNodeSerializer.class, as = TrackingNode.class)
public final class TrackingNode {
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

    public TrackingNode(final TrackingNodeBuilder builder) {
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
        this.error = builder.error;

        this.producer = builder.producer;
    }

    public static final class TrackingNodeBuilder {
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

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setTimestamp(final Integer timestampValue) {
            this.timestamp = timestampValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setDescription(final String descriptionValue) {
            this.description = descriptionValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setSenderIban(final String senderIbanValue) {
            this.senderIban = senderIbanValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setReceiverIban(final String receiverIbanValue) {
            this.receiverIban = receiverIbanValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setAmountLiteral(final String amountLiteralValue) {
            this.amountLiteral = amountLiteralValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setAmount(final double amountValue) {
            this.amount = amountValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setTransferType(final String transferTypeValue) {
            this.transferType = transferTypeValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setAccount(final String accountValue) {
            this.account = accountValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setCard(final String cardValue) {
            this.card = cardValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setCardHolder(final String cardHolderValue) {
            this.cardHolder = cardHolderValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setSeller(final String sellerValue) {
            this.seller = sellerValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setCurrency(final String currencyValue) {
            this.currency = currencyValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setError(final String errorValue) {
            this.error = errorValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setProducer(final Account producerValue) {
            this.producer = producerValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNodeBuilder setInvolvedAccounts(final List<String> involvedAccountsValue) {
            this.involvedAccounts = involvedAccountsValue;
            return this;
        }

        /**
         * Builder setter.
         */
        public TrackingNode build() {
            return new TrackingNode(this);
        }
    }
}
