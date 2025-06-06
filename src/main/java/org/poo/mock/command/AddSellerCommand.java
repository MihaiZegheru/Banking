package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.seller.Seller;

import java.util.Optional;

public final class AddSellerCommand extends BankingCommand {
    private final String name;
    private final int id;
    private final String iban;
    private final String type;
    private final String cashbackStrategy;

    public AddSellerCommand(final String command, final String name, final int id,
                            final String iban, final String type, final String cashbackStrategy) {
        super(command);
        this.name = name;
        this.id = id;
        this.iban = iban;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().addSellerByFeature(name, new Seller(name, id, iban, type,
                cashbackStrategy));
        return Optional.empty();
    }
}
