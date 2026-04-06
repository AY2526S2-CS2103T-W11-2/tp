---
layout: default.md
title: "User Guide"
pageNav: 3
---

# HitList User Guide

HitList is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, HitList can get your contact management tasks done faster than traditional GUI apps.

HitList is targeted at headhunters who need to manage a large number of contacts and companies, but it can be used by anyone who needs to manage their contacts efficiently.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
2. **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
3. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-W11-2/tp/releases).
4. Copy the file to the folder you want to use as the _home folder_ for your HitList.
5. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hitlist.jar` command to run the application.
6. A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.

![Ui](images/Ui.png)

7. Type the command in the command box and press Enter to execute it.
   e.g. typing **`help`** and pressing Enter will open the help window.
8. Some example commands you can try:
   * `add /n John Doe /p 98765432` : Adds a contact named `John Doe` to the HitList.
   * `list` : Lists all contacts.
   * `delete 3` : Deletes the 3rd contact shown in the current list.
   * `clear` : Deletes all contacts.
   * `exit` : Exits the app.
9. Refer to the [Commands](#commands) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Commands

**Notes about the command format:**

* Words in `UPPER_CASE` are the parameters to be supplied by the user.
  e.g. in `add /n NAME`, `NAME` is a parameter which can be used as `add /n John Doe`
* Items in square brackets are optional.
  e.g. `edit INDEX [/n NAME] [/p PHONE]` can be used as `edit 3 /n John Doe` or as `edit 3 /p 98765432`
* Parameters can be in any order.
  e.g. if the command specifies `/n NAME /p PHONE_NUMBER`, `/p PHONE_NUMBER /n NAME` is also acceptable
* Extraneous parameters for commands that do not take in parameters, such as `help`, `list`, `exit`, and `clear`, will be ignored.
  e.g. if the command specifies `help 123`, it will be interpreted as `help`
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Candidate Contacts

#### Adding a contact : `add`

Adds a contact to the HitList.

<box header="Command Format">
    <code>add</code>
    <code>/n</code>
    <popover src="/syntax/Name.md"><code>NAME</code></popover>
    <code>/p</code>
    <popover src="/syntax/Phone.md"><code>PHONE_NUMBER</code></popover>
    <code>[/e</code>
    <code>EMAIL]</code>
    <panel header="Parameters">
        <include src="syntax/Name.md" />
        <include src="syntax/Phone.md" />
    </panel>
</box>

Examples:
<tabs>
    <tab header="John">
        <code>add /n John Doe /p 98765432</code>
        <br>
        <img width="738" height="601" alt="Add John Doe to HitList" src="images/ug/AddContact_Optional.png" />
    </tab>
    <tab header="Betsy">
        <code>add /n Betsy Crowe /p 87654321 /e betsy.crowe@gmail.com /a 321, Clementi Rd, 123465</code>
        <br>
        <img width="738" height="601" alt="Add Betsy Crowe to HitList" src="images/ug/AddContact_Full.png" />
    </tab>
</tabs>

---

#### Editing a contact : `edit`

Edits an existing contact in the HitList.

Format: `edit INDEX [/n NAME] [/p PHONE] [/e EMAIL] [/a ADDRESS]`

* Edits the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed HitList.
* The index **must be a positive integer** `1, 2, 3, …`
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:

<img width="738" height="601" alt="Before Edit of HitList" src="images/ug/BeforeEdit.png" />

* `edit 1 /p 91234567` edits the phone number of the first contact to `91234567`

<img width="738" height="601" alt="Edit 1st Contact number in HitList" src="images/ug/AfterEdit_Contact.png" />

* `edit 2 /n Betsy Crower` edits the name of the second contact to `Betsy Crower`

<img width="738" height="601" alt="Edit 2nd Contact Name to Betsy Crowe in HitList" src="images/ug/AfterEdit_Name.png" />

#### Deleting a contact : `delete`

Deletes the specified contact from HitList.

Format: `delete INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed HitList.
* The index **must be a positive integer** `1, 2, 3, …`

Examples:
* `list` followed by `delete 2` deletes the second contact in HitList

