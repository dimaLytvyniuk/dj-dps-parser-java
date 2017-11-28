package parser_commands;

import org.json.JSONObject;

public class ExecutionCommand {
    private SyntaxCommand syntaxCommand;
    private JSONObject params;

    public ExecutionCommand(SyntaxCommand syntaxCommand, String params) {
        this.syntaxCommand = syntaxCommand;
        this.params = new JSONObject(params);
    }

    public SyntaxCommand getSyntaxCommand() {
        return syntaxCommand;
    }

    public JSONObject getParams() {
        return params;
    }
}
