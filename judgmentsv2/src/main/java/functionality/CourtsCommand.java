package functionality;

import java.io.IOException;
import java.util.List;

public class CourtsCommand extends Command {

    public CourtsCommand()
    {
        this.argsRequired=false;
    }

    @Override
    public String execute(List<String> args, Parser parser) throws IOException {
        return parser.getConnector().courtTypeStats();
    }
}
