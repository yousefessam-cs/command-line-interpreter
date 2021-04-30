package com.company;

//import java.awt.List;
//import java.util.LinkedList;

import java.io.File;

public class Parser
{
    String[] args = new String[2]; // Will be filled by arguments extracted by parse method
    String cmd; // Will be filled by the command extracted by parse method


    public boolean parse(String input)
    {
        int counter = 0 , counter2 = -1 , counter3 = -1 , counter4 = -1 , first_arg_counter = -1 , sec_arg_counter = -1,
                first_arg_counter2 = -1 , sec_arg_counter2 = -1;
        String[] list = input.split(" ");
        if (list[0] ==null)
            return false;
        for(String part : list)
        {
            if(counter==0)
            {
                cmd = part;
                counter++;
            }
            else if(counter==1)
            {
                args[0] = part;
                counter++;
            }
            else if(counter==2)
            {
                args[1] = part;
                counter++;
            }
            else
            {
                break;
            }
        }

        if( cmd.equals("date") || cmd.equals("help") || cmd.equals("pwd") || cmd.equals("clear")
                || cmd.equals("exit") )
        {
            for(int i=0;i<args.length;i++)
            {
                if(args[i]==null)
                {
                    return true;
                }
                else if(args[i]!=null)
                {
                    return false;
                }
            }
            return true;
        }


        else if( cmd.equals("cd") )
        {
            if (args[1]!=null)
                return false;

            return true;
        }


        else if( cmd.equals("more") || cmd.equals("rm") )
        {
            if(args[0]!=null && args[1]==null)
                return true;
            else
                return false;
        }


        else if( cmd.equals("mkdir") || cmd.equals("rmdir") )
        {
            for(int i=0;i<args.length-1;i++)
            {
                if(args[i]==null)
                {
                    return false;
                }
                else if(args[i]!=null && args[i+1]!=null)
                {
                    return false;
                }
                else if( args[i]!=null || (args[i]!=null && args[i+1]==null) )
                {
                    String str = args[i];
                    char[] handel = str.toCharArray();
                    for(int j=0;j<handel.length;j++)
                    {
                        if( handel[j]=='.' || handel[j]=='/' )
                        {
                            return false;
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
            }
            return true;
        }

        else if( cmd.equals("ls") )
        {
            for(int i=0;i<args.length-1;i++)
            {
                if(args[i]==null && args[i+1]==null)
                {
                    return true;
                }
                else if ((args[0].equals(">") ||args[0].equals(">>"))&&args[1]!=null)
                    return true;
                else if( args[i]!=null || (args[i]!=null && args[i+1]==null) )
                {
                    String str = args[i];
                    char[] handel = str.toCharArray();
                    for(int j=0;j<handel.length;j++)
                    {
                        if( handel[j]=='-' )
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }

            }
            return true;
        }


        else if( cmd.equals("args") )
        {
            for(int i=0;i<args.length-1;i++)
            {
                if(args[i]==null)
                {
                    return false;
                }
                else if(args[i]!=null && args[i+1]!=null)
                {
                    return false;
                }
                else if( args[i]!=null || (args[i]!=null && args[i+1]==null) )
                {
                    String str = args[i];
                    if( str.equals("cd") || str.equals("cp") || str.equals("ls") || str.equals("cat") || str.equals("more")
                            || str.equals("rm") || str.equals("mv") || str.equals("mkdir") || str.equals("rmdir") )
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return true;
        }


        else if( cmd.equals("cp") || cmd.equals("mv") )
        {
            for(int i=0;i<args.length;i++)
            {
                if(args[i]==null)
                {
                    return false;
                }
                else if(args[i]!=null)
                {
                    String str = args[i];
                    char[] handel = str.toCharArray();
                    for(int j=0;j<handel.length;j++)
                    {
                        first_arg_counter++;
                        if(first_arg_counter==0)
                        {
                            if(handel[j]=='.' || handel[j]=='/')
                            {
                                return false;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else if(first_arg_counter==1)
                        {
                            if(handel[j]=='/' || handel[j]=='.')
                            {
                                return false;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else if( first_arg_counter!=1 && handel[j]=='.')
                        {
                            int index = j+1;
                            char[] txt = {'t','x','t'};
                            for(int k=0;k<3;k++)
                            {
                                if(txt[k]==handel[index])
                                {
                                    continue;
                                }
                                index++;
                            }
                            break;
                        }
                        else if(sec_arg_counter==0)
                        {
                            if(handel[j]!='.')
                            {
                                continue;
                            }
                        }
                        else if(sec_arg_counter==1)
                        {
                            if(handel[j]=='/')
                            {
                                continue;
                            }
                            else
                            {
                                return false;
                            }
                        }

                    }
                }
            }
            return true;
        }


        else if( cmd.equals("cat") )
        {

            if(args[0]==null)
            {
                return false;
            }
            else
                return true;

        }

        else
        {
            System.out.println("your command is wrong");
            return false;
        }

    }


    public String getCmd()
    {
        return this.cmd;
    }


    public String[] getArguments()
    {
        return this.args;
    }

}