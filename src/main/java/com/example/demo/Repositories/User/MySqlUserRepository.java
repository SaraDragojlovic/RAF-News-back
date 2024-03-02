package com.example.demo.Repositories.User;

import com.example.demo.Entities.User;
import com.example.demo.Entities.Enums.Status;
import com.example.demo.Entities.Enums.UserType;
import com.example.demo.Repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            String hashedPassword = DigestUtils.sha256Hex(user.getPassword_hash());

            preparedStatement = connection.prepareStatement("INSERT INTO users (email, first_name, last_name, user_type, status, password_hash) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirst_name());
            preparedStatement.setString(3, user.getLast_name());
            preparedStatement.setString(4, String.valueOf(user.getUser_type()));
            preparedStatement.setString(5, String.valueOf(user.getStatus()));
            preparedStatement.setString(6, hashedPassword);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("first_name"), resultSet.getString("last_name"),
                        UserType.valueOf(resultSet.getString("user_type").toUpperCase()), Status.valueOf(resultSet.getString("status").toUpperCase()), resultSet.getString("password_hash")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public List<User> pageUsers(Integer page) {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startIndex = (page - 1) * 10;

            String query = "SELECT * FROM users LIMIT ?, 11";
            statement = connection.prepareStatement(query);
            statement.setInt(1, startIndex);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        UserType.valueOf(resultSet.getString("user_type").toUpperCase()),
                        Status.valueOf(resultSet.getString("status").toUpperCase()),
                        resultSet.getString("password_hash")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User findUser(Integer id) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                UserType userType = UserType.valueOf(resultSet.getString("user_type").toUpperCase());
                Status status = Status.valueOf(resultSet.getString("status").toUpperCase());
                String password_hash = resultSet.getString("password_hash");
                user = new User(userId, email, first_name, last_name, userType, status, password_hash);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password_hash) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password_hash = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password_hash);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                UserType userType = UserType.valueOf(resultSet.getString("user_type").toUpperCase());
              //  Status status = Status.valueOf(resultSet.getString("status").toUpperCase());
                user = new User(userId, email, first_name, last_name, userType, Status.ACTIVE, password_hash);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET email = ?, first_name = ?, last_name = ?, user_type = ?, status = ?, password_hash = ? WHERE id = ?");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirst_name());
            preparedStatement.setString(3, user.getLast_name());
            preparedStatement.setString(4, String.valueOf(user.getUser_type()));
            preparedStatement.setString(5, String.valueOf(user.getStatus()));
            preparedStatement.setString(6, user.getPassword_hash());
            preparedStatement.setInt(7, user.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public void updateStatus(Integer id, String status) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT user_type FROM users WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userType = resultSet.getString("user_type");
                if ("ADMIN".equalsIgnoreCase(userType)) {
                    throw new Exception("Updating status failed, operation not allowed for ADMIN users.");
                }
            }
            this.closeStatement(preparedStatement);

            preparedStatement = connection.prepareStatement("UPDATE users SET status = ? WHERE id = ?");
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Updating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new Exception("Updating user failed, no rows affected.");
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }




    @Override
    public void deleteUser(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM users where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