<img width="738" height="601" alt="Listing of HitList" src="images/ug/BeforeDelete.png" />
<img width="738" height="601" alt="Deleting of Contact in HitList" src="images/ug/AfterDelete_Index.png" />

* `find Irfan` followed by `delete 1` deletes the first contact in the results of the `find` command

<img width="738" height="601" alt="Find Irfan in HitList" src="images/ug/Find_Irfan.png" />
<img width="738" height="601" alt="Deleting Irfan in HitList" src="images/ug/AfterDelete.png" />

Format `delete /n CONTACT_NAME`

* Deletes the contact with the specified name from HitList.
* The contact name must exactly match an existing contact in HitList.

Example:
* `list` followed by `delete /n David Li` deletes the contact named `David Li` from HitList

<img width="738" height="601" alt="Listing of HitList" src="images/ug/BeforeDelete_ByName.png" />
<img width="738" height="601" alt="Deleting David Li in HitList" src="images/ug/AfterDelete_ByName.png" />

#### Listing all contacts : `list`

Shows a list of all contacts in the HitList.

Format: `list`

<img width="738" height="601" alt="Before Edit of HitList" src="images/ug/List.png" />

#### Locating contacts : `find`

Finds contacts whose names match any given prefix.

Format: `find [KEYWORD]...`

* Name search is case-insensitive.
  e.g. `han` matches `Hans`
* Name search uses prefix matching.
  e.g. `Han` matches `Hans`
* If multiple name keywords are given, a contact matching any one of them is returned.

Examples:
* `find John` returns `john` and `John Doe`

<img width="738" height="601" alt="Finding john" src="images/ug/Find_John.png" />

* `find alex david` returns `Alex Yeoh`, `David Li`

<img width="738" height="601" alt="Find Alex and David in HitList" src="images/ug/Find_Alex_David.png" />

### Contact Groups

#### Adding a contact group : `grpadd`

Adds a contact group to the HitList.

Format: `grpadd /g GROUP_NAME`

Examples:
* `grpadd /g Admins`

<img width="738" height="601" alt="Creation of group Admins in HitList" src="images/ug/Grpadd_Admins.png" />

* `grpadd /g Experienced`

<img width="738" height="601" alt="Creation of group Experienced in HitList" src="images/ug/Grpadd_Experienced.png" />

#### Deleting a contact group : `grpdel`

Deletes the specified contact group from HitList.

Format: `grpdel /g GROUP_NAME`

Examples:
* `grpdel /g Admins`

<img width="738" height="601" alt="Deletion of group Admins in HitList" src="images/ug/Grpadd_Experienced.png" />

* `grpdel /g Unemployed`

<img width="738" height="601" alt="Deletion of group Unemployed in HitList" src="images/ug/Grpadd_Experienced.png" />

> !NOTE
> Deleting a contact group does not delete the contacts in that group from HitList. It only deletes the group itself and the association of the contacts to that group.
> e.g. if `John Doe` is in the `Students` group, and the `Students` group is deleted, `John Doe` will still be in HitList but will no longer be associated with any contact group.
>
> Group Names are currently case-sensitive, so `Students` and `students` are considered different groups. Hence, deleting `students` will not delete the `Students` group.

#### Listing contacts in a contact group : `grplist`

Lists all the contacts who are members of a specified contact group.

Format: `grplist /g GROUP_NAME`

Examples:
* `grplist /g Students`

<img width="738" height="601" alt="List group members of Students group" src="images/ug/Grplist_Students.png" />

* `grplist /g Experienced`

<img width="738" height="601" alt="List group members of Experienced group" src="images/ug/Grplist_Students.png" />

#### Assigning a contact to a contact group : `grpassign`

Adds an existing contact to an existing contact group.

Format: `grpassign /n NAME /g GROUP_NAME`

* The contact name must exactly match an existing contact in HitList.
* The group name must exactly match an existing contact group in HitList.

Examples:
* `grpassign /n Alex Yeoh /g Experienced`

