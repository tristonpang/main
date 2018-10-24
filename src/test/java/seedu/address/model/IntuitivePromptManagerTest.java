package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Role;

public class IntuitivePromptManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private IntuitivePromptManager intuitivePromptManager = new IntuitivePromptManager();

    @Test
    public void isIntuitiveMode_onCreation_returnsFalse() {
        assertFalse(intuitivePromptManager.isIntuitiveMode());
    }

    @Test
    public void addArgument_addCommandWord_addSuccessful() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        assertTrue(intuitivePromptManager.isIntuitiveMode());
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void getInstruction_getNextRequiredAddInstruction_getCorrectInstruction() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        assertEquals(IntuitivePromptManager.ADD_ROLE_INSTRUCTION, intuitivePromptManager.getInstruction());
    }

    @Test
    public void addArgument_addFirstArgument_addSuccessful() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(IntuitivePromptManager.PATIENT_ARG_IDENTIFIER);
        assertTrue(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void retrieveArguments_addPatientWithTags_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(IntuitivePromptManager.PATIENT_ARG_IDENTIFIER);
        intuitivePromptManager.addArgument("John Doe");
        intuitivePromptManager.addArgument("95592345");
        intuitivePromptManager.addArgument("doe@gmail.com");
        intuitivePromptManager.addArgument("Blk 123 Smith Street");
        intuitivePromptManager.addArgument("vegetarian,prefersTablets");
        intuitivePromptManager.addArgument("S2345123A");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertTrue(retrievedArguments.equals(AddCommand.COMMAND_WORD + " "
                + PREFIX_ROLE + "patient "
                + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "95592345 "
                + PREFIX_EMAIL + "doe@gmail.com "
                + PREFIX_ADDRESS + "Blk 123 Smith Street "
                + PREFIX_TAG + "vegetarian "
                + PREFIX_TAG + "prefersTablets "
                + PREFIX_PATIENT_NRIC + "S2345123A"));
    }

    @Test
    public void retrieveArguments_addPatientWithoutTags_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(IntuitivePromptManager.PATIENT_ARG_IDENTIFIER);
        intuitivePromptManager.addArgument("John Doe");
        intuitivePromptManager.addArgument("95592345");
        intuitivePromptManager.addArgument("doe@gmail.com");
        intuitivePromptManager.addArgument("Blk 123 Smith Street");
        intuitivePromptManager.addArgument("//");
        intuitivePromptManager.addArgument("S2345123A");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, AddCommand.COMMAND_WORD + " "
                + PREFIX_ROLE + "patient "
                + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "95592345 "
                + PREFIX_EMAIL + "doe@gmail.com "
                + PREFIX_ADDRESS + "Blk 123 Smith Street "
                + PREFIX_PATIENT_NRIC + "S2345123A");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void addArgument_invalidArgument_throwsCommandException() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Role.MESSAGE_ROLE_CONSTRAINTS
                + "\n" + IntuitivePromptManager.ADD_ROLE_INSTRUCTION);
        intuitivePromptManager.addArgument("!@#$%");
    }

    @Test
    public void retrieveArguments_editPerson_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(EditCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("2");
        intuitivePromptManager.addArgument("1 3"); //edit name and email
        intuitivePromptManager.addArgument("Jane Watson");
        intuitivePromptManager.addArgument("watson@gmail.com");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, "edit 2 n/Jane Watson e/watson@gmail.com");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void retrieveArguments_clearPersonTags_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(EditCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("2");
        intuitivePromptManager.addArgument("5"); //edit tags
        intuitivePromptManager.addArgument(IntuitivePromptManager.EDIT_CLEAR_TAGS_COMMAND);

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, "edit 2 t/");
        assertFalse(intuitivePromptManager.areArgsAvailable());

    }
}
