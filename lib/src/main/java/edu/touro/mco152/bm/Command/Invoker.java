package edu.touro.mco152.bm.Command;
/**
 * A class that invokes commands.
 */
public class Invoker {
    private Command command;

    /**
     * Sets the command to be invoked.
     *
     * @param command the command to be invoked
     * @return the invoker object for method chaining
     */
    public Invoker setCommand(Command command) {
        this.command = command;
        return this;
    }

    /**
     * Invokes the set command.
     * Honestly, such a cool method name sounds like I'm part of a cult.
     */
    public void invoke() {
        command.run();
    }
}
