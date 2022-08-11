package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        String sqlCommand = "CREATE TABLE Users (Id INT PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(20), lastName VARCHAR(20), age INT)";
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(sqlCommand);
            connection.commit();
            System.out.println("Таблица была создана!");
        } catch (SQLException e) {
            /*System.out.println("Ошибка при создании таблицы!");*/
            try (Connection connection = Util.getConnection()) {
                connection.rollback();
            }
            catch (SQLException a) {
            }
        }
    }


    public void dropUsersTable() {

        String sqlCommand = "DROP TABLE IF EXISTS Users";
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(sqlCommand);
            connection.commit();
            System.out.println("Таблица была удалена!");
        } catch (SQLException e) {
//            System.out.println("Ошибка при удалении таблицы!");
            try (Connection connection = Util.getConnection()) {
                connection.rollback();
            }
            catch (SQLException a) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getConnection()) {
            String sql = "INSERT INTO Users (firstName, lastName, age) Values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int rows = preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении!");
            try (Connection connection = Util.getConnection()) {
                connection.rollback();
            }
            catch (SQLException a) {
            }
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = Util.getConnection()) {
            String sql = "DELETE FROM Users WHERE Id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с id – " + id + " удалён из базы данных!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении!");
            try (Connection connection = Util.getConnection()) {
                connection.rollback();
            }
            catch (SQLException a) {
            }
        }
    }

    public List<User> getAllUsers() {

        String sqlCommand = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sqlCommand);
            while(resultSet.next()){
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                users.add(user);
            }
            connection.commit();
            for (int j = 0; j < users.size(); j++) {
            System.out.println(users.get(j).toString()); }
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе пользователей!");
            e.printStackTrace();
            try (Connection connection = Util.getConnection()) {
                connection.rollback();
            }
            catch (SQLException a) {
            }
        }
        return users;
    }

    public void cleanUsersTable() {

        String sqlCommand = "DELETE FROM Users";
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(sqlCommand);
            connection.commit();
            System.out.println("Данные из таблицы удалены!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении данных!");
            try (Connection connection = Util.getConnection()) {
                connection.rollback();
            }
            catch (SQLException a) {
            }
        }
    }
}
