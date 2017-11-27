package parser_commands.text;

import parser_commands.SyntaxCommand;

/**
 * Created by dima on 27.11.17.
 */
public class Append extends SyntaxCommand {
    public Append() {
        super();

        name = "append";

        defaultProperty.put("append", "value");

        help = "{\n" +
                "    synopsis:\"Convert script context to string\",\n" +
                "    \n" +
                "    name:{\n" +
                "      \"default\" : \"append\",\n" +
                "      synonims:[]\n" +
                "    },\n" +
                "    input:[\"text\"],\n" +
                "    output:\"text\",\n" +
                "\n" +
                "    \"default param\": \"value\",\n" +
                "    \n" +
                "    params:[{\n" +
                "            name: \"value\",\n" +
                "            synopsis: \"The meaning of the text\",\n" +
                "            type:[],\n" +
                "            synonims: [\"value\"],\n" +
                "            \"default value\": \"none\"\n" +
                "        }],\n" +
                "    \n" +
                "    example:{\n" +
                "      description:\"Convert string value '[1,1,1]' to string value '[1,1,1]'\",\n" +
                "      code:\"<?html\\n    <h1>\\n?>\\nappend('hello')\\nappend('</h1>')\\n\\n// or\\n\\n<?html hello ?>\\nwrap('h1')\\n\"\n" +
                "\n" +
                "    }\n" +
                "  }";
    }

    @Override
    public void execute() {

    }
}
