package functionality;

import java.io.IOException;
import java.util.*;

public class Parser {
    private Orders orders=null;
    Set<String> available_commands=new HashSet<>();
    String manual;
    Map<String,String> manualDetail=new HashMap<>();

    private List<String> split(String s)
    {
        s=s.trim();
        List<String> result=new LinkedList<>();
        StringBuilder sb=new StringBuilder();
        boolean opened=false;

        for (int i=0;i<s.length();i++)
        {
            if (s.charAt(i)=='"')
            {
                if (opened && sb.length()!=0) {
                    result.add(sb.toString());
                    sb = new StringBuilder();
                }
                opened=!opened;
                continue;
            }

            if (s.charAt(i)==' ')
                if (!opened)
                {
                    if (sb.length()!=0)
                    {
                        result.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    continue;
                }

            sb.append(s.charAt(i));

        }
        if (sb.length()!=0)
            result.add(sb.toString());

        return result;
    }

    public Parser()
    {
        available_commands.addAll(Arrays.asList(":load", ":show", ":clear_load", ":help", ":list", ":stat",":top",":judge",":reason"));

        manual=FileStringParser.getStringFromFile("manual.txt");

        for (String cmd: available_commands.toArray(new String[0]))
            if (!cmd.equals(":help"))
                manualDetail.put(cmd.substring(1), FileStringParser.getStringFromFile(cmd.substring(1) + "_man.txt"));

    }

    public String parse(String s) throws IOException
    {
        List<String> input=split(s);
        if (input.size()!=0)
        {
            List<String> arguments=null;
            String command=input.get(0);
            if (input.size()>1) {
                arguments=new LinkedList<>();
                arguments.addAll(input);
                arguments.remove(0);
            }

            if (!available_commands.contains(command))
                return "invalid command";

            if (command.equals(":load"))
            {
                if (arguments==null)
                    return "enter load paths";

                if (orders==null)
                    orders=new Orders();
                orders.append(arguments);

               return "files loaded";
            }

            if (command.equals(":help"))
                return help(arguments);

            if (command.equals("clear_load"))
            {
                orders=null;
                return "cleared";
            }

            if (orders==null)
                return "no file loaded";

            if (arguments==null)
                return "missing command arguments";

            if (command.equals(":show"))
                return orders.showMetric(arguments);

            if (command.equals(":list"))
                return list(arguments);

            if (command.equals(":stat"))
                return stat(arguments);

            if (command.equals(":reason"))
                return orders.reason(arguments);

            if (command.equals(":judge"))
                return orders.judgesWithNumbers(arguments);

            if (command.equals(":top"))
                return top(arguments);
        }
        return null;
    }

    private String top(List<String> args)
    {
        StringBuilder sb=new StringBuilder();
        try
        {
            for (String s: args)
            {
                if (s.matches("^-r=.*"))
                {
                    int top=Integer.parseInt(s.substring(3));
                    sb.append(orders.topRegulations(top));
                }
                else
                    if (s.matches("^-j=.*"))
                    {
                        int top=Integer.parseInt(s.substring(3));
                        sb.append(orders.employeesOfTheMonth(top));
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

    private String help(List<String> arguments)
    {
        if (arguments==null)
            return "\n"+manual;

        StringBuilder sb=new StringBuilder("\n");
        for (String s: arguments)
            sb.append(s+":\n"+manualDetail.getOrDefault(s,"no such command")+"\n");

        return sb.toString();
    }

    private String list(List<String> args)
    {
        StringBuilder sb=new StringBuilder("\n");

        for (String s: args)
        if (s.equals("-j"))
            sb.append(orders.listJudges());
        else
            if (s.equals("-s"))
                sb.append(orders.listSignature());
            else
                sb.append("invalid list argument: "+s+"\n");

        return sb.toString();
    }

    private String stat(List<String> args)
    {
        StringBuilder sb=new StringBuilder("\n");

        for (String s: args)
            if (s.equals("-j"))
                sb.append(orders.avarageNumberOfAnnMarieCheerfulInCourt());
            else
            if (s.equals("-m"))
                sb.append(orders.monthlyStats());
            else
                if (s.equals("-t"))
                    sb.append(orders.courtTypeStats());
                else
                    sb.append("invalid stat argument: "+s+"\n");

        return sb.toString();
    }
}
