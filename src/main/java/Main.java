import exceptions.ParserError;

/**
 * Created by dima on 22.11.17.
 */
public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
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
            System.out.println(parser.parse(str));
        }
        catch (ParserError e) {
            System.out.println(e.getMessage());
        }
    }
}
