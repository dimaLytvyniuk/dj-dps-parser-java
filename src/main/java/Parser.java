import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class Parser {
    private static Pattern valuesRE = Pattern.compile("'((?:\\\\[\'bfnrt/\\\\]|\\\\u[a-fA-F0-9]{4}|[^\'\\\\])*)'|\"((?:\\\\[\"bfnrt/\\\\]|\\\\u[a-fA-F0-9]{4}|[^\"\\\\])*)\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern lineRE = Pattern.compile("[\r\n\t\\s]*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern lineCommentRE = Pattern.compile("//[\\w\\S.\t:,;\'\"(){}\\[\\]0-9-_](?:[\n\r]*)", Pattern.CASE_INSENSITIVE);
    private static Pattern inlineCommentRE = Pattern.compile("/\\*[\\w\\W\b\\.\t:,;\'\"\\(\\)\\{\\}\\[\\]\\*0-9-_]*(?:\\*/)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private static Pattern commandSplitRE = Pattern.compile("(\\))([a-zA-Z@])", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern nonbrackedParamsRE = Pattern.compile("\\(([\\w\b\\.\t:,\'\"0-9-_]+[\\w\b\\.\t:,\'\"\\[\\]\\^0-9-_]*)\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern propertyNameRE = Pattern.compile("((@[a-zA-Z\\-_\\.]+|[a-zA-Z\\-_\\.]+)(?=[\\(\\)\\{\\}:\\[\\]\\s]+))", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static Pattern emptyPropsListRE = Pattern.compile("\\(\\s*\\)", Pattern.CASE_INSENSITIVE);
    private static Pattern defaultValueRE = Pattern.compile(":\\{\\^*[0-9]+\\};", Pattern.CASE_INSENSITIVE);
    private static Pattern defaultStoredValueRE = Pattern.compile(":\\^[0-9]+;", Pattern.CASE_INSENSITIVE);
    private static Pattern urlLookup = Pattern.compile("\\^[0-9]+", Pattern.CASE_INSENSITIVE);
    private static Pattern commandNameRE = Pattern.compile("\"@*([a-zA-Z0_-]+[a-zA-Z0-9_-]*\\.*)+\":", Pattern.CASE_INSENSITIVE);
    private static Pattern paramsRE = Pattern.compile(":[\\{\\^\\[]+[a-zA-Z0-9_:\",\\^\\{\\}\\[\\]-]*[\\}\\]]+;*|:\\^[0-9]+;*", Pattern.CASE_INSENSITIVE);

    private static Pattern scriptRE = Pattern.compile("(\\<\\?([^?]|(\\?+[^?\\>]))*\\?\\>)");

    private static Pattern bindableRE = Pattern.compile("({{[a-zA-Z$_]+[a-zA-Z0-9\\$\\_\\.\\[\\]\"\']*}}))");

    private static Pattern urlRE = Pattern.compile("((https?://)([a-zA-Z0-9]+[a-zA-Z0-9_-]*)(:\\d{0,4})?([a-zA-Z0-9_\\-/\%\=\\{\\}\\?\\+\\&\\.:]*))");
}
