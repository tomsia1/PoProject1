package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.util.List;

public class TopCommand extends Command{

    public String execute(List<String> args, Parser parser) throws IOException
    {
        DataBaseOrders connector=parser.getConnector();
        StringBuilder sb=new StringBuilder();
        try
        {
            for (String s: args)
            {
                if (s.matches("^-r=.*"))
                {
                    int top=Integer.parseInt(s.substring(3));
                    sb.append(connector.topRegulations(top));
                }
                else
                if (s.matches("^-j=.*"))
                {
                    int top=Integer.parseInt(s.substring(3));
                    sb.append(connector.employeesOfTheMonth(top));
                }
                else
                    sb.append("invalid argument: "+s+"\n");
            }
        } catch (NumberFormatException e)
        {
            return "illegal number in top command";
        }

        return sb.toString();
    }
}
