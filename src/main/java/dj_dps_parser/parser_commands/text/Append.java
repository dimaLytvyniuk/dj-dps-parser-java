package dj_dps_parser.parser_commands.text;

import dj_dps_parser.parser_commands.SyntaxCommand;

/**.
 * Class that represent command "append" from data processing script
 * @see SyntaxCommand
 * @see <a href="https://github.com/boldak/dj-dps-commands/blob/master/src/text/append.js">"append" in js</a>
 */
public class Append extends SyntaxCommand {
    public Append() {
        super();

        name = "append";

        synonims.put("append", "append");

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
