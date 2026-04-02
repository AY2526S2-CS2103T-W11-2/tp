package hitlist.model.company;

import static hitlist.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import hitlist.model.company.role.Role;
import hitlist.model.company.role.UniqueRoleList;

/**
 * Represents a Company in the hitlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Company {
    private final CompanyName name;
    private final CompanyDescription description;
    private final UniqueRoleList uniqueRoleList;

    /**
     * Constructs a {@code Company} with the given name and description.
     *
     * @param name The name of the company.
     * @param description The description of the company.
     */
    public Company(CompanyName name, CompanyDescription description) {
        requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
        this.uniqueRoleList = new UniqueRoleList();
    }

    /**
     * Constructs a {@code Company} with the given name, description and list of roles.
     *
     * @param name The name of the company.
     * @param description The description of the company.
     * @param uniqueRoleList The list of roles associated with the company.
     */
    public Company(CompanyName name, CompanyDescription description, UniqueRoleList uniqueRoleList) {
        requireAllNonNull(name, description, uniqueRoleList);
        this.name = name;
        this.description = description;
        this.uniqueRoleList = uniqueRoleList;
    }

    /**
     * Gets the name of the company.
     *
     * @return The name of the company.
     */
    public CompanyName getName() {
        return this.name;
    }

    /**
     * Gets the description of the company.
     *
     * @return The description of the company.
     */
    public CompanyDescription getDescription() {
        return this.description;
    }

    /**
     * Gets the list of roles associated with the company.
     *
     * @return The list of roles associated with the company.
     */
    public UniqueRoleList getUniqueRoleList() {
        return this.uniqueRoleList;
    }

    /**
     * Returns true if the company has the specified role.
     *
     * @param role The role to check for.
     * @return True if the company has the specified role, false otherwise.
     */
    public boolean hasRole(Role role) {
        requireNonNull(role);

        return getUniqueRoleList().contains(role);
    }

    /**
     * Adds the specified role to the company.
     * The role must not already exist in the company.
     *
     * @param role The role to be added.
     */
    public void addRole(Role role) {
        requireNonNull(role);

        getUniqueRoleList().add(role);
        assert getUniqueRoleList().contains(role) : "Role list should contain the added role";
    }

    /**
     * Replaces {@code target} with {@code editedRole} in this company.
     * {@code target} must exist in this company.
     */
    public void setRole(Role target, Role editedRole) {
        requireAllNonNull(target, editedRole);
        uniqueRoleList.setRole(target, editedRole);
        assert uniqueRoleList.contains(editedRole) : "Role list should contain the newly edited role";
    }

    /**
     * Removes the specified role from this company.
     * The role must exist in this company.
     *
     * @param role The role to be removed.
     */
    public void removeRole(Role role) {
        requireNonNull(role);

        getUniqueRoleList().remove(role);
        assert !getUniqueRoleList().contains(role) : "Role list should no longer contain the removed role";
    }

    /**
    * Returns true if both companies have the same name.
    * This defines a weaker notion of equality between two companies.
    *
    * @param otherCompany The company to compare with this company.
    * @return True if both companies have the same name, false otherwise.
    */
    public boolean isSameCompany(Company otherCompany) {
        if (otherCompany == this) {
            return true;
        }

        return otherCompany != null
                && this.name.equals(otherCompany.name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return this.name.equals(otherCompany.name)
                && this.description.equals(otherCompany.description)
                && this.uniqueRoleList.equals(otherCompany.uniqueRoleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, uniqueRoleList);
    }

    @Override
    public String toString() {
        return "companyName=" + name + ", companyDescription=" + description + ", roles=" + uniqueRoleList;
    }
}
