package exceptions;

public class ParserError extends Exception {
    private String myMessage;

    public ParserError(String message, String command, int line) {
        super(message);

        if (command != null) {
            this.myMessage = String.format("Invalid commmand number %s (starts at line %d)\n %s", command, line, message);
        }
        else
            myMessage = message;
    }

    @Override
    public String getMessage() {
        return myMessage;
    }
}
