package com.test.mysql;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TestMysql {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println(Integer.MAX_VALUE);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TestMysql test = new TestMysql();
//        Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://115.29.10.121:3306/canal1?characterEncoding=utf8", "root", "dpjA8Z6XPXbvos"
//        );
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://mysql1.dev.scsite.net:3306/souche_item?characterEncoding=utf8", "test_rw", "FPTIBCZU3cHG9Lrb"
        );

//        test.query3(connection);
        PreparedStatement preparedStatement = connection.prepareStatement("update item_front_spu set category_id=3 where front_spu_id=16427700915;");
        preparedStatement.execute();
        preparedStatement.close();
//        test.update(connection);
//        try {
//            test.query1(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        test.insert(connection);

        connection.close();

    }

    private void update(Connection connection) throws SQLException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/shenfl/Downloads/全量车型名称.csv")));
        String s;

//        PreparedStatement statement = connection.prepareStatement("insert into test (name) values (?)");
//        for (int j = 0; j < 1000; j++) {
//            for (int i = 0; i < 16; i++) {
//                statement.setNString(1, "he" + j);
//                statement.addBatch();
//            }
//            statement.executeBatch();
//        }
        PreparedStatement statement = connection.prepareStatement("insert into test_index (name) values (?)");
        aa: while (true) {
            for (int i = 0; i < 100; i++) {
                s = br.readLine();
                if (s != null) {
                    statement.setNString(1, s);
                    statement.addBatch();
                } else {
                    statement.executeBatch();
                    break aa;
                }
            }
            System.out.println("-------------");
            statement.executeBatch();
        }
        statement.close();
    }

    private static void aa(String s) {
        s = "dfd";
    }

    private void insert(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into test(name) values('会计法麦当劳渴望')");
        boolean execute = statement.execute();
        System.out.println(execute);
    }

    private void query2(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("show index from test");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("Column_name"));
        }
        System.out.println("end");
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) statement.close();
    }

    private void query1(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from item_dev.stock");
        ResultSetMetaData metaData = statement.getMetaData();
        System.out.println(metaData.getColumnClassName(1));
        System.out.println(metaData.getColumnClassName(2));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
//            System.out.println(resultSet.getObject(1));
//            System.out.println(resultSet.getObject(2));
//            System.out.println(resultSet.getObject(3));
//            System.out.println(resultSet.getObject(4));
//            Map<String, Object> map = new HashMap<>();
//            map.put("aa", resultSet.getObject(1));
//            map.put("bb", resultSet.getObject(2));
//            map.put("cc", resultSet.getObject(3));
//            map.put("dd", resultSet.getObject(4));
            System.out.println(resultSet.getObject(1));
        }
        System.out.println("end");
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) statement.close();
    }

    private void query3(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from order_flows where id=178");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Object object = resultSet.getInt(9);
            System.out.println(object);

//            PreparedStatement statement1 = connection.prepareStatement("select * from test_sub where pid=" + resultSet.getObject(1));
//            ResultSet resultSet1 = statement1.executeQuery();
//            if (resultSet1.next()) {
//                System.out.println(resultSet1.getObject(1));
//            }
//            resultSet1.close();
//            statement1.close();
        }
        System.out.println("end...");
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) statement.close();
    }

    private void query(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from testbit limit 10");
//        statement.setFetchSize(Integer.MIN_VALUE);
//        statement.setFetchDirection(ResultSet.FETCH_REVERSE);
        ResultSetMetaData metaData = statement.getMetaData();
        System.out.println(metaData.getColumnClassName(1));
        System.out.println(metaData.getColumnName(1));
        System.out.println(metaData.getColumnClassName(2));
        System.out.println(metaData.getColumnName(2));
        System.out.println(metaData.getColumnClassName(3));
        System.out.println(metaData.getColumnName(3));
        System.out.println(metaData.getColumnClassName(4));
        System.out.println(metaData.getColumnName(4));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Object object = resultSet.getObject(1);
            System.out.println(object);
            if (object != null) System.out.println(object.getClass());
            object = resultSet.getInt(2);
            System.out.println(object);
            if (object != null) System.out.println(object.getClass());
            object = resultSet.getInt(3);
            System.out.println(object);
            if (object != null) System.out.println(object.getClass());
            object = resultSet.getObject(4);
            System.out.println(object);
            if (object != null) System.out.println(object.getClass());

//            PreparedStatement statement1 = connection.prepareStatement("select * from test_sub where pid=" + resultSet.getObject(1));
//            ResultSet resultSet1 = statement1.executeQuery();
//            if (resultSet1.next()) {
//                System.out.println(resultSet1.getObject(1));
//            }
//            resultSet1.close();
//            statement1.close();
        }
        System.out.println("end...");
        if (resultSet != null) {
            resultSet.close();
            resultSet.close();
        }
        if (statement != null) statement.close();
        if (statement != null) statement.close();
    }
}
