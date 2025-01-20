package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.serviceplan.ServicePlan;
import org.poo.banking.user.serviceplan.StandardServicePlan;
import org.poo.banking.user.serviceplan.StudentServicePlan;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public final class AddUserCommand extends BankingCommand {
    private final String firstName;
    private final String lastname;
    private final String email;
    private final String birthDate;
    private final String occupation;

    public AddUserCommand(final String command, final String firstName, final String lastname,
                          final String email, String birthDate, String occupation) {
        super(command);
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.birthDate = birthDate;
        this.occupation = occupation;
    }

    @Override
    public Optional<ObjectNode> execute() {
        ServicePlan servicePlan;
        if (Objects.equals(occupation, "student")) {
            servicePlan = new StudentServicePlan();
        } else {
            servicePlan = new StandardServicePlan();
        }

        BankingManager.getInstance().addUserByFeature(email, new User(firstName, lastname, email,
                birthDate, occupation, servicePlan));
        return Optional.empty();
    }
}
