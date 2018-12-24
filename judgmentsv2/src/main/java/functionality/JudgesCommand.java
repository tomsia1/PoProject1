package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.util.List;

public class JudgesCommand extends Command {

    public JudgesCommand()
    {
        this.argsRequired=false;
    }
    @Override
    public String execute(List<String> args, Parser parser) throws IOException {
        StringBuilder sb=new StringBuilder();
        DataBaseOrders connector=parser.getConnector();

        if (args==null)
            return connector.employeesOfTheMonth(10);
        else
            for (String s: args)
                if (s.matches("^-n=.*"))
                try {
                    int top=Integer.parseInt(s.substring(3));
                    sb.append(connector.employeesOfTheMonth(top));
                } catch (NumberFormatException ex){
                    sb.append("invalid number format");
                }
                else
                    sb.append("invalid argument: "+s+"\n");
        return sb.toString();
    }


}
