package View;

public class ExitCommand extends Command {
    public ExitCommand(Integer key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
