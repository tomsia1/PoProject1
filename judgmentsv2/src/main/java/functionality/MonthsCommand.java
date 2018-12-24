package functionality;

import java.io.IOException;
import java.util.List;

public class MonthsCommand extends Command{

    public MonthsCommand()
    {
        this.argsRequired=false;
    }

    @Override
    public String execute(List<String> args, Parser parser) throws IOException {
        return parser.getConnector().monthlyStats();
    }
}
