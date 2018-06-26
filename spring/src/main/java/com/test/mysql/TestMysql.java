package com.test.mysql;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TestMysql {
    public static void main(String[] args) throws SQLException {
        System.out.println(Integer.MAX_VALUE);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TestMysql test = new TestMysql();
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://172.17.40.234:3306/canal1?characterEncoding=utf8", "root", "123456"
        );
//        Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://115.29.10.121:3306/item_dev?characterEncoding=utf8", "root", "dpjA8Z6XPXbvos"
//        );

        test.query(connection);
//        try {
//            test.query1(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        test.insert(connection);


        connection.close();

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

    private void query(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from test limit 10");
//        statement.setFetchSize(Integer.MIN_VALUE);
//        statement.setFetchDirection(ResultSet.FETCH_REVERSE);
        ResultSetMetaData metaData = statement.getMetaData();
        System.out.println(metaData.getColumnClassName(1));
        System.out.println(metaData.getColumnClassName(2));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getObject(2));
            PreparedStatement statement1 = connection.prepareStatement("select * from test_sub where pid=" + resultSet.getObject(1));
            ResultSet resultSet1 = statement1.executeQuery();
            if (resultSet1.next()) {
                System.out.println(resultSet1.getObject(1));
            }
            resultSet1.close();
            resultSet1.close();
            statement1.close();
            statement1.close();
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
