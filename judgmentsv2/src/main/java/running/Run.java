package running;

import functionality.Parser;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;

public class Run {

    public static void main(String[] args) {

        Parser parser=new Parser();
        String s="";

        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();

            System.out.println("type help for list of available commands");

            while (!s.equals("esc")) {
                s = lineReader.readLine();
                //s=br.readLine();
                System.out.println(parser.parse(s));
            }
        } catch (IOException ex){
            System.out.println("blad IO");
            ex.printStackTrace();
        }

    }
}
