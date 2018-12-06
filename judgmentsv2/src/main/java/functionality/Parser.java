package functionality;

import db.DataBaseOrders;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Parser {
    private DataBaseOrders connector=null;
    Set<String> available_commands=new HashSet<>();
    Map<String,Command> commands=new HashMap<>();

    public void setConnector (DataBaseOrders connector)
    {
        this.connector=connector;
    }

    public DataBaseOrders getConnector()
    {
        return this.connector;
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
        available_commands.addAll
                (Arrays.asList(":load", ":show", ":clear_load", ":list", ":stat",":top",":judge",":reason"));

        String manual=FileStringParser.getStringFromFile("manual.txt");
        Map<String,String> manualDetail=new HashMap<>();

        for (String cmd: available_commands.toArray(new String[0]))
                manualDetail.put(cmd.substring(1),
                        FileStringParser.getStringFromFile(cmd.substring(1) + "_man.txt"));

        commands.put(":help",new HelpCommand(manual,manualDetail));

        try {
            for (String s : available_commands) {
                String className ="functionality."+ Character.toUpperCase(s.charAt(1)) + s.substring(2) + "Command";

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

            return command.execute(arguments,this);

        }
        return null;
    }

}
