package functionality;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpCommand extends Command {

    private String manual;
    private Map<String,String> manualDetail=new HashMap<>();

    public HelpCommand(String manual, Map<String,String> manualDetail)
    {
        this.manual=manual;
        this.manualDetail=manualDetail;
        this.argsRequired=false;
        this.dataBaseRequired=false;
    }

    public String execute(List<String> arguments, Parser parser)
    {
        if (arguments==null)
            return "\n"+manual;

        StringBuilder sb=new StringBuilder("\n");
        for (String s: arguments)
            sb.append(s+":\n"+manualDetail.getOrDefault(s,"no such command")+"\n");

        return sb.toString();
    }
}
