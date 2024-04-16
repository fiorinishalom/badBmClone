package edu.touro.mco152.bm.Command;

public class Invoker {
    private Command command;

    public Invoker setCommand(Command command) {
        this.command = command;
        return this;
    }

    /**
     * Honestly, such a cool method name.
     */
    public void invoke() {
        command.run();
    }
}
