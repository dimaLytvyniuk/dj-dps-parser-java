package parser_commands.context;

import parser_commands.SyntaxCommand;

/**
 * Created by dima on 27.11.17.
 */
public class Context extends SyntaxCommand {
    public Context() {
        super();

        name = "context";

        synonims.put("context", "context");
        synonims.put("ctx", "context");

        internalAliases.put("v", "value");
        internalAliases.put("val", "value");
        internalAliases.put("value", "value");
        internalAliases.put("message", "value");
        internalAliases.put("msg", "value");

        defaultProperty.put("context", "value");
        defaultProperty.put("ctx", "value");

        help = "{\n" +
                "    synopsis:\"Sets script context\",\n" +
                "    name:{\n" +
                "      \"default\" : \"context\",\n" +
                "      synonims:[\"ctx\", \"context\"]\n" +
                "    },\n" +
                "    \"default param\": \"value\",\n" +
                "    input:[\"any\"],\n" +
                "    output:\"Type of parameter 'value'\",\n" +
                "    params:[\n" +
                "      {\n" +
                "        name:\"value\",\n" +
                "        synopsis:\"Value will be stored in context\",\n" +
                "        type:[\"string\",\"number\",\"bindable\",\"injection\"],\n" +
                "        synonims:[\"v\", \"val\", \"value\"],\n" +
                "        \"default value\": \"none\"\n" +
                "      }\n" +
                "    ],\n" +
                "    example:{\n" +
                "      description:\"Put string 'Hello' into context\",\n" +
                "      code:\"co\n" +
                "module.exports = {\n" +
                "  name: \"context\",\n" +
                "  synonims:{\n" +
                "    \"context\" : \"context\",\n" +
                "    \"ctx\" : \"context\"\n" +
                "  },\n" +
                "\n" +
                "  \"internal aliases\":{\n" +
                "      \"v\":\"value\",\n" +
                "      \"val\":\"value\",\n" +
                "      \"value\":\"value\",\n" +
                "      \"message\":\"value\",\n" +
                "      \"msg\":\"value\"\n" +
                "  },\n" +
                "  \n" +
                "  defaultProperty:{\n" +
                "    \"context\" : \"value\",\n" +
                "    \"ctx\" : \"value\"\n" +
                "  },\n" +
                "\n" +
                "  execute:function(command,state){\n" +
                "    try{\n" +
                "      state.head = {\n" +
                "        type: typeof command.settings.value,\n" +
                "        data: command.settings.value  \n" +
                "      }\n" +
                "    } catch(e) {\n" +
                "      throw e.toString()\n" +
                "    }\n" +
                "    return state;\n" +
                "  },\n" +
                "\n" +
                "  help:{\n" +
                "    synopsis:\"Sets script context\",\n" +
                "    name:{\n" +
                "      \"default\" : \"context\",\n" +
                "      synonims:[\"ctx\", \"context\"]\n" +
                "    },\n" +
                "    \"default param\": \"value\",\n" +
                "    input:[\"any\"],\n" +
                "    output:\"Type of parameter 'value'\",\n" +
                "    params:[\n" +
                "      {\n" +
                "        name:\"value\",\n" +
                "        synopsis:\"Value will be stored in context\",\n" +
                "        type:[\"string\",\"number\",\"bindable\",\"injection\"],\n" +
                "        synonims:[\"v\", \"val\", \"value\"],\n" +
                "        \"default value\": \"none\"\n" +
                "      }\n" +
                "    ],\n" +
                "    example:{\n" +
                "      description:\"Put string 'Hello' into context\",\n" +
                "      code:\"context(value:'Hello')\\r\\n\\r\\n//or with synonims and defaults\\r\\nctx('Hello')\\r\\nset('b')\\r\\ninfo()\\r\\n\\r\\n//or with injection\\r\\n<?text Hello ?>\\r\\n\\r\\n//or get string from var\\r\\ninfo()\\r\\nset('a')\\r\\nctx({{a}})\\r\\ninfo()\\r\\nctx({{b}})\\r\\ninfo()\\r\\n\\r\\nlog()\\r\\n\"\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    \n" +
                "\n" +
                "  }\n" +
                "}ntext(value:'Hello')\\r\\n\\r\\n//or with synonims and defaults\\r\\nctx('Hello')\\r\\nset('b')\\r\\ninfo()\\r\\n\\r\\n//or with injection\\r\\n<?text Hello ?>\\r\\n\\r\\n//or get string from var\\r\\ninfo()\\r\\nset('a')\\r\\nctx({{a}})\\r\\ninfo()\\r\\nctx({{b}})\\r\\ninfo()\\r\\n\\r\\nlog()\\r\\n\"\n" +
                "    }" +
                "}";
    }

    @Override
    public void execute() {

    }
}
