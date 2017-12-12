package dj_dps_parser.parser_commands;

import java.util.HashMap;
import java.util.Map;

/**
 * SyntaxCommand is abstract class for representation of data processing script
 * commands. All commands are inherited from this class.
 * @see <a href="https://github.com/boldak/dj-dps-commands/blob/master/src">data processing script commands in js</a>
 */
public abstract class SyntaxCommand {
    /**
     * Name of command.
     */
    protected String name;

    /**
     * Synonims of this command.
     * Key - synonim.
     * Value - name of command.
     * @see Map
     */
    protected Map<String, String> synonims;

    /**
     * InternalAliases of this command.
     * Key - name of command
     * Value - argument.
     * @see Map
     */
    protected Map<String, String> internalAliases;

    /**
     * Default properties of this command.
     * Key - name of property.
     * Value - name of command.
     * @see Map
     */
    protected Map<String, String> defaultProperty;

    /**
     * Information about command.
     */
    protected String help;

    protected SyntaxCommand() {
        synonims = new HashMap<>();
        internalAliases = new HashMap<>();
        defaultProperty = new HashMap<>();
    }

    /**
     * Call execution for command.
     */
    public abstract void execute();

    /**
     *
     * @return name of command.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return map with synonims of command.
     */
    public Map<String, String> getSynonims() {
        return synonims;
    }

    /**
     *
     * @return internal aliases of command.
     */
    public Map<String, String> getInternalAliases() {
        return internalAliases;
    }

    /**
     *
     * @return default property of command.
     */
    public Map<String, String> getDefaultProperty() {
        return defaultProperty;
    }

    /**
     *
     * @return information about command.
     */
    public String getHelp() {
        return help;
    }
}
