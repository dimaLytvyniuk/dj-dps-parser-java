import exceptions.ParserError;
import utils.LineMapper;
import utils.ParserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class Parser {
    private static Pattern valuesRE = Pattern.compile("'((?:\\\\[\'bfnrt/\\\\]|\\\\u[a-fA-F0-9]{4}|[^\'\\\\])*)'|\"((?:\\\\[\"bfnrt/\\\\]|\\\\u[a-fA-F0-9]{4}|[^\"\\\\])*)\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern lineRE = Pattern.compile("[\r\n\t\\s]*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern lineCommentRE = Pattern.compile("//[\\w\\S.\t:,;\'\"(){}\\[\\]0-9-_](?:[\n\r]*)", Pattern.CASE_INSENSITIVE);
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

    private static Pattern bindableRE = Pattern.compile("([a-zA-Z\\$_]+[a-zA-Z0-9\\$_\\.\\[\\]\"\']*)");

    private static Pattern urlRE = Pattern.compile("((https?://)([a-zA-Z0-9]+[a-zA-Z0-9_-]*)(:\\d{0,4})?([a-zA-Z0-9_\\-/%=\\{\\}\\?\\+\\&\\.:]*))");

    private ParserUtils parserUtils;

    private String incomingStr;
    Map<String, String> defaultPropName;

    public Parser() {
        parserUtils = new ParserUtils();
        defaultPropName = new HashMap<>();
    }

    public void parse(String str) throws ParserError {
        incomingStr = replaceAllRegex(str);

        try {
            String[] arrayTmp = incomingStr.split(";");
            for (int i = 0; i < arrayTmp.length; i++) {
                arrayTmp[i] += ";";
                arrayTmp[i] = validationCommand(arrayTmp[i], i);
            }

            incomingStr = String.join(";", arrayTmp);
            incomingStr = incomingStr.replace(";;", ";");
        } catch (Exception e) {
            if (e.getClass() != ParserError.class)
                throw new ParserError(e.toString(), -1, 0);
            else
                throw  e;
        }
    }

    private String validationCommand(String cmd, int i) throws ParserError {
        try {
            Matcher matcher = commandNameRE.matcher(cmd);
            matcher.find();
            String cmdName = matcher.group();
            cmdName = cmdName.substring(1, cmdName.length() - 2);

            Matcher matcherParamsRE = paramsRE.matcher(cmd);

            ArrayList<String> params = new ArrayList<>();
            while (matcher.find()) {
                params.add(getParamForCmd(matcher.group(), cmdName));
            }

            return String.format("\"%s\"%s", cmdName, params.get(0));
        } catch (Exception e) {
            throw new ParserError(e.getMessage(), i, LineMapper.findLineOfCommandStart(incomingStr, i));
        }
    }

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

    private String replaceVarIndex(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, parserUtils.varIndex(matcher.group()));
        }
        matcher.appendTail(buffer);
        return matcher.toString();
    }

    private String replacePushUrl(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, parserUtils.pushUrl(matcher.group()));
        }
        matcher.appendTail(buffer);
        return matcher.toString();
    }

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
}
