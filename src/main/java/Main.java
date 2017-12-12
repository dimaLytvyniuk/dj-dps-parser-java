import dj_dps_parser.Parser;
import dj_dps_parser.exceptions.ParserError;
import dj_dps_parser.parser_commands.ExecutionCommand;
import dj_dps_parser.parser_commands.SyntaxCommand;
import dj_dps_parser.parser_commands.context.Context;
import dj_dps_parser.parser_commands.dps.ReturnCommand;
import dj_dps_parser.parser_commands.html.Html;
import dj_dps_parser.parser_commands.html.WrapHtml;
import dj_dps_parser.parser_commands.text.Append;
import dj_dps_parser.parser_commands.var.Set;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 22.11.17.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<SyntaxCommand> synataxCommands = new ArrayList<>();
        synataxCommands.add(new Context());
        synataxCommands.add(new Html());
        synataxCommands.add(new WrapHtml());
        synataxCommands.add(new Append());
        synataxCommands.add(new Set());
        synataxCommands.add(new ReturnCommand());

        Parser parser = new Parser(synataxCommands);
        String str = "<?html\n" +
                "   <h4>Relevant News</h4>\n" +
                "    <div    class=\"row\" \n" +
                "            style=\" position:relative;\n" +
                "                    margin:0;\" \n" +
                "    >\n" +
                "        <div    class= \"column medium-3 left\"\n" +
                "                 style=\"color: #333;\n" +
                "                        margin: -2px -5px 0 1em;\n" +
                "                        font-size: small;\n" +
                "                        border: 2px solid #333;\n" +
                "                        border-bottom: none;\n" +
                "                        border-radius: 10px 10px 0 0;\n" +
                "                        padding: 5.5px 1em;\n" +
                "                        top: 2px;\n" +
                "                        background: #ffffff;\n" +
                "                        cursor:pointer;\n" +
                "                        z-index:5\"\n" +
                "                ng-click = \"API.emit('setFeedView')\"        \n" +
                "        >\n" +
                "            as Feed            \n" +
                "        </div>    \n" +
                "        <div    class=\"column medium-3 left\" \n" +
                "                style=\" border: 1px solid #999;\n" +
                "                        font-size: small;\n" +
                "                        border-bottom: none;\n" +
                "                        border-radius: 10px 10px 0 0;\n" +
                "                        padding: 5px 1em;\n" +
                "                        top: 0px;\n" +
                "                        background: #ffffff;\n" +
                "                        color: #999;\n" +
                "                        cursor:pointer;\"\n" +
                "                ng-click = \"API.emit('setTimelineView')\"        \n" +
                "        >\n" +
                "            as Timeline            \n" +
                "        </div>    \n" +
                "    </div>\n" +
                "    <div    class=\"row\" \n" +
                "            style=\" height: 5px;\n" +
                "                    margin: 0;\n" +
                "                    border-top: 2px solid #999;\n" +
                "                    background: #fff;\"\n" +
                "    >\n" +
                "\n" +
                "    </div>\n" +
                "?> \n" +
                "\n" +
                "wrap(\n" +
                "        tag:'div', \n" +
                "        class:'row', \n" +
                "        style:'margin:0.25em 0.2em; padding:0.5em;'\n" +
                "    )\n" +
                "return()";

        try {
            List<ExecutionCommand> list = parser.parse(str);
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getSyntaxCommand().getName() + " ");
                if (list.get(i).getParams() != null)
                    System.out.println(list.get(i).getParams().toString());
            }
            //System.out.println(list.get(4).getParams().getString("style"));
            //System.out.println("\n\n".replace("\n", "\\\\n"));


        }
        catch (ParserError e) {
            System.out.println(e.getMessage());
        }
    }
}
