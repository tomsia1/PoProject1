package functionality;

import java.util.List;

public class Clear_loadCommand extends Command {

    public Clear_loadCommand()
    {
        this.argsRequired=false;
        this.dataBaseRequired=false;
    }

    public String execute(List<String> args, Parser parser)
    {
        parser.setConnector(null);
        parser.resetCache();
        return "cleared";
    }
}