<img width="738" height="601" alt="Before Assigning Alex Yeoh to Experienced Group" src="images/ug/BeforeGrpassign_Experienced.png" />
<img width="738" height="601" alt="After Assigning Alex Yeoh to Experienced Group" src="images/ug/AfterGrpassign_Experienced.png" />

* `grpassign /n Betsy Crowe /g Students`

<img width="738" height="601" alt="Before Assigning Betsy Crowe to Students Group" src="images/ug/BeforeGrpassign_Students.png" />
<img width="738" height="601" alt="After Assigning Betsy Crowe to Students Group" src="images/ug/AfterGrpassign_Students.png" />

#### Unassigning a contact from a contact group : `grpunassign`

Removes an existing contact from an existing contact group.

Format: `grpunassign /n NAME /g GROUP_NAME`

* The contact name must exactly match an existing contact in HitList.
* The group name must exactly match an existing contact group in HitList.
* The contact must already belong to the specified group.

Examples:
* `grpunassign /n Alex Yeoh /g Experienced`
* `grpunassign /n Betsy Crowe /g Students`

### Company Profiles

#### Adding a company : `cmpadd`

Adds a company to the HitList.

Format: `cmpadd /c COMPANY_NAME /d COMPANY_DESCRIPTION`

* The company name must be unique and not the same as any existing company in HitList.
* The company description can be any string which does not include `/` or start with spaces.

Examples:
* `cmpadd /c Google /d Tech giant`

<img width="738" height="601" alt="Creation of company Google with description Tech giant" src="images/ug/Cmpadd_Google.png" />

* `cmpadd /c Meta /d Social media giant`

<img width="738" height="601" alt="Creation of company Meta with description Social media giant" src="images/ug/Cmpadd_Meta.png" />

#### Deleting a company : `cmpdel`

Deletes the specified company from HitList.

Format: `cmpdel /c COMPANY_NAME`

* The company name must be an existing company in HitList.
* The company name typed must be the exact company name registered in HitList.

Example:
* `cmpdel /c Google` deletes a company named `Google` from hitList.

<img width="738" height="601" alt="Deletion of company Meta" src="images/ug/Cmpdel_Google" />

* `cmpdel /c Meta` deletes a company named `Meta` from hitList.

<img width="738" height="601" alt="Deletion of company Meta" src="images/ug/Cmpdel_Meta" />

#### Listing all Companies : `cmplist`

Shows a list of all companies in the hitList.

Format: `cmplist`

<img width="738" height="601" alt="List all companies in HitList" src="images/ug/Cmplist.png" />

#### Adding a role to a company : `roleadd`

Adds a role to a specified existing company in the HitList.

Format: `roleadd /r ROLE_NAME /d ROLE_DESCRIPTION /c COMPANY_NAME`

* The role name must be unique within the company and not the same as any existing role in that company.
* The role description can be any string which does not include `/` or start with spaces.
* The company name must be an existing company in HitList.
* The company name typed must be the exact company name registered in HitList.

Examples:
* `roleadd /r Quality Assurance Engineer /d Ensures software products meet quality standards by developing test plans /c Google Inc.` adds a role named `Quality Assurance Engineer` to the company `Google`.

<img width="738" height="601" alt="Addition of Quality Assurance Engineer role to company Google Inc." src="images/ug/Roleadd_QualityAssurance.png" />

* `roleadd /r DevOps Engineers /d Manages infrastructure and automates deployment processes, bridging the gap between development and IT operations /c Meta Platforms, Inc.` adds a role named `DevOps Engineers` to the company `Meta`.

<img width="738" height="601" alt="Addition of DevOps Engineers role to company Meta Platforms, Inc." src="images/ug/Roleadd_Devops.png" />

#### Deleting a role from a company : `roledel`

Deletes a role from a specified existing company in the HitList.

Format: `roledel /r ROLE_NAME /c COMPANY_NAME` or `roledel INDEX /c COMPANY_NAME`

* Deletes a role from the company by either specifying the role name or the role's index number.
* The role name must be an existing role within the company.
* The index refers to the position of the role in the company's role list (1-based indexing).
* The company name must be an existing company in HitList.
* The company name typed must be the exact company name registered in HitList.

