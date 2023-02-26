package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private Map<Integer, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    public void show() {
        System.out.println("TOY LANGUAGE INTERPRETER\n");
        while (true) {
            this.printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command command = null;

            try {
                int option = Integer.parseInt(key);
                command = this.commands.get(option);
            }
            catch (NumberFormatException ignored) { }

            if (command == null) {
                System.out.println("Invalid option!");
            }
            else {
                command.execute();
            }
        }
    }

    private void printMenu() {
        System.out.println("MENU:");
        for (Command command: this.commands.values()) {
            String line = String.format("%4s. %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }
}
