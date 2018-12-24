package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.util.List;

public class LoadCommand extends Command {

    public LoadCommand()
    {
        this.argsRequired=true;
        this.dataBaseRequired=false;
    }

   public String execute(List<String> args, Parser parser) throws IOException
   {
       if (parser.getConnector()==null)
           parser.setConnector(new DataBaseOrders());
       parser.getConnector().append(args);

       parser.resetCache();

       return "";
   }
}
