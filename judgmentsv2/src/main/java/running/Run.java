package running;

import functionality.Command;
import functionality.Parser;
import functionality.ShowCommand;

import java.io.*;

public class Run {

    public static void main(String[] args) throws IOException {

        Parser parser=new Parser();
        String s="";
        BufferedReader br=new BufferedReader(new InputStreamReader((System.in)));

        System.out.println("type :help for list of available commands");

        while (!s.equals("esc"))
        {
            s=br.readLine();
            System.out.println(parser.parse(s));
        }
    }
}
