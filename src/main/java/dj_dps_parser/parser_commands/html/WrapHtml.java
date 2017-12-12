package dj_dps_parser.parser_commands.html;

import dj_dps_parser.parser_commands.SyntaxCommand;

/**.
 * Class that represent command "wrapHtml" from data processing script
 * @see SyntaxCommand
 * @see <a href="https://github.com/boldak/dj-dps-commands/blob/master/src/html/wrapHtml.js">"wrapHtml" in js</a>
 */
public class WrapHtml extends SyntaxCommand {
    public WrapHtml() {
        super();

        name = "wrapHtml";

        synonims.put("wrapHtml", "wrapHtml");
        synonims.put("wrap", "wrapHtml");

        internalAliases.put("tag", "tag");

        defaultProperty.put("wrapHtml", "tag");
        defaultProperty.put("wrap", "tag");

        help = "{\n" +
                "    synopsis:\"Wrap context into html tag\",\n" +
                "    \n" +
                "    name:{\n" +
                "      \"default\" : \"wrapHtml\",\n" +
                "      synonims:[\"wrapHtml\",\"wrap\"]\n" +
                "    },\n" +
                "    input:[\"string\", \"html\"],\n" +
                "    output:\"html\",\n" +
                "    \"default param\": \"tag\",\n" +
                "    \n" +
                "    params:[{\n" +
                "            name: \"tag\",\n" +
                "            synopsis: \"Wrapper tag name (required)\",\n" +
                "            type:[\"string\"],\n" +
                "            synonims: [],\n" +
                "            \"default value\": \"none\"\n" +
                "        },\n" +
                "        {\n" +
                "            name: \"style\",\n" +
                "            synopsis: \"Wrapper inline css style (optional)\",\n" +
                "            type:[\"string\"],\n" +
                "            synonims: [],\n" +
                "            \"default value\": \"none\"\n" +
                "        },\n" +
                "        {\n" +
                "            name: \"class\",\n" +
                "            synopsis: \"Wrapper class (optional)\",\n" +
                "            type:[\"string\"],\n" +
                "            synonims: [],\n" +
                "            \"default value\": \"none\"\n" +
                "        }\n" +
                "        ],\n" +
                "    \n" +
                "    example:{\n" +
                "      description:\"Build HTML table\",\n" +
                "      code:   \"<?javascript\\r\\n    \\r\\n    $context.rowMapper = function(d){\\r\\n      return \\\"<tr>\\\"+ d.value.map(function(v){\\r\\n         return \\\"<td style=\\\\\\\\\\\"font-size:x-small\\\\\\\\\\\">\\\"+v+\\\"</td>\\\"\\r\\n      }).join(\\\"\\\")+\\\"</tr>\\\"\\r\\n    };\\r\\n\\r\\n?>\\r\\n\\r\\n<?dps\\r\\n\\r\\n    map({{rowMapper}})\\r\\n    concat()\\r\\n    html()\\r\\n    wrapHtml(tag:\\\"table\\\", class:\\\"skin\\\", style:'background:#ded;')\\r\\n\\r\\n?>\\r\\nset(\\\"t2html\\\")\\r\\n\\r\\n\\r\\n\\r\\nload(\\r\\n    ds:\\\"47611d63-b230-11e6-8a1a-0f91ca29d77e_2016_02\\\",\\r\\n    as:'dataset'\\r\\n)\\r\\n\\r\\nproj([\\r\\n    {dim:\\\"time\\\", as:\\\"row\\\"},\\r\\n    {dim:\\\"indicator\\\",as:\\\"col\\\"}\\r\\n])\\r\\n\\r\\nformat(2)\\r\\njson()\\r\\nselect(\\\"$.body.*\\\")\\r\\nrun({{t2html}})\\r\\n\"\n" +
                "    }\n" +
                "  }";
    }

    @Override
    public void execute() {

    }
}
