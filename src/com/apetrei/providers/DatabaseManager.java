package com.apetrei.providers;

import java.sql.*;

public class DatabaseManager {

    public static void connect() {
        Connection conn = null;
        try {

            String url = "jdbc:sqlite:src/com/game_data.db";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                if (conn != null) {

                    DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                    System.out.println("DATABASE:\nDriver name: " + dm.getDriverName());
                    System.out.println("Driver version: " + dm.getDriverVersion());
                    System.out.println("Product name: " + dm.getDatabaseProductName());
                    System.out.println("Product version: " + dm.getDatabaseProductVersion());


                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Statement sql = null;
        try {
            sql = conn.createStatement();
            ResultSet rs = null;
            rs = sql.executeQuery( "SELECT * FROM SETTINGS;" );

            System.out.println(rs.getFloat("Volume"));
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }


    }



}
