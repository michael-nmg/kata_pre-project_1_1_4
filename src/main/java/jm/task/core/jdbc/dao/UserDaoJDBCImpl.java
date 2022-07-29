package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {
    private final String tableNameUserDao = "users";
    private final String initTableUserDao = String.format("CREATE TABLE IF NOT EXISTS %s(" +
            "id BIGINT NOT NULL auto_increment," +
            "lastName TINYTEXT," +
            "firstName TINYTEXT," +
            "age TINYINT," +
            "PRIMARY KEY (id));", tableNameUserDao);
    private final String dropTableUserDao = String.format("DROP TABLE IF EXISTS %s;", tableNameUserDao);
    private final String insertUserDao = String.format("INSERT INTO %s (lastName, firstName, age) VALUES (?, ?, ?);", tableNameUserDao);
    private final String allRowsUserDao = String.format("SELECT * FROM %s;", tableNameUserDao);
    private final String cleanTableUserDao = String.format("TRUNCATE TABLE %s;", tableNameUserDao);
    private final String removeUserDao = String.format("DELETE FROM %s WHERE id = ?;", tableNameUserDao);
    private final Connection connectionUserDao = Util.getConnection();


    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try {
            connectionUserDao.prepareStatement(initTableUserDao).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connectionUserDao.prepareStatement(dropTableUserDao).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement prepareSt = connectionUserDao.prepareStatement(insertUserDao);
            prepareSt.setString(1, lastName);
            prepareSt.setString(2, name);
            prepareSt.setByte(3, age);
            prepareSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement prepareSt = connectionUserDao.prepareStatement(removeUserDao);
            prepareSt.setLong(1, id);
            prepareSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            ResultSet resSet = connectionUserDao.prepareStatement(allRowsUserDao).executeQuery();
            while (resSet.next()) {
                result.add(new User(
                        resSet.getString("firstName"),
                        resSet.getString("lastName"),
                        resSet.getByte("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {
        try {
            connectionUserDao.prepareStatement(cleanTableUserDao).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}