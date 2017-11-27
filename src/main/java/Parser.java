import exceptions.ParserError;
import parser_commands.ExecutionCommand;
import parser_commands.SyntaxCommand;
import utils.LineMapper;
import utils.ParserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class Parser {
    private static Pattern valuesRE = Pattern.compile("'((?:\\\\[\'bfnrt/\\\\]|\\\\u[a-fA-F0-9]{4}|[^\'\\\\])*)'|\"((?:\\\\[\"bfnrt/\\\\]|\\\\u[a-fA-F0-9]{4}|[^\"\\\\])*)\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern lineRE = Pattern.compile("[\r\n\t\\s]*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);


    private static Pattern lineCommentRE = Pattern.compile("//[\\w\\S .\t:,;\'\"(){}\\[\\]0-9-_]*(?:[\n\r]*)", Pattern.CASE_INSENSITIVE);
    private static Pattern inlineCommentRE = Pattern.compile("/\\*[\\w\\W\\\\b\\.\t:,;\'\"\\(\\)\\{\\}\\[\\]\\*0-9-_]*(?:\\*/)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern commandSplitRE = Pattern.compile("(\\))([a-zA-Z@])", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern nonbrackedParamsRE = Pattern.compile("\\(([\\w\\\\b\\.\t:,\'\"0-9-_]+[\\w\\\\b\\.\t:,\'\"\\[\\]\\^0-9-_]*)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern propertyNameRE = Pattern.compile("((@[a-zA-Z\\-_\\.]+|[a-zA-Z\\-_\\.]+)(?=[\\(\\)\\{\\}:\\[\\]\\s]+))", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern emptyPropsListRE = Pattern.compile("\\(\\s*\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern defaultValueRE = Pattern.compile(":\\{\\^*[0-9]+\\};", Pattern.CASE_INSENSITIVE);
    private static Pattern defaultStoredValueRE = Pattern.compile(":\\^[0-9]+;", Pattern.CASE_INSENSITIVE);
    private static Pattern urlLookup = Pattern.compile("\\^[0-9]+", Pattern.CASE_INSENSITIVE);
    private static Pattern commandNameRE = Pattern.compile("\"@*([a-zA-Z0_-]+[a-zA-Z0-9_-]*\\.*)+\":", Pattern.CASE_INSENSITIVE);
    private static Pattern paramsRE = Pattern.compile(":[\\{\\^\\[]+[a-zA-Z0-9_:\",\\^\\{\\}\\[\\]-]*[\\}\\]]+;*|:\\^[0-9]+;*", Pattern.CASE_INSENSITIVE);

    private static Pattern scriptRE = Pattern.compile("(\\<\\?([^?]|(\\?+[^?\\>]))*\\?\\>)");

    private static Pattern bindableRE = Pattern.compile("(\\{\\{[a-zA-Z\\$_]+[a-zA-Z0-9\\$_\\.\\[\\]\"\']*\\}\\})");
    private static Pattern urlRE = Pattern.compile("((https?://)([a-zA-Z0-9]+[a-zA-Z0-9_-]*)(:\\d{0,4})?([a-zA-Z0-9_\\-/%=\\{\\}\\?\\+\\&\\.:]*))");

    private ParserUtils parserUtils;// Parser util class

    private String incomingStr;// incoming script to parse

    private Map<String, String> defaultPropName;// first command, second PropName
    private Map<String, String> keywords;// first synomin name, second commandName
    private Map<String, SyntaxCommand> commands;// first command name, second command

    /**
     * Initialize fields
     * @param listCommands ArrayList of SyntaxCommand with command that will be used in parser
     */
    public Parser(ArrayList<SyntaxCommand> listCommands) {
        parserUtils = new ParserUtils();
        defaultPropName = new HashMap<>();
        keywords = new HashMap<>();
        commands = new HashMap<>();

        config(listCommands);
    }

    /**
     * Config maps with default properties names, keywords and commands
     * @param listCommands ArrayList of SyntaxCommand with command that will be used in parser
     */
    private void config(ArrayList<SyntaxCommand> listCommands) {
        listCommands.forEach(command -> {
            command.getDefaultProperty().forEach((k, v) -> {
                defaultPropName.put(k, v);
            });

            command.getSynonims().forEach((k, v) -> {
                keywords.put(k, v);
            });

            commands.put(command.getName(), command);
        });
    }

    /**
     * Parse dj-dps script
     * @param str Script to parse
     * @return List of ExecutionCommands from script
     * @throws ParserError
     */
    public List<ExecutionCommand> parse(String str) throws ParserError {
        incomingStr = replaceAllRegex(str);
        ArrayList<ExecutionCommand> executionCommands = new ArrayList<>();

        try {
            String[] arrayTmp = incomingStr.split(";");
            for (int i = 0; i < arrayTmp.length; i++) {
                arrayTmp[i] += ";";
                arrayTmp[i] = validationCommand(arrayTmp[i], i);
            }

            incomingStr = String.join(";", arrayTmp);
            incomingStr = incomingStr.replace(";;", ";");

            ArrayList<String> script = putVarsInsteadIndex();

            for (int i = 0; i < script.size(); i++)
                executionCommands.add(creatExecutionCommand(script.get(i)));
        } catch (Exception e) {
            if (e.getClass() != ParserError.class)
                throw new ParserError(e.toString(), -1, 0);
            else
                throw  e;
        }

        return executionCommands;
    }

    /**
     * Validate command
     * @param cmd command
     * @param i index of command
     * @return JSON representation of command
     * @throws ParserError
     */
    private String validationCommand(String cmd, int i) throws ParserError {
        try {
            Matcher matcher = commandNameRE.matcher(cmd);
            matcher.find();
            String cmdName = matcher.group();
            cmdName = cmdName.substring(1, cmdName.length() - 2);

            Matcher matcherParamsRE = paramsRE.matcher(cmd);

            ArrayList<String> params = new ArrayList<>();
            while (matcherParamsRE.find()) {
                params.add(getParamForCmd(matcherParamsRE.group(), cmdName));
            }

            return String.format("\"%s\"%s", cmdName, params.get(0));
        } catch (Exception e) {
            throw new ParserError(e.getMessage(), i, LineMapper.findLineOfCommandStart(incomingStr, i));
        }
    }

    /**
     * Replace default properties names in commands
     * @param item params for command
     * @param cmdName name of command
     * @return JSON representation of params for command
     * @throws Exception
     */
    private String getParamForCmd(String item, String cmdName) throws Exception {
        try {
            Pattern inParams1 = Pattern.compile("\\:\\{\\^");
            Pattern inParams2 = Pattern.compile("\\:\\{");

            if (defaultValueRE.matcher(item).find()) {
                String p = "";

                if (inParams1.matcher(item).find()) {
                    p = item.substring(3, item.length() - 3);
                } else if (inParams2.matcher(item).find()) {
                    p = item.substring(2, item.length() - 2);
                }

                String defaultProp = defaultPropName.containsKey(cmdName) ? defaultPropName.get(cmdName) : "undefined";
                return String.format(":{\"%s\":%s}", defaultProp, p);
            }

            if (defaultStoredValueRE.matcher(item).find()) {
                String p = item.substring(1, item.length() - 1);

                String defaultProp = defaultPropName.containsKey(cmdName) ? defaultPropName.get(cmdName) : "undefined";
                return String.format(":{\"%s\":%s}", defaultProp, p);
            }
        } catch (Exception e) {
            throw e;
        }
        return item;
    }

    /**
     * Replce Value of param by index
     * @param str Command
     * @param pattern Pattern to use in replacement
     * @return Command with changed params
     */
    private String replaceVarIndex(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, parserUtils.varIndex(matcher.group()));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Replave push url
     * @param str command
     * @param pattern Pattern to use in replacement
     * @return Command with correct url
     */
    private String replacePushUrl(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, parserUtils.pushUrl(matcher.group()));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Do all regex replacements
     * @param str script
     * @return Script split on commands by ';'
     */
    private String replaceAllRegex(String str) {
        str = replaceVarIndex(str, scriptRE);

        str = replacePushUrl(str, urlRE);

        str = bindableRE
                .matcher(str)
                .replaceAll("\"$1\"");

        str = lineCommentRE
                .matcher(str)
                .replaceAll("");

        str = replaceVarIndex(str, valuesRE);

        str = lineRE
                .matcher(str)
                .replaceAll("");
        str = inlineCommentRE
                .matcher(str)
                .replaceAll("");

        str = commandSplitRE
                .matcher(str)
                .replaceAll("$1;$2");
        str = nonbrackedParamsRE
                .matcher(str)
                .replaceAll("({$1})");
       str = propertyNameRE
                .matcher(str)
                .replaceAll("\"$1\"");
        str = str.replaceAll("\'","\"");
       str = emptyPropsListRE
                .matcher(str)
                .replaceAll("({})");
         str = str.replaceAll("\\(", ":");
        str = str.replaceAll("\\)", "");

        return str;
    }

    /**
     * Put var value instead index
     * @return JSON object representations of commands
     */
    private ArrayList<String> putVarsInsteadIndex() {
        ArrayList<String> script = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\^[0-9]+");
        String[] cmd = incomingStr.split(";");

        for (int i = 0; i < cmd.length; i++) {
            Matcher matcher = pattern.matcher(cmd[i]);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(buffer, parserUtils.varValue(matcher.group()));
            }
            matcher.appendTail(buffer);
            script.add(buffer.toString());
        }

        return script;
    }

    /**
     * Create Execution command
     * @param str Command
     * @return Execution for concrete command
     * @throws ParserError
     */
    private ExecutionCommand creatExecutionCommand(String str) throws ParserError {
        try {
            int index = str.indexOf(":");
            String cmd = str.substring(0, index).replaceAll("\"", "");
            String params = str.substring(index + 1, str.length());
            return new ExecutionCommand(commands.get(keywords.get(cmd)), params);
        }
        catch (Exception e) {
            throw new ParserError(e.toString(), -1, 0);
        }
    }
}
