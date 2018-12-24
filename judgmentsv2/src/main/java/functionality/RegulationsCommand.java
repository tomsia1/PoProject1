package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.util.List;

public class RegulationsCommand extends Command {

    public RegulationsCommand()
    {
        this.argsRequired=false;
    }

    @Override
    public String execute(List<String> args, Parser parser) throws IOException {

        DataBaseOrders connector=parser.getConnector();
        StringBuilder sb=new StringBuilder();

        if (args==null)
            return connector.topRegulations(10);
        else
            for (String s: args)
                if (s.matches("^-n=.*"))
                try {
                    int top=Integer.parseInt(s.substring(3));
                    sb.append(connector.topRegulations(top));
                } catch (NumberFormatException ex){
                    sb.append("invalid number format");
                }

        return sb.toString();
    }


}
