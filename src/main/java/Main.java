import exceptions.ParserError;
import parser_commands.ExecutionCommand;
import parser_commands.SyntaxCommand;
import parser_commands.context.Context;
import parser_commands.html.Html;
import parser_commands.html.WrapHtml;
import parser_commands.text.Append;
import parser_commands.var.Set;

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

        try {
            List<ExecutionCommand> list = parser.parse(str);
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getSyntaxCommand().getName() + " ");
                if (list.get(i).getParams() != null)
                    System.out.println(list.get(i).getParams().toString());
            }
            System.out.println(list.get(4).getParams().getString("style"));
        }
        catch (ParserError e) {
            System.out.println(e.getMessage());
        }
    }
}
