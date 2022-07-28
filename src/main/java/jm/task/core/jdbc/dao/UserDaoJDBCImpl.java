package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {
    private final String dropTable = String.format("DROP TABLE IF EXISTS %s;", Util.TABLE_NAME);
    private final String insert = String.format("INSERT INTO %s (lastName, firstName, age) VALUES (?, ?, ?);", Util.TABLE_NAME);
    private final String allRows = String.format("SELECT * FROM %s;", Util.TABLE_NAME);
    private final String cleanTable = String.format("TRUNCATE TABLE %s;", Util.TABLE_NAME);
    private final String remove = String.format("DELETE FROM %s WHERE id = ?;", Util.TABLE_NAME);


    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement( String.format(Util.SCRIPTS, Util.TABLE_NAME)).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(dropTable).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement prepareSt = connection.prepareStatement(insert);
            prepareSt.setString(1, lastName);
            prepareSt.setString(2, name);
            prepareSt.setByte(3, age);
            prepareSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement prepareSt = connection.prepareStatement(remove);
            prepareSt.setLong(1, id);
            prepareSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            ResultSet resSet = connection.prepareStatement(allRows).executeQuery();
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
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(cleanTable).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}