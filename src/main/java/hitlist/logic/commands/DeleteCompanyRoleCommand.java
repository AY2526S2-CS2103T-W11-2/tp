package hitlist.logic.commands;

import static hitlist.commons.util.CollectionUtil.requireAllNonNull;
import static hitlist.logic.parser.CliSyntax.PREFIX_COMPANY;
import static hitlist.logic.parser.CliSyntax.PREFIX_ROLE;
import static hitlist.ui.UiPaneVisibility.SHOW_ROLE_LIST;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import hitlist.commons.core.index.Index;
import hitlist.commons.util.ToStringBuilder;
import hitlist.logic.commands.exceptions.CommandException;
import hitlist.model.Model;
import hitlist.model.company.Company;
import hitlist.model.company.CompanyName;
import hitlist.model.company.role.Role;
import hitlist.model.company.role.RoleName;

/**
 * Deletes a role from a company profile.
 * Can delete by role name or by role index within the company.
 */
public class DeleteCompanyRoleCommand extends Command {

    public static final String COMMAND_WORD = "roledel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a role from a company profile.\n"
            + "Parameters: (by name) " + PREFIX_ROLE + " ROLE_NAME "
            + PREFIX_COMPANY + " COMPANY_NAME\n"
            + "or (by index) INDEX " + PREFIX_COMPANY + " COMPANY_NAME\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_ROLE + " Software Engineer "
            + PREFIX_COMPANY + " Google\n"
            + "Example 2: " + COMMAND_WORD + " 1 " + PREFIX_COMPANY + " Google";

    public static final String MESSAGE_SUCCESS = "Deleted role: %1$s from company: %2$s";
    public static final String MESSAGE_COMPANY_NOT_FOUND = "Company not found: %1$s";
    public static final String MESSAGE_ROLE_INDEX_OUT_OF_BOUNDS = "Invalid role index: %1$d. "
            + "Company %2$s has %3$d role(s).";
    public static final String MESSAGE_ROLE_NAME_NOT_FOUND = "No role named '%1$s' found in company: %2$s";

    private final Index roleIndex;
    private final RoleName roleName;
    private final CompanyName companyName;

    /**
     * Creates a DeleteCompanyRoleCommand to delete a role by index.
     */
    public DeleteCompanyRoleCommand(Index roleIndex, CompanyName companyName) {
        requireAllNonNull(roleIndex, companyName);
        this.roleIndex = roleIndex;
        this.roleName = null;
        this.companyName = companyName;
    }

    /**
     * Creates a DeleteCompanyRoleCommand to delete a role by name.
     */
    public DeleteCompanyRoleCommand(RoleName roleName, CompanyName companyName) {
        requireAllNonNull(roleName, companyName);
        this.roleName = roleName;
        this.roleIndex = null;
        this.companyName = companyName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Company company = findCompanyIgnoreCase(model, companyName)
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_COMPANY_NOT_FOUND, companyName)));

        Role roleToDelete;

        if (isDeleteByIndex()) {
            roleToDelete = deleteByIndex(company);
        } else {
            roleToDelete = deleteByName(company);
        }

        company.getUniqueRoleList().remove(roleToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                roleToDelete.getRoleName(), company.getName()), SHOW_ROLE_LIST);
    }

    private Optional<Company> findCompanyIgnoreCase(Model model, CompanyName targetCompanyName) {
        return model.getHitList().getCompanyList().stream()
                .filter(company -> company.getName().toString().equalsIgnoreCase(targetCompanyName.toString()))
                .findFirst();
    }

    private Role deleteByIndex(Company company) throws CommandException {
        List<Role> roles = company.getUniqueRoleList().asUnmodifiableObservableList();
        int zeroBasedIndex = roleIndex.getZeroBased();

        if (zeroBasedIndex >= roles.size()) {
            throw new CommandException(String.format(MESSAGE_ROLE_INDEX_OUT_OF_BOUNDS,
                    roleIndex.getOneBased(), company.getName(), roles.size()));
        }

        return roles.get(zeroBasedIndex);
    }

    private Role deleteByName(Company company) throws CommandException {
        List<Role> roles = company.getUniqueRoleList().asUnmodifiableObservableList();
        return roles.stream()
                .filter(role -> role.getRoleName().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_ROLE_NAME_NOT_FOUND, roleName, company.getName())
                ));
    }

    private boolean isDeleteByIndex() {
        return roleIndex != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCompanyRoleCommand)) {
            return false;
        }

        DeleteCompanyRoleCommand otherCommand = (DeleteCompanyRoleCommand) other;

        if (isDeleteByIndex() && otherCommand.isDeleteByIndex()) {
            return roleIndex.equals(otherCommand.roleIndex)
                    && companyName.equals(otherCommand.companyName);
        } else if (!isDeleteByIndex() && !otherCommand.isDeleteByIndex()) {
            return roleName.equals(otherCommand.roleName)
                    && companyName.equals(otherCommand.companyName);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("roleIndex", roleIndex)
                .add("roleName", roleName)
                .add("companyName", companyName)
                .toString();
    }
}