Examples:
* `roledel /r "Quality Assurance Engineer" /c Google Inc.` deletes the role named `Quality Assurance Engineer` from the company `Google Inc.`

<img width="738" height="601" alt="Deletion of Quality Assurance Engineer role from company Google Inc." src="images/ug/Roledel_QualityAssurance.png" />

* `roledel 1 /c Meta Platforms, Inc.` deletes the first role listed in the company `Meta Platforms, Inc.`'s role list.

<img width="738" height="601" alt="Deletion of first role from company Meta Platforms, Inc." src="images/ug/Roledel_Index.png" />

### Others

#### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

#### Clearing all entries : `clear`

Clears all entries from the HitList.

Format: `clear`

> [!CAUTION]
> This command deletes all contacts, contact groups, companies, and roles from the HitList. Use with caution.
> The action is irreversible and there is no confirmation prompt before the action is executed.

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

HitList data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HitList data are saved automatically as a JSON file `[JAR file location]/data/hitlist.json`.

Advanced users are welcome to update data directly by editing that data file.

**Caution:** If your changes to the data file make its format invalid, HitList will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.

Furthermore, certain edits can cause HitList to behave in unexpected ways, for example, if a value entered is outside the acceptable range. Therefore, edit the data file only if you are confident that you can update it correctly.

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?

**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HitList home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                      | Format                                                           | Examples                                                                              |
|-----------------------------|------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Getting Help**            | `help`                                                           | `help`                                                                                |
| **Add contact**             | `add /n NAME /p PHONE_NUMBER [/e EMAIL] [/a ADDRESS]`            | `add /n Betsy Crowe /p 87654321 /e betsy.crowe@gmail.com /a 321, Clementi Rd, 123465` |
| **Delete contact**          | `delete INDEX`\n`delete /n NAME`                                 | `delete 3`\n`delete /n David Li`                                                      |
| **Edit contact**            | `edit INDEX [/n NAME] [/p PHONE_NUMBER] [/e EMAIL] [/a ADDRESS]` | `edit 2 /n James Lee /e jameslee@example.com`                                         |
| **List contacts**           | `list`                                                           | `list`                                                                                |
| **Find contact(s)**         | `find [KEYWORD]...`                                              | `find John`                                                                           |
| **Add contact group**       | `grpadd /g GROUP_NAME`                                           | `grpadd /g Students`                                                                  |
| **Delete contact group**    | `grpdel /g GROUP_NAME`                                           | `grpdel /g Students`                                                                  |
| **List contact groups**     | `grplist`                                                        | `grplist`                                                                             |
| **List contacts in group**  | `grplist /g GROUP_NAME`                                          | `grplist /g Students`                                                                 |
| **Assign contact to group** | `grpassign /n NAME /g GROUP_NAME`                                | `grpassign /n Alex Yeoh /g Students`                                                  |
| **Unassign contact from group** | `grpunassign /n NAME /g GROUP_NAME`                          | `grpunassign /n Alex Yeoh /g Students`                                                |
| **Add Company**             | `cmpadd /c COMPANY_NAME /d COMPANY_DESCRIPTION`                  | `cmpadd /c Google /d Tech giant`                                                      |
| **Delete Company**          | `cmpdel /c COMPANY_NAME`                                         | `cmpdel /c Google`                                                                    |
| **List Companies**          | `cmplist`                                                        | `cmplist`                                                                             |
| **Add Role to Company**     | `roleadd /r ROLE_NAME /d ROLE_DESCRIPTION /c COMPANY_NAME`       | `roleadd /r Software Tester /d Tests provided software /c Google Inc.`                |
| **Delete Role from Company** | `roledel /r ROLE_NAME /c COMPANY_NAME` or `roledel INDEX /c COMPANY_NAME` | `roledel /r "Software Engineer" /c Google Inc.`<br>`roledel 1 /c Google Inc.` |
| **Clear**                   | `clear`                                                          | `clear`                                                                               |
| **Exit**                    | `exit`                                                           | `exit`                                                                                |

<include src="/syntax/Parameters.md" />
