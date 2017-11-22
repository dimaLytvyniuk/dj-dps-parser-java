package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUtils {
    private ArrayList<String> values;

    public ParserUtils() {
        values = new ArrayList<>();
    }

    public String varIndex(String tag) {
        Pattern pattern1 = Pattern.compile("(?:\\?)(javascript|json|text|html|dps|xml|csv)");
        Pattern pattern2 = Pattern.compile("(^\\?)|(\\?$)");

        String key = tag.substring(1, tag.length() - 1);

        if (key.indexOf("?") == 0) {
            key = key.replaceAll("\"", "\\\"");
            String postProcess = "";

            Matcher matcher = pattern1.matcher(key);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                postProcess = matcher.group().substring(1);
                matcher.appendReplacement(buffer, "");
            }
            matcher.appendTail(buffer);
            key = matcher.toString();

            key = pattern2.matcher(key).replaceAll("");
            key = key.replaceAll("\r", "\\r")
                    .replaceAll("\n", "\\n")
                    .replaceAll("\t", "\\t");

            values.add(key);

            return String.format("context(value:^%d);%s();", values.size() - 1, postProcess);
        } else {
            key = key.replaceAll("\"", "'");
            values.add(key);

            return String.format("^%s", values.size() - 1);
        }

    }

    public String pushUrl(String tag) {
        values.add(tag);

        return String.format("^%d", values.size() - 1);
    }

    public String getUrl(String key) {
        return values.get(Integer.parseInt(key.substring(1)));
    }

    public String varValue(String tag) {
        String key = tag.substring(1);
        String r = values.get(Integer.parseInt(key));

        while (r.indexOf('^') == 0) {
            key = r.substring(1);
            r = values.get(Integer.parseInt(key));
        }

        return String.format("\"%s\"", r);
    }
}
