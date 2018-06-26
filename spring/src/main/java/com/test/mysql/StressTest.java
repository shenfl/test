package com.test.mysql;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StressTest {

    private String url;
    private String username;
    private String password;
    private String sourcePath;
    private String configPath;
    private Connection connection;
    private Thread runner;
    private Integer batch;
    private Integer sleep;
    private int count = 0;
    private List<String> list = new ArrayList<>();
    private StressTest(String configPath, String sourcePath) {
        this.configPath = configPath;
        this.sourcePath = sourcePath;
    }

    private void loadProperties() {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            prop.load(classLoader.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            System.out.println("Load config file error: " + e);
            System.exit(-1);
        }
        url = prop.getProperty("db.url");
        username = prop.getProperty("db.username");
        password = prop.getProperty("db.password");
        batch = Integer.valueOf(prop.getProperty("batch"));
        sleep = Integer.valueOf(prop.getProperty("sleep"));
        if (url == null || username == null || password == null || batch == null || sleep == null) {
            System.out.println("Config is not right");
            System.exit(-1);
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Can not get connection " + e.toString());
            System.exit(-1);
        }
    }

    private void start() {
        runner = new Thread(new Runnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement("insert into test(name) values(?)");
                } catch (SQLException e) {
                    System.out.println("Get statement error: " + e);
                    System.exit(-1);
                }

                while (!Thread.interrupted()) {
                    for (int i = 0; i < batch; i++) {
                        try {
                            statement.setString(1, list.get(count));
                            statement.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        count++;
                        if (count >= list.size()) count = 0;
                    }
                    System.out.println(count);
//                    try {
//                        Thread.sleep(sleep);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        System.out.println("Interrupted...");
//                    }
                }
            }
        });
        runner.start();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage:");
            System.out.println("\targs0: config file");
            System.out.println("\targs1: source file");
            System.exit(-1);
        }
        StressTest stressTest = new StressTest(args[0], args[1]);
        stressTest.loadProperties();
        stressTest.loadContent();
        stressTest.start();
    }

    private void loadContent() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    StressTest.class.getClassLoader().getResourceAsStream("content"), "utf-8"));
        } catch (Exception e) {
            System.out.println("Load content file error: " + e);
            System.exit(-1);
        }
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.trim().length() != 0) list.add(line);
            }
        } catch (Exception e) {
            System.out.println("Read content file error: " + e);
            System.exit(-1);
        }
    }

}
