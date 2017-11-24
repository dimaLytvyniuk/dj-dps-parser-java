package parser_commands;

import java.util.Map;

public abstract class SyntaxCommand {
    private String name;
    private Map<String, String> synonims;
    private Map<String, String> internalAliases;
    private Map<String, String> defaultProperty;
    private String help;

    public abstract void execute();

}
