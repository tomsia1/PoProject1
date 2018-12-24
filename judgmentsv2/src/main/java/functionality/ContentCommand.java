package functionality;

import db.DataBaseOrders;

import java.util.List;

public class ContentCommand extends Command {

    public ContentCommand()
    {
        this.argsRequired=true;
        this.dataBaseRequired=true;
    }

    public String execute(List<String> args,Parser parser)
    {
        return parser.getConnector().reason(args);
    }
}
