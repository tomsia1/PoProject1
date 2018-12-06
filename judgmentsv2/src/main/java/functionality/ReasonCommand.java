package functionality;

import db.DataBaseOrders;

import java.util.List;

public class ReasonCommand extends Command {

    public ReasonCommand()
    {
        this.argsRequired=true;
        this.dataBaseRequired=true;
    }

    public String execute(List<String> args,Parser parser)
    {
        return parser.getConnector().reason(args);
    }
}
