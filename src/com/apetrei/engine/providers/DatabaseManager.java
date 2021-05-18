package com.apetrei.engine.providers;

import com.apetrei.misc.exceptions.ValueNotFoundException;

import java.sql.*;

public class DatabaseManager {

    static private DatabaseManager databaseManager;
    private Connection connection;

    private DatabaseManager(){
        connect();
    }

    private void connect() {
        try {
            String url = "jdbc:sqlite:src/com/game_data.db";
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        } finally {

            try {
                if (connection != null) {
                    if(ConfigHandler.isDebugMode()) {
                        DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                        System.out.println("DATABASE:\nDriver name: " + dm.getDriverName());
                        System.out.println("Driver version: " + dm.getDriverVersion());
                        System.out.println("Product name: " + dm.getDatabaseProductName());
                        System.out.println("Product version: " + dm.getDatabaseProductVersion());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    private void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //WORKING ON THE DATABASE

    //READ
    public float recoverFloat(String collumnName, String table) throws ValueNotFoundException {
        String sql_command = "SELECT * FROM "+table+" WHERE ID = 1;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( sql_command );

            return  resultSet.getFloat(collumnName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new ValueNotFoundException("collumnName");
    }
    public int recoverInt(String collumnName, String table) throws ValueNotFoundException {
        String sql_command = "SELECT * FROM "+table+" WHERE ID = 1;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( sql_command );;

            return  resultSet.getInt(collumnName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new ValueNotFoundException("collumnName");
    }

    //WRITE
    public void writeFloat(float value, String collumnName, String table) {
        String sql_command = "UPDATE "+table+" SET "+collumnName+" = ?  WHERE ID = 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql_command);
            preparedStatement.setFloat(1, value);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void writeInt(int value, String collumnName, String table) {
        String sql_command = "UPDATE "+table+" SET "+collumnName+" = ?  WHERE ID = 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql_command);
            preparedStatement.setInt(1, value);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //__________________________________IMPORTANT___________________________

    //UPDATE DATABASE
    public void resetGameState() {
        ConfigHandler.setCurrentLevel(1);
        ConfigHandler.setScore(0);
        updateDataBase();
    }

    public void updateDataBase(){
        //Settings
        writeFloat(ConfigHandler.getSoundVolume(),"VOLUME","SETTINGS");
        writeFloat(ConfigHandler.getMusicVolume(),"MUSICVOLUME","SETTINGS");

        //Game state
        writeFloat(ConfigHandler.getCurrentLevel(),"CURRENT_LEVEL","GAME_STATE");
        writeFloat(ConfigHandler.getScore(),"SCORE","GAME_STATE");
    }


    public void updateConfigClass(){
        try {
            //Settings
            ConfigHandler.setSoundVolume( recoverFloat("VOLUME","SETTINGS") );
            ConfigHandler.setMusicVolume( recoverFloat("MUSICVOLUME","SETTINGS") );

            //Game state
            ConfigHandler.setCurrentLevel( recoverInt("CURRENT_LEVEL","GAME_STATE") );
            ConfigHandler.setScore( recoverInt("SCORE","GAME_STATE") );

        } catch (ValueNotFoundException e) {
            e.printStackTrace();
        }
    }


    //____________________________________SINGLETON___________________
    static public DatabaseManager getInstance(){
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }
}
