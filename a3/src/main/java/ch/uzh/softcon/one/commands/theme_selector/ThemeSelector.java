package ch.uzh.softcon.one.commands.theme_selector;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.theme_selector.themes.DefaultTheme;

import java.util.Stack;

public class ThemeSelector {
    private static Command[] slots = new Command[4];
    private static Stack<Command> themeStack = new Stack<>();

    public ThemeSelector() {
        Command noCommand = new NoCommand();
        for (int i = 0; i < 4; i++) {
            slots[i] = noCommand;
        }
    }

    public void setCommand(int i, Command c) {
        slots[i] = c;
    }

    public static void pressButton(int buttonNum) {
        slots[buttonNum].execute();

        //Don't push the same command twice.
        if (themeStack.isEmpty() || themeStack.peek() != slots[buttonNum]) {
            themeStack.push(slots[buttonNum]);
        }
    }

    public static void pressUndo() {
        if (!themeStack.isEmpty()) {
            themeStack.pop();
            if (themeStack.isEmpty()) {
                DefaultTheme defaultTheme = new DefaultTheme();
                DefaultThemeCommandOn defaultThemeOn = new DefaultThemeCommandOn(defaultTheme);
                defaultThemeOn.execute();
            } else {
                Command previousCommand = themeStack.peek();
                previousCommand.execute();
            }
        }
    }
}
