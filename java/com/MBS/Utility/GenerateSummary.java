package com.MBS.Utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public class GenerateSummary {

    static String filepath = "result/results.properties";

    public static Properties loadPropertiesFile() throws Exception
    {

        Properties prop = new Properties();
        InputStream in = new FileInputStream(filepath);
        prop.load(in);
        in.close();
        return prop;
    }

    public static void main(String args[]) throws Exception {

        ArrayList<String> a = new ArrayList<String>();
        String Total = null;

        try {
            Properties prop = loadPropertiesFile();
            Set<Object> set = prop.keySet();
            Collection<Object> set1 = prop.values();

            System.out.println("Reading all key values from properties file \n");

            for (Object o : set) {
                String key = (String) o;
                //System.out.println("key " + key + " value " + prop.getProperty(key));

                Total = prop.getProperty("Total Stories");

                String value = prop.getProperty(key);
                if (value.trim().equals("FAIL")) {
                    a.add(key);
                    System.err.println("ADDED KEY -[" + key + "]-into arraylist.............");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.err.println("Total Stroies Run :-" + Total);
        System.err.println("Total Pass :-" + ((Integer.parseInt(Total)) - (a.size())));
        System.err.println("Total Fail :-" + a.size());

        String TotalSto = Total;
        int pass = ((Integer.parseInt(Total)) - (a.size()));
        int fail = a.size();

        try (FileWriter writer = new FileWriter("summary.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write("----------------------------------" + "\n");
            bw.write("   Completed : *" + TotalSto + "*\n");
            bw.write("   Passed    : *" + pass + "*\n");
            bw.write("   Failed    : *" + fail + "*\n");
            bw.write("----------------------------------");
            bw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        try {
            PrintWriter writer = new PrintWriter(filepath);
            writer.print("");
            writer.close();
            System.out.println("--->");
        } catch (Exception e) {
        }
    }

}
