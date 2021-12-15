package ch.uzh.softcon.four.logic;

public class IOFormatter {

    public static String formatOutput(String textBeforeTable, boolean printTable, String textAfterTable) { // Concatenate text with Table layout
        StringBuilder out = new StringBuilder();
        if (!textBeforeTable.equals("")) out.append(textBeforeTable).append("\n");

        // If table has to be printed generate table
        if (printTable) {
            // TODO Print table magic
            out.append("""
            =======================
            ||        TABLE      ||
            ||        -----      ||
            ||        Moves      ||
            ||    [0] Stay       ||
            ||    [1] Hit        ||
            ||    [2] Split      ||
            =======================
            """);
        }

        out.append(textAfterTable);
        return out.toString();
    }

    public static String formatErrorMessage(String errorMessage) { // Color the error messages in red
        final String BRIGHT_RED = "\u001B[91m";
        final String RESET = "\u001B[0m";
        return BRIGHT_RED + errorMessage + RESET;
    }
}
