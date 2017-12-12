package dj_dps_parser.parser_commands;

import org.json.JSONObject;

/**
 * Class that represent result of working parser and consist reference on concrete SyntaxCommand
 * and params like json object for it.
 * @see SyntaxCommand
 * @see JSONObject
 */
public class ExecutionCommand {
    /**
     * Reference on concrete SyntaxCommand
     */
    private SyntaxCommand syntaxCommand;

    /**
     * Params like json object
     */
    private JSONObject params;

    /**
     * Construtor to initialize fields
     * @param syntaxCommand Reference on concrete SyntaxCommand
     * @param params json line of params for syntaxCommand
     */
    public ExecutionCommand(SyntaxCommand syntaxCommand, String params) {
        this.syntaxCommand = syntaxCommand;
        this.params = new JSONObject(params);
    }

    /**
     *
     * @return Reference on concrete SyntaxCommand
     */
    public SyntaxCommand getSyntaxCommand() {
        return syntaxCommand;
    }

    /**
     *
     * @return params for concrete SyntaxCommand
     */
    public JSONObject getParams() {
        return params;
    }
}
