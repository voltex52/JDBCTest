package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.conn;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        Util.getConnection();

        String sqlCommand = "CREATE TABLE Users (Id INT PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(20), lastName VARCHAR(20), age INT)";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sqlCommand);
            conn.commit();
            conn.close();
            System.out.println("Таблица была создана!");
        } catch (SQLException e) {
            /*System.out.println("Ошибка при создании таблицы!");*/
        }
    }


    public void dropUsersTable() {

        Util.getConnection();

        String sqlCommand = "DROP TABLE IF EXISTS Users";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sqlCommand);
            conn.commit();
            conn.close();
            System.out.println("Таблица была удалена!");
        } catch (SQLException e) {
//            System.out.println("Ошибка при удалении таблицы!");
            try {
                conn.rollback();
            }
            catch (SQLException a) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        Util.getConnection();

        try {
            String sql = "INSERT INTO Users (firstName, lastName, age) Values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int rows = preparedStatement.executeUpdate();
            conn.commit();
            conn.close();
            System.out.println("User с именем – " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении!");
            try {
                conn.rollback();
            }
            catch (SQLException a) {
            }
        }

    }

    public void removeUserById(long id) {

        Util.getConnection();

        try {
            String sql = "DELETE FROM Users WHERE Id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            conn.commit();
            conn.close();
            System.out.println("User с id – " + id + " удалён из базы данных!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении!");
            try {
                conn.rollback();
            }
            catch (SQLException a) {
            }
        }
    }

    public List<User> getAllUsers() {

        Util.getConnection();

        String sqlCommand = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while(resultSet.next()){
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getString(3));
//                System.out.println(resultSet.getByte(4));
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                users.add(user);
            }
            conn.commit();
            conn.close();
            for (int j = 0; j < users.size(); j++) {
            System.out.println(users.get(j).toString()); }
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе пользователей!");
            e.printStackTrace();
            try {
                conn.rollback();
            }
            catch (SQLException a) {
            }
        }
        return users;
    }

    public void cleanUsersTable() {

        Util.getConnection();

        String sqlCommand = "DELETE FROM Users";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sqlCommand);
            conn.commit();
            conn.close();
            System.out.println("Данные из таблицы удалены!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении данных!");
            try {
                conn.rollback();
            }
            catch (SQLException a) {
            }
        }
    }
}
