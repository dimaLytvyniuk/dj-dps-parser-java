package dj_dps_parser.exceptions;

/**
 * ParserError represent exception that will be throwed if
 * in parser is exception.
 * @see Exception
 */
public class ParserError extends Exception {
    private String myMessage;

    /**
     * Constructor for ParserError
     * @param message message to display
     * @param command number of command where was exception
     * @param line number of line where was command
     */
    public ParserError(String message, int command, int line) {
        super(message);

        if (command != -1) {
            this.myMessage = String.format("Invalid commmand number %s (starts at line %d)\n %s", command, line, message);
        }
        else
            myMessage = message;
    }

    /**
     *
     * @return message about error in parser
     */
    @Override
    public String getMessage() {
        return myMessage;
    }
}
