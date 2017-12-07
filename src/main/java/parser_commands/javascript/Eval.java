package parser_commands.javascript;

import parser_commands.SyntaxCommand;

/**
 * Created by dima on 07.12.17.
 */
public class Eval extends SyntaxCommand {

    public Eval() {
        super();

        name = "eval";

        synonims.put("eval", "eval");
        synonims.put("evaluate", "eval");
        synonims.put("js",  "eval");
        synonims.put("javascript", "eval");

        help = "{\n" +
                "        synopsis: \"Evaluate context as javascript\",\n" +
                "\n" +
                "        name: {\n" +
                "            \"default\": \"eval\",\n" +
                "            synonims: [\"eval\", \"evaluate\", \"js\",\"javascript\"]\n" +
                "        },\n" +
                "\n" +
                "        \"default param\": \"none\",\n" +
                "        input:[\"string\"],\n" +
                "        output:\"object\",\n" +
                "\n" +
                "        params: [],\n" +
                "\n" +
                "        example: {\n" +
                "            description: \"Create javascript callbacks\",\n" +
                "            code:  \"<?javascript\\n    \\n    var eqFirstMeta = function(a,b){\\n      return a.metadata[0].id == b.metadata[0].id\\n    }\\n    \\n    var nullCount = function(values){\\n        return values.filter(function(d){\\n            return d == null\\n        }).length\\n    };\\n\\n?>\\n\\nset('f')\\n\\nget('f.eqFirstMeta')\\ninfo('eqFirstMeta')\\ninfo()\\n\\nget('f.nullCount')\\ninfo('nullCount')\\ninfo()\\n\\nlog()\\n\"\n" +
                "        }\n" +
                "    }";
    }

        @Override
    public void execute() {

    }
}
