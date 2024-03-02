package com.example.demo.Repositories.Comment;

import com.example.demo.Entities.Comment;
import com.example.demo.Repositories.MySqlAbstractRepository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (author_name, text, creation_date, news_id) VALUES(?, ?, CURRENT_TIMESTAMP, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAuthor_name());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getNews_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }


    @Override
    public List<Comment> allComments() {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from comments");
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt("id"),  resultSet.getString("author_name"), resultSet.getString("text"),
                        resultSet.getInt("news_id"));
                Timestamp ts = resultSet.getTimestamp("creation_date");
                comment.setCreation_date(sdf.format(ts));
                comments.add(comment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public Comment findComment(Integer id) {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int commentId = resultSet.getInt("id");
                String author_name = resultSet.getString("author_name");
                String text = resultSet.getString("text");
                Timestamp ts = resultSet.getTimestamp("creation_date");
                String creation_date = sdf.format(ts);
                int news_id = resultSet.getInt("news_id");
                comment = new Comment(commentId, author_name, text, news_id);
                comment.setCreation_date(creation_date);
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

        return comment;
    }

    @Override
    public List<Comment> findCommentsByNewsId(Integer id) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE news_id = ? ORDER BY creation_date DESC");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int commentId = resultSet.getInt("id");
                String authorName = resultSet.getString("author_name");
                String text = resultSet.getString("text");
                Timestamp ts = resultSet.getTimestamp("creation_date");
                String creationDate = sdf.format(ts);
                int newsId = resultSet.getInt("news_id");
                Comment comment = new Comment(commentId, authorName, text, newsId);
                comment.setCreation_date(creationDate);
                comments.add(comment);
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

        return comments;
    }

    @Override
    public Comment updateComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE comments SET author_name = ?, text = ?, news_id = ? WHERE id = ?"
            );
            preparedStatement.setString(1, comment.getAuthor_name());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getNews_id());
            preparedStatement.setInt(4, comment.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating comment failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return comment;
    }


    @Override
    public void deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments where id = ?");
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
