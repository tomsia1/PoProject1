package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Parser {
    private DataBaseOrders connector=null;
    private Set<String> available_commands=new HashSet<>();
    private Map<String,Command> commands=new HashMap<>();
    private Map<String,String> cache;

    public void setConnector (DataBaseOrders connector)
    {
        this.connector=connector;
    }

    public DataBaseOrders getConnector()
    {
        return this.connector;
    }

    public void resetCache()
    {
        cache=new HashMap<>();
        cache.put("esc","");
        cache.put("","");
    }

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
        resetCache();
        available_commands.addAll
                (Arrays.asList("load", "rubrum", "clear_load", "list", "months","judges",
                        "judge","content","regulations", "courts","jury"));

        String manual=FileStringParser.getStringFromFile("manual.txt");
        Map<String,String> manualDetail=new HashMap<>();

        for (String cmd: available_commands)
                manualDetail.put(cmd,
                        FileStringParser.getStringFromFile(cmd + "_man.txt"));

        commands.put("help",new HelpCommand(manual,manualDetail));

        try {
            for (String s : available_commands) {
                String className ="functionality."+ Character.toUpperCase(s.charAt(0)) + s.substring(1) + "Command";

                Class<?> klass = Class.forName(className);
                Constructor<?> ctor=klass.getConstructor();
                Command command=(Command) ctor.newInstance();
                commands.put(s,command);
            }
        }catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e)
        {
            e.printStackTrace();
        }


    }

    public String parse(String s) throws IOException
    {
        if (cache.containsKey(s))
            return cache.get(s);

        List<String> input=split(s);
        if (input.size()!=0)
        {
            List<String> arguments=null;
            String command_name=input.get(0);
            if (input.size()>1) {
                arguments=new LinkedList<>();
                arguments.addAll(input);
                arguments.remove(0);
            }

            Command command=commands.get(command_name);

            if (command==null)
                return "no such command";

            if (connector==null && command.isDataBaseRequired())
                return "no files loaded";

            if (arguments==null && command.areArgsRequired())
                return "missing command options or arguments";

            String result=command.execute(arguments,this);

            if (!(command instanceof LoadCommand) && !(command instanceof Clear_loadCommand))
                cache.put(s,result);

            return result;

        }
        return null;
    }

}
