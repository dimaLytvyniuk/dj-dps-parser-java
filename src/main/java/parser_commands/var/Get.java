package parser_commands.var;

import parser_commands.SyntaxCommand;

/**
 * Created by dima on 07.12.17.
 */
public class Get extends SyntaxCommand {

    public Get() {
        super();

        name = "get";

        synonims.put("get", "get");

        internalAliases.put("path", "path");
        internalAliases.put("select", "path");
        internalAliases.put("var", "path");
        internalAliases.put("as", "as");
        internalAliases.put("type", "as");

        defaultProperty.put("get", "path");

        help = "{\n" +
                "        synopsis: \"Get deep copy of variable and set context\",\n" +
                "        name: {\n" +
                "            \"default\": \"get\",\n" +
                "            synonims: []\n" +
                "        },\n" +
                "        input:[\"any\"],\n" +
                "        output:\"type of variable\",\n" +
                "        \"default param\": \"path\",\n" +
                "        params: [\n" +
                "            {\n" +
                "                name: \"path\",\n" +
                "                synopsis: \"\",\n" +
                "                type:[\"json-path\"],\n" +
                "                synonims: [\"path\",\"select\", \"var\"],\n" +
                "                \"default value\": \"$\"\n" +
                "            }, {\n" +
                "                name: \"as\",\n" +
                "                synopsis: \"Type of variable.\",\n" +
                "                type: [\"json-path\"],\n" +
                "                synonims: [\"type\", \"as\"],\n" +
                "                \"default value\": \"$\"\n" +
                "        }],\n" +
                "        example: {\n" +
                "            description: \"Inspect variables\",\n" +
                "            code:  \"<?json \\r\\n    \\\"Hello\\\" \\r\\n?>\\r\\nset(\\\"str\\\")\\r\\n\\r\\n<?javascript \\r\\n    var notNull = function(item){\\r\\n        return item != undefined\\r\\n        \\r\\n    }; \\r\\n?>\\r\\nset(\\\"functions\\\")\\r\\n\\r\\nload(\\r\\n    ds:\\\"47611d63-b230-11e6-8a1a-0f91ca29d77e_2016_02\\\", \\r\\n    as:'json'\\r\\n)\\r\\n\\r\\nselect(\\\"$.metadata.dataset.commit\\\")\\r\\n\\r\\nset(var:\\\"commitNote\\\", val:\\\"$[0].note\\\")\\r\\nget(\\\"str\\\")\\r\\ninfo()\\r\\nget(\\\"functions.notNull\\\")\\r\\ninfo()\\r\\nget(\\\"commitNote\\\")\\r\\ninfo()\\r\\n// equals for previus\\r\\nget(\\\"$.commitNote\\\")\\r\\ninfo()\\r\\nlog()\\r\\n\"\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "    }";
    }

    @Override
    public void execute() {
    }
}
