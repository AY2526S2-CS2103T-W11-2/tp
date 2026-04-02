package hitlist.logic.commands;

import static hitlist.logic.parser.CliSyntax.PREFIX_COMPANY;
import static hitlist.ui.UiPaneVisibility.SHOW_COMPANY_LIST;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import hitlist.commons.util.ToStringBuilder;
import hitlist.logic.Messages;
import hitlist.logic.commands.exceptions.CommandException;
import hitlist.model.Model;
import hitlist.model.company.Company;
import hitlist.model.company.CompanyName;

/**
 * Deletes a company identified using its displayed company name from HitList.
 */
public class DeleteCompanyCommand extends Command {

    public static final String COMMAND_WORD = "cmpdel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the company identified by the company name used in the displayed company list.\n"
            + "Parameters: COMPANY_NAME (must already exist in the list)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COMPANY + " Google";

    public static final String MESSAGE_COMPANY_NOT_FOUND = "Company not found: %1$s";
    public static final String MESSAGE_DELETE_COMPANY_SUCCESS = "Deleted Company: %1$s";

    private final CompanyName companyName;

    /**
     * Creates a DeleteCompanyCommand to delete the specified {@code Company}.
     *
     * @param companyName The name of the company to be deleted.
     */
    public DeleteCompanyCommand(CompanyName companyName) {
        requireNonNull(companyName);
        this.companyName = companyName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Company companyToDelete = findCompanyIgnoreCase(model, companyName)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_COMPANY_NOT_FOUND, companyName)));

        model.deleteCompany(companyToDelete);

        return new CommandResult(
                String.format(MESSAGE_DELETE_COMPANY_SUCCESS,
                        Messages.formatCompany(companyToDelete)), SHOW_COMPANY_LIST);
    }

    private Optional<Company> findCompanyIgnoreCase(Model model, CompanyName targetCompanyName) {
        return model.getHitList().getCompanyList().stream()
                .filter(company -> company.getName().toString().equalsIgnoreCase(targetCompanyName.toString()))
                .findFirst();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCompanyCommand)) {
            return false;
        }

        DeleteCompanyCommand otherDeleteCompanyCommand = (DeleteCompanyCommand) other;
        return companyName.equals(otherDeleteCompanyCommand.companyName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("companyName", companyName)
                .toString();
    }
}
