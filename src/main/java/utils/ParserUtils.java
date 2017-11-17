package utils;

import java.util.ArrayList;

public class ParserUtils {
    private ArrayList<String> values;

    public String varIndex(String tag) {
        return new String();
    }

    public String pushUrl(String tag) {
        values.add(tag);

        return String.format("^%d", values.size() - 1);
    }

    public String getUrl(String key) {
        return values.get(Integer.parseInt(key));
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
