import com.company.Terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;
import java.util.regex.Pattern;

public class cmd {

    public static void main(String[] args) throws IOException {
        Terminal ter =new Terminal();
        Scanner scanner = new Scanner(System.in);

        boolean choice ;
        while (true){
            Parser parser =new Parser();
            System.out.print("$");
            String command=scanner.nextLine();
            if (command =="")
                continue;
            String[] pipe =command.split(Pattern.quote("|"));
            for (int i = 0; i < pipe.length; i++) {
                if (i==0 && pipe.length>1)
                {
                    pipe[i]=pipe[i].substring(0, pipe[i].length()-1);
                }
                else if(i>0)
                {
                    pipe[i]=pipe[i].substring(1);
                }
                choice = parser.parse(pipe[i]);
                if(choice)
                {
                    String[] arg =new String[2];
                    arg= parser.getArguments();
                    if(parser.getCmd().equals("mkdir"))
                    {
                        ter.mkdir(arg[0]);
                    }
                    else if (parser.getCmd().equals("rmdir"))
                    {
                        ter.rmdir(arg[0]);
                    }
                    else if (parser.getCmd().equals("more"))
                    {
                        ter.more(arg[0]);
                    }
                    else if(parser.getCmd().equals("help"))
                    {
                        ter.help();
                    }
                    else if (parser.getCmd().equals("ls"))
                    {
                        if (arg[0]==null)
                            ter.ls();
                        else if(arg[0].equals(">") ||arg[0].equals(">>"))
                        {
                            ter.ls(arg[0],arg[1]);
                        }
                        else
                            ter.ls(arg[0]);
                    }
                    else if(parser.getCmd().equals("cd"))
                    {
                        if (arg[0]!=null)
                            ter.cd(arg[0]);
                        else
                            ter.cd("~");
                    }
                    else if(parser.getCmd().equals("cp"))
                    {
                        ter.cp(arg[0],arg[1]);
                    }
                    else if (parser.getCmd().equals("mv"))
                    {
                        ter.mv(arg[0],arg[1]);
                    }
                    else if (parser.getCmd().equals("rm"))
                    {
                        ter.rm(arg[0]);
                    }
                    else if (parser.getCmd().equals("clear"))
                    {
                        clear();
//						ter.clear();
                    }
                    else if (parser.getCmd().equals("pwd"))
                    {
                        ter.pwd();
                    }
                    else if (parser.getCmd().equals("args"))
                    {
                        ter.args(arg[0]);
                    }
                    else if (parser.getCmd().equals("date"))
                    {
                        ter.date();
                    }
                    else if (parser.getCmd().equals("exit"))
                    {
                        ter.exit();
                    }
                    else if (parser.getCmd().equals("cat"))
                    {
                        if (arg[1]==null)
                            ter.cat(arg,1);
                        else
                            ter.cat(arg,2);
                    }
                }
                else if(choice ==false)
                    System.out.println("Wrong command");


            }

        }

    }
    public static void clear()
    {
        try
        {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


}
