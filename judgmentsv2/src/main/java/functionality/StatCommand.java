package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.util.List;

public class StatCommand extends Command{

    public String execute(List<String> args, Parser parser) throws IOException
    {
        DataBaseOrders connector=parser.getConnector();
        StringBuilder sb=new StringBuilder("\n");

        for (String s: args)
            if (s.equals("-j"))
                sb.append(connector.avarageNumberOfAnnMarieCheerfulInCourt());
            else
            if (s.equals("-m"))
                sb.append(connector.monthlyStats());
            else
            if (s.equals("-t"))
                sb.append(connector.courtTypeStats());
            else
                sb.append("invalid stat argument: "+s+"\n");

        return sb.toString();
    }
}
