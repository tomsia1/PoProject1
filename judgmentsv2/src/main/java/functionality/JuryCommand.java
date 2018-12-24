package functionality;

import java.io.IOException;
import java.util.List;

public class JuryCommand extends Command {
    public JuryCommand() {
        this.argsRequired=false;
    }

    @Override
    public String execute(List<String> args, Parser parser) throws IOException {
        return parser.getConnector().avarageNumberOfAnnMarieCheerfulInCourt();
    }
}
