package functionality;

import java.io.IOException;
import java.util.List;

public abstract class Command {

    protected boolean argsRequired=true;
    protected boolean dataBaseRequired=true;

    public abstract String execute(List<String> args, Parser parser) throws IOException;

    public boolean areArgsRequired()
    {
        return argsRequired;
    }

    public boolean isDataBaseRequired()
    {
        return dataBaseRequired;
    }


}
