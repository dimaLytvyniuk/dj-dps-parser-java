package parser_commands.var;

import parser_commands.SyntaxCommand;

/**
 * Created by dima on 27.11.17.
 */
public class Set extends SyntaxCommand {
    public Set() {
        super();

        name = "set";

        synonims.put("set", "set");
        synonims.put( "put", "set");
        synonims.put("let", "set");

        internalAliases.put("var", "var");
        internalAliases.put("variable", "var");
        internalAliases.put("value","value");
        internalAliases.put("val", "value");

        defaultProperty.put("set", "var");
        defaultProperty.put("put", "var");
        defaultProperty.put("let", "var");

        help = "{\n" +
                "        synopsis: \"Set variable value\",\n" +
                "        name: {\n" +
                "            \"default\": \"set\",\n" +
                "            synonims: [\"set\", \"put\", \"let\"]\n" +
                "        },\n" +
                "        input: [\"any\"],\n" +
                "        output: \"type of input context\",\n" +
                "        \"default param\": \"var\",\n" +
                "        params: [{\n" +
                "            name: \"var\",\n" +
                "            synopsis: \"Variable name (required).\",\n" +
                "            type: [\"string\"],\n" +
                "            synonims: [\"var\", \"variable\"],\n" +
                "            \"default value\": \"undefined\"\n" +
                "        }, {\n" +
                "            name: \"value\",\n" +
                "            synopsis: \"Json path to selected value (optional). If 'value' is not assigned then input context will be stored.\",\n" +
                "            type: [\"json-path\"],\n" +
                "            synonims: [\"value\", \"val\"],\n" +
                "            \"default value\": \"'$'\"\n" +
                "        }],\n" +
                "        example: {\n" +
                "            description: \"Variable usage\",\n" +
                "            code: \"<?json \\r\\n    \\\"Hello\\\" \\r\\n?>\\r\\nset(\\\"str\\\")\\r\\n\\r\\n<?javascript \\r\\n    var notNull = function(item){\\r\\n        return item != undefined\\r\\n        \\r\\n    }; \\r\\n?>\\r\\nset(\\\"functions\\\")\\r\\n\\r\\nload(\\r\\n    ds:\\\"47611d63-b230-11e6-8a1a-0f91ca29d77e_2016_02\\\", \\r\\n    as:'json'\\r\\n)\\r\\n\\r\\nselect(\\\"$.metadata.dataset.commit\\\")\\r\\n\\r\\nset(var:\\\"commitNote\\\", val:\\\"$[0].note\\\")\\r\\nget(\\\"str\\\")\\r\\ninfo()\\r\\nget(\\\"functions.notNull\\\")\\r\\ninfo()\\r\\nget(\\\"commitNote\\\")\\r\\ninfo()\\r\\n// equals for previus\\r\\nget(\\\"$.commitNote\\\")\\r\\ninfo()\\r\\nlog()\\r\\n\"\n" +
                "        }\n" +
                "\n" +
                "    }";
    }

    @Override
    public void execute() {

    }
}
