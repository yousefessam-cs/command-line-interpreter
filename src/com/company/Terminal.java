package com.company;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Terminal
{
    private String defDir = System.getProperty("user.home") + "\\";

    public void help()
    {
        System.out.println("args : List all command arguments");
        System.out.println("cat :Concatenate files and print on the standard output");
        System.out.println("cd : Change directory");
        System.out.println("clear : Clear the console");
        System.out.println("cp : Copy items");
        System.out.println("date : Current date/time");
        System.out.println("pwd : Display current directory");
        System.out.println("rm : Remove files");
        System.out.println("mkdir : Creates new directory");
        System.out.println("rmdir : Removes empty directory");
        System.out.println("ls : Lists all files and directories in the current directory");
        System.out.println("mv : Moves file");
        System.out.println("more : Display and scroll down the output in one direction");
        System.out.println("exit : Stop all");

    }
    public void mkdir(String path)
    {
        File newFile =new File(checkPath(path));
        if (newFile.mkdir())
            System.out.println("Directory created");
        else if(newFile.exists())
            System.out.println("The Directory already exists");
        else
            System.out.println("Couldn't create a new directory");
    }
    public void rmdir(String path)
    {
        File newFile =new File(checkPath(path));

        if(newFile.exists()&&newFile.isDirectory()) {
            newFile.delete();
            if (newFile.exists())
                System.out.println("Directory is not empty");

        }
        else if(newFile.exists()&&!newFile.isDirectory())
        {
            System.out.println("It is not Directory");
        }
        else
        {
            System.out.println("Directory doesn't exist");
        }
    }
    public void more(String path)
    {
        File file =new File(checkPath(path));
        Scanner scanner = new Scanner(System.in);
        if(file.exists())
        {
            try {
                Scanner fileReader = new Scanner(file);
                while(fileReader.hasNext())
                {
                    System.out.println(fileReader.nextLine());
                    String in=scanner.nextLine();
                    if(in=="")
                        continue;
                    else if(in.equals("q"))
                    {
                        System.out.println("_______________________________________");
                        return;
                    }

                }
                fileReader.close();
            }
            catch (Exception e){
                System.out.println("This is not a file");
                return;
            }
            System.out.println("_______________________________________");
            System.out.println("File is done");
        }

        else
        {
            System.out.println(path+" doesn't exist");
        }
    }
    public void cd(String path ) {
        if (path.equals("~")) {
            defDir = System.getProperty("user.home") + "\\";
            System.out.println(defDir);
            return;
        }

        if(path.startsWith("./"))
        {
            File fileSameDir = new File(checkPath(path.substring(2)));
            if (fileSameDir.toString().endsWith("\\"))
                defDir=fileSameDir.toString();
            else
                defDir = fileSameDir.toString()+"\\";

            System.out.println("Directory changed to " + defDir);
            return;
        }
        File fileSameDir = new File(checkPath(path));
        if (path.equals("..")) {
            int counter = 0;
            int index = defDir.length();

            for (int i = 0; i < defDir.length() - 1; i++) {

                if ((defDir.charAt(i) == '\\')) {
                    index = i;
                }
            }
            defDir=defDir.substring(0, index + 1);
            System.out.println("Directory changed to " + defDir);
            return;

        } else if (fileSameDir.exists() && fileSameDir.isDirectory()) {
            if (fileSameDir.toString().endsWith("\\"))
                defDir=fileSameDir.toString();
            else
                defDir = fileSameDir.toString()+"\\";
            System.out.println(defDir );
            return;
        } else if (!fileSameDir.exists()) {
            System.out.println("bash: cd:\" " + path + " No such directory");
        }
        else {
            System.out.println("bash: cd:\" " + path + " No such directory");
        }

    }
    public void cp(String sourcePath, String destinationPath)throws IOException {
        File f1 = new File(checkPath(sourcePath));
        FileReader Reader = null;
        if (f1.exists()) {
            try {
                Reader = new FileReader(f1.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("can't find source to copy");
            return ;
        }

        File f2 = new File(checkPath(destinationPath));
        if (f2.isDirectory())
        {
            String temp=f1.getAbsolutePath();
            while (temp.contains("\\"))
            {
                temp= temp.substring( temp.indexOf("\\")+1);
            }
            f2=new File(checkPath(destinationPath)+"\\"+temp);
        }
        FileWriter Writer = null;

        try {
            Writer = new FileWriter(f2.getAbsolutePath());
            if(f2.createNewFile())
            {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner Read = new Scanner(Reader);
        String s;
        StringBuilder x =new StringBuilder();
        while (Read.hasNextLine()) {
            s=Read.nextLine();
            x.append(s+"\n");
        }

        Writer.write(x.toString());
        Read.close();
        Writer.close();

    }
    public void mv(String sourcePath, String destinationPath) throws IOException {
        cp(sourcePath,destinationPath);
        rm(sourcePath);
    }
    public void rm(String sourcePath) {
        File f = new File(checkPath(sourcePath));
        if (f.exists() && !f.isDirectory()) {
            if (f.delete())
                return;
            else
                System.out.println("Not deleted");
        }
        else if (f.exists() && f.isDirectory())
            System.out.println("Can not delete directory");
    }
    public String checkPath(String path) {
        if (path.startsWith("C:\\") || path.startsWith("D:\\") || path.startsWith("E:\\") || path.startsWith("G:\\") ||path.startsWith("F:\\")) {
            File testFile = new File(path);
            if (path.equals("C:\\") || path.equals("D:\\") || path.equals("E:\\") || path.equals("G:\\") || path.equals("F:\\"))
                return path;
            if (testFile.exists()) {
                if (path.endsWith("\\"))
                    return path;
                else
                    return path + "\\";
            }

        } else {
            if (path.endsWith("\\"))
                return defDir + path;
            else
                return defDir + path + "\\";
        }
        return path;
    }
    public void ls()
    {
        System.out.print("\n");

        File myfile = new File(defDir);
        File[] myfilearr = myfile.listFiles();
        for(File file : myfilearr)
        {
            String str = file.toString();
            char[] handel = str.toCharArray();
            for(int i=defDir.length();i<handel.length;i++)
            {
                System.out.print(handel[i]);
                //continue;
            }
            System.out.print("\n");
        }

    }

    public void ls(String arg1)
    {
        if(arg1.equals("-l") || arg1.equals("-a"))
        {
            System.out.print("\n");

            File myfile = new File(defDir);
            File[] myfilearr = myfile.listFiles();
            for(File file : myfilearr)
            {
                String str = file.toString();
                char[] handel = str.toCharArray();
                for(int i=defDir.length();i<handel.length;i++)
                {
                    System.out.print(handel[i]);
                    //continue;
                }
                System.out.print("\n");
            }
        }
        else
        {
            System.out.println("invalid argument");
        }
    }
    public void ls(String arg1,String arg2)
    {
        String redirect = arg1;
        String fileName = arg2;
        String fileType = fileName.substring(fileName.length()-3, fileName.length());
        if((fileType.equalsIgnoreCase("txt"))&&(redirect.equals(">")))
        {
            File myfile = new File(defDir);
            File[] myfilearr = myfile.listFiles();
            StringBuilder temp=new StringBuilder();
            for(File file : myfilearr)
            {
                String str = file.toString();
                char[] handel = str.toCharArray();
                for(int i=defDir.length();i<handel.length;i++)
                {
                    temp.append(handel[i]);
                }
                temp.append("\n");
            }
            try
            {
                BufferedWriter writer=new BufferedWriter(new FileWriter(checkPath( fileName)));
                writer.write(temp.toString());
                writer.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }


        }
        else if((fileType.equalsIgnoreCase("txt")&&(redirect.equals(">>"))))
        {
            File myfile = new File(defDir);
            File[] myfilearr = myfile.listFiles();
            StringBuilder temp=new StringBuilder();
            for(File file : myfilearr)
            {
                String str = file.toString();
                char[] handel = str.toCharArray();
                for(int i=defDir.length();i<handel.length;i++)
                {
                    temp.append(handel[i]);
                }
                temp.append("\n");
            }
            try
            {
                BufferedWriter writer=new BufferedWriter(new FileWriter(checkPath( fileName),true));
                writer.write(temp.toString());
                writer.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else
            System.out.println("wrong command or file type");
    }

    public void exit()
    {
        System.exit(0);
    }

    public void pwd()
    {
        System.out.println(defDir);
    }

    public void args(String command)
    {
        if(command.equals("cp") || command.equals("mv"))
        {
            System.out.println("arg1: SourcePath, arg2: DestinationPath");
        }
        else if(command.equals("cd"))
        {
            System.out.println("arg: new direcotry");
        }
        else if(command.equals("cat"))
        {
            System.out.println("arg1: >(to create) or >>(to append) , arg2: file name");
        }
        else if(command.equals("more") || command.equals("rm"))
        {
            System.out.println("arg: file name");
        }
        else if(command.equals("mkdir") || command.equals("rmdir"))
        {
            System.out.println("arg: directory name");
        }
        else if(command.equals("args"))
        {
            System.out.println("arg1: command");
        }
        else if(command.equals("ls") || command.equals("date") || command.equals("clear") || command.equals("help")
                || command.equals("exit") || command.equals("pwd"))
        {
            System.out.println("arg: NONE");
        }
        else
        {
            System.out.println("error");
        }

    }

    public void date()
    {
        Date date = new Date();
        SimpleDateFormat d = new SimpleDateFormat("E dd MMM YYYY HH:mm:ss aa");
        System.out.println(d.format(date));
    }

    public  void clear()
    {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }
    public void cat(String arr[],int count)
    {
        StringBuilder sb = new StringBuilder();

        int repetition = 0;
        if (count==2) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(">>")) {
                    repetition = 2;
                } else if (arr[i].contains(">")) {
                    repetition = 1;
                }
            }
        }
        if(count == 2)
        {
            if(repetition==2)
            {
                String redirect = arr[0];
                String fileName = arr[1];
                redirect2(fileName);
            }
            else if(repetition==1)
            {
                String redirect = arr[0];
                String fileName = arr[1];
                redirect1(fileName);
            }
            else
            {
                String[] temp = new String[2];
                temp[0]=arr[0];
                cat(temp,1);
                temp[0]=arr[1];
                cat(temp,1);
            }
        }
        else if(count == 1)
        {
            String fileName = arr[0];

            try
            {
                File myFile = new File(checkPath(fileName) );
                if (myFile.exists() && !myFile.isDirectory()) {
                    Scanner fileReader = new Scanner(myFile);
                    while (fileReader.hasNextLine()) {
                        String fileContent = fileReader.nextLine();
                        System.out.println(fileContent);
                    }
                    fileReader.close();
                }
                else
                    System.out.println("File doesn't exist");
            }
            catch(FileNotFoundException e)
            {
                System.out.println(e);
            }

        }
        System.out.println(sb);
    }

    public void redirect2(String fileName)
    {
        Scanner input = new Scanner(System.in);
        String fileContent = null;
        System.out.println("Enter content of your file");
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter(fileName,true));
            while (true)
            {
                if (input.hasNext()) {
                    fileContent = input.nextLine();
                    if (fileContent.equals("q"))
                        break;
                    writer.write(fileContent+"\n");

                }
            }
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void redirect1(String fileName)
    {
        Scanner input = new Scanner(System.in);
        String fileContent = null;
        System.out.println("Enter content of your file");
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter(fileName));
            while (true)
            {
                if (input.hasNext()) {
                    fileContent = input.nextLine();
                    if (fileContent.equals("q"))
                        break;
                    writer.write(fileContent+"\n");

                }
            }


            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}
