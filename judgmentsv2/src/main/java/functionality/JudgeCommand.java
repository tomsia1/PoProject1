package functionality;

import db.DataBaseOrders;

import java.util.List;

public class JudgeCommand extends Command {

    public String execute(List<String> args, Parser parser)
    {
        return parser.getConnector().judgesWithNumbers(args);
    }
}
