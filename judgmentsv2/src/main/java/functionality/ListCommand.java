package functionality;

import java.util.List;

public class ListCommand extends Command {

    public String execute(List<String> args, Parser parser)
    {
        StringBuilder sb=new StringBuilder("\n");

        for (String s: args)
            if (s.equals("-j"))
                sb.append(parser.getConnector().listJudges());
            else
            if (s.equals("-s"))
                sb.append(parser.getConnector().listSignature());
            else
                sb.append("invalid list argument: "+s+"\n");

        return sb.toString();
    }
}
