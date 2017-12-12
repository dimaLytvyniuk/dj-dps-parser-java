import dj_dps_parser.Parser;
import org.junit.Test;
import dj_dps_parser.parser_commands.ExecutionCommand;
import dj_dps_parser.parser_commands.SyntaxCommand;
import dj_dps_parser.parser_commands.context.Context;
import dj_dps_parser.parser_commands.dps.ReturnCommand;
import dj_dps_parser.parser_commands.html.Html;
import dj_dps_parser.parser_commands.html.WrapHtml;
import dj_dps_parser.parser_commands.javascript.Eval;
import dj_dps_parser.parser_commands.text.Append;
import dj_dps_parser.parser_commands.var.Get;
import dj_dps_parser.parser_commands.var.Set;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 07.12.17.
 */
public class ParserTest {
    @Test
    public void parse() throws Exception {
        ArrayList<SyntaxCommand> synataxCommands = new ArrayList<>();
        synataxCommands.add(new Context());
        synataxCommands.add(new Html());
        synataxCommands.add(new WrapHtml());
        synataxCommands.add(new Append());
        synataxCommands.add(new Set());
        synataxCommands.add(new ReturnCommand());

        Parser parser = new Parser(synataxCommands);
        String str = "set('tags')\n" +
                "\n" +
                "<?html\n" +
                "  DB totals:<br/>datasets:&nbsp;\n" +
                "?>\n" +
                "// test\n" +
                "\n" +
                "/*\n" +
                "  test\n" +
                "*/\n" +
                "\n" +
                "append({{datasets}})\n" +
                "wrap(\n" +
                "  tag:'div', style:'margin:0'\n" +
                ")\n" +
                "html()";
        System.out.println("Test1:");
        List<ExecutionCommand> list = parser.parse(str);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getSyntaxCommand().getName() + " ");
            if (list.get(i).getParams() != null)
                System.out.println(list.get(i).getParams().toString());
        }
        System.out.println();
    }

    @Test
    public void feedViewTest() throws Exception {
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
        System.out.println("Test feedView:");
        List<ExecutionCommand> list = parser.parse(str);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getSyntaxCommand().getName() + " ");
            if (list.get(i).getParams() != null)
                System.out.println(list.get(i).getParams().toString());
        }
        System.out.println();
    }

    @Test
    public void getChannelsHTMLTest() throws Exception {
        ArrayList<SyntaxCommand> synataxCommands = new ArrayList<>();
        synataxCommands.add(new Context());
        synataxCommands.add(new Html());
        synataxCommands.add(new WrapHtml());
        synataxCommands.add(new Append());
        synataxCommands.add(new Set());
        synataxCommands.add(new ReturnCommand());
        synataxCommands.add(new Eval());
        synataxCommands.add(new Get());

        Parser parser = new Parser(synataxCommands);
        String str = "<?html\n" +
                "    <div>\n" +
                "        <a href=\"<%=url%>\" target=\"_blank\" style=\"font-size:small; line-height:1; margin:0; padding:0\">\n" +
                "            <%=title%>\n" +
                "        </a>\n" +
                "    </div>\n" +
                "?>\n" +
                "\n" +
                "set('htmlTemplate')\n" +
                "\n" +
                "<?html\n" +
                "   <div class=\"row\" style=\"margin:0\">\n" +
                "        <button class=\"success right small radius\"\n" +
                "                ng-click=\"API.emit('addRss')\"\n" +
                "                style=\"padding: 0.5em;font-stretch: ultra-condensed;margin: 0.3rem 0.5em 0.1rem;\"\n" +
                "        >\n" +
                "            New RSS Channel...\n" +
                "        </button>\n" +
                "        <button class=\"success right small radius\"\n" +
                "                ng-click=\"API.emit('changeRss')\"\n" +
                "                style=\"padding: 0.5em;font-stretch: ultra-condensed;margin: 0.3rem 0.5em 0.1rem;\"\n" +
                "        >\n" +
                "            Change Selection...\n" +
                "        </button>\n" +
                "    </div>\n" +
                "?>\n" +
                "set('htmlFooter')\n" +
                "\n" +
                "get('channels')\n" +
                "wrap(tag:'div', class:'row', style:'margin:0.5em;')\n" +
                "set('channels')\n" +
                "\n" +
                "<?html\n" +
                "    <h4> RSS Channels </h4>\n" +
                "?>\n" +
                "append({{channels}})\n" +
                "append({{htmlFooter}})\n" +
                "\n" +
                "wrap(\n" +
                "        tag:'div', \n" +
                "        class:'row', \n" +
                "        style:'background: #eee;margin:0.25em 0.2em; padding:0.5em; border:1px solid #999;'\n" +
                "    )\n" +
                "return()";
        System.out.println("Test getChannelsHTML:");
        List<ExecutionCommand> list = parser.parse(str);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getSyntaxCommand().getName() + " ");
            if (list.get(i).getParams() != null)
                System.out.println(list.get(i).getParams().toString());
        }
        System.out.println();
    }

    @Test
    public void timelineView() throws Exception {
        ArrayList<SyntaxCommand> synataxCommands = new ArrayList<>();
        synataxCommands.add(new Context());
        synataxCommands.add(new Html());
        synataxCommands.add(new WrapHtml());
        synataxCommands.add(new Append());
        synataxCommands.add(new Set());
        synataxCommands.add(new ReturnCommand());
        synataxCommands.add(new Eval());
        synataxCommands.add(new Get());

        Parser parser = new Parser(synataxCommands);
        String str = "<?html\n" +
                "   <h4>Relevant News</h4>\n" +
                "    <div    class=\"row\" \n" +
                "            style=\" position:relative;\n" +
                "                    margin:0;\" \n" +
                "    >\n" +
                "        <div    class= \"column medium-3 left\" \n" +
                "                style=\" border: 1px solid #999;\n" +
                "                        margin: 0 0 0 1em;\n" +
                "                        font-size: small;\n" +
                "                        border-bottom: none;\n" +
                "                        border-radius: 10px 10px 0 0;\n" +
                "                        padding: 5px 1em;\n" +
                "                        top: 0px;\n" +
                "                        background: #ffffff;\n" +
                "                        color: #999;\n" +
                "                        cursor:pointer;\"\n" +
                "                ng-click = \"API.emit('setFeedView')\"        \n" +
                "        >\n" +
                "            as Feed            \n" +
                "        </div>    \n" +
                "        <div    class=\"column medium-3 left\" \n" +
                "                style=\" color: #333;\n" +
                "                        margin: -2px -5px;\n" +
                "                        font-size: small;\n" +
                "                        border: 2px solid #333;\n" +
                "                        border-bottom: none;\n" +
                "                        border-radius: 10px 10px 0 0;\n" +
                "                        padding: 5.5px 1em;\n" +
                "                        top: 2px;\n" +
                "                        background: #ffffff;\n" +
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
        System.out.println("Test getChannelsHTML:");
        List<ExecutionCommand> list = parser.parse(str);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getSyntaxCommand().getName() + " ");
            if (list.get(i).getParams() != null)
                System.out.println(list.get(i).getParams().toString());
        }
        System.out.println();
    }
}