package seedu.address.model;

import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class IntuitivePromptManager {
    private static int currentArgIndex; //change this to enum in future version
    private static List<String> arguments;
    private static String commandWord;
    private static boolean isIntuitiveMode;

    private static final int MIN_ARGUMENT_INDEX = 0;

    private static final String ADD_COMMAND_WORD = "add";
    private static final String ADD_NAME_INSTRUCTION = "Please enter patient's name";
    private static final String ADD_EMAIL_INSTRUCTION = "Please enter patient's email";
    private static final String ADD_PHONE_INSTRUCTION = "Please enter patient's phone number";
    private static final String ADD_ADDRESS_INSTRUCTION = "Please enter patient's address";
    private static final String ADD_TAGS_INSTRUCTION = "Please enter patient's tags, " +
            "separated by commas (with no spaces after a comma)";

    private static final String NAME_PREFIX = "n/";
    private static final String EMAIL_PREFIX = "e/";
    private static final String PHONE_PREFIX = "p/";
    private static final String ADDRESS_PREFIX = "a/";
    private static final String TAGS_PREFIX = "t/";

    private static final int ADD_MAX_ARGUMENTS = 5;
    private static final int ADD_NAME_INDEX = 0;
    private static final int ADD_EMAIL_INDEX = 1;
    private static final int ADD_PHONE_INDEX = 2;
    private static final int ADD_ADDRESS_INDEX = 3;
    private static final int ADD_TAGS_INDEX = 4;

    //logger
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);


    public IntuitivePromptManager() {
        currentArgIndex = MIN_ARGUMENT_INDEX;
        arguments = new ArrayList<>();
        commandWord = null;
        isIntuitiveMode = false;
    }

    public boolean isIntuitiveMode() {
        return isIntuitiveMode;
    }

    public void addArgument(String input) {
        if (commandWord != null) {
            arguments.add(input.trim());
            currentArgIndex++;
        } else {
            commandWord = input.trim();
            isIntuitiveMode = true;
        }

        logger.info("Intuitive Argument index: " + currentArgIndex);
        logger.info("Current arguments: " + arguments);


        if (currentArgIndex >= getMaximumArguments(commandWord)) {
            exitIntuitiveMode();
        }
    }

    public String getInstruction() {
        switch (commandWord) {
            case ADD_COMMAND_WORD:
                return retrieveAddInstruction();
            default:
                return "Invalid";
        }
    }

    public void removeArgument() {
        //assert currentArgIndex > 0
        arguments.remove(currentArgIndex-1);
        currentArgIndex--;
    }

    public void setCommandWord(String command) {
        commandWord = command;
    }

    private void exitIntuitiveMode() {
        isIntuitiveMode = false;
    }

    private String retrieveAddInstruction() {
        switch (currentArgIndex) {
            case ADD_NAME_INDEX:
                return ADD_NAME_INSTRUCTION;
            case ADD_EMAIL_INDEX:
                return ADD_EMAIL_INSTRUCTION;
            case ADD_PHONE_INDEX:
                return ADD_PHONE_INSTRUCTION;
            case ADD_ADDRESS_INDEX:
                return ADD_ADDRESS_INSTRUCTION;
            case ADD_TAGS_INDEX:
                return ADD_TAGS_INSTRUCTION;
            default:
                return "Invalid";
        }
    }

    private int getMaximumArguments(String commandWord) {
        switch (commandWord) {
            case ADD_COMMAND_WORD:
                return ADD_MAX_ARGUMENTS;
        }
        return 0;
    }

    public boolean areArgsAvailable() {
        return !arguments.isEmpty();
    }

    public String retrieveArguments() {
        switch (commandWord) {
            case ADD_COMMAND_WORD:
                return prepareArgumentsForAdd();
            default:
                return "Invalid";
        }
    }

    private void resetIntuitiveCache() {
        commandWord = null;
        arguments.clear();
        currentArgIndex = MIN_ARGUMENT_INDEX;
    }

    private String prepareArgumentsForAdd() {
        String preparedString = "";
        preparedString += ADD_COMMAND_WORD + " ";

        int index = 0;
        for (String arg : arguments) {
            preparedString += prefixAddArgument(index, arg);
            preparedString += " ";
            index++;
        }

        resetIntuitiveCache();
        return preparedString.trim();
    }

    private String prefixAddArgument(int index, String argument) {
        switch (index) {
            case ADD_NAME_INDEX:
                return NAME_PREFIX + argument;
            case ADD_EMAIL_INDEX:
                return EMAIL_PREFIX + argument;
            case ADD_PHONE_INDEX:
                return PHONE_PREFIX + argument;
            case ADD_ADDRESS_INDEX:
                return ADDRESS_PREFIX + argument;
            case ADD_TAGS_INDEX:
                String resultArg = TAGS_PREFIX + argument;
                return resultArg.replace(",", " " + TAGS_PREFIX).trim();
        }
        return "";
    }
}
