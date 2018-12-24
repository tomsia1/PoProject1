package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.util.List;

public class RubrumCommand extends Command{

    public String execute (List<String> args, Parser parser)
    {
        return parser.getConnector().showMetric(args);
    }
}
