package running;

import functionality.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
