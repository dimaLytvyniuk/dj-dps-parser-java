package parser_commands;

import java.util.HashMap;
import java.util.Map;

public abstract class SyntaxCommand {
    protected String name;
    protected Map<String, String> synonims;
    protected Map<String, String> internalAliases;
    protected Map<String, String> defaultProperty;
    protected String help;

    protected SyntaxCommand() {
        synonims = new HashMap<>();
        internalAliases = new HashMap<>();
        defaultProperty = new HashMap<>();
    }

    public abstract void execute();

    public String getName() {
        return name;
    }

    public Map<String, String> getSynonims() {
        return synonims;
    }

    public Map<String, String> getInternalAliases() {
        return internalAliases;
    }

    public Map<String, String> getDefaultProperty() {
        return defaultProperty;
    }

    public String getHelp() {
        return help;
    }
}
