package com.example.demo.Repositories.News_Tags;

import com.example.demo.Entities.News_Tags;
import com.example.demo.Repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNews_TagsRepository extends MySqlAbstractRepository implements News_TagsRepository {

    @Override
    public News_Tags addNews_Tags(News_Tags news_tags) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO news_tags (news_id, tag_id) VALUES(?, ?)");
            preparedStatement.setInt(1, news_tags.getNews_id());
            preparedStatement.setInt(2, news_tags.getTag_id());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return news_tags;
    }

    @Override
    public List<News_Tags> allNews_Tags() {
        List<News_Tags> news_tagsList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news_tags");
            while (resultSet.next()) {
                news_tagsList.add(new News_Tags(resultSet.getInt("news_id"), resultSet.getInt("tag_id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news_tagsList;
    }

    @Override
    public News_Tags findNews_TagsByNewsIdAndTagId(Integer news_id, Integer tag_id) {
        News_Tags news_tags = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tags WHERE news_id = ? AND tag_id = ?");
            preparedStatement.setInt(1, news_id);
            preparedStatement.setInt(2, tag_id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                news_tags = new News_Tags(resultSet.getInt("news_id"), resultSet.getInt("tag_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news_tags;
    }


    @Override
    public List<News_Tags> findNews_TagsByNewsId(Integer news_id) {
        List<News_Tags> news_tagsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tags WHERE news_id = ?");
            preparedStatement.setInt(1, news_id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                news_tagsList.add(new News_Tags(resultSet.getInt("news_id"), resultSet.getInt("tag_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news_tagsList;
    }

    @Override
    public List<News_Tags> findNews_TagsByTagId(Integer tag_id) {
        List<News_Tags> news_tagsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tags WHERE tag_id = ?");
            preparedStatement.setInt(1, tag_id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                news_tagsList.add(new News_Tags(resultSet.getInt("news_id"), resultSet.getInt("tag_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news_tagsList;
    }

    @Override
    public News_Tags updateNews_Tags(News_Tags oldNews_Tags, News_Tags newNews_Tags) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            if (!(oldNews_Tags.getNews_id() == newNews_Tags.getNews_id())) {
                preparedStatement = connection.prepareStatement("UPDATE news_tags SET news_id = ? WHERE news_id = ? AND tag_id = ?");
                preparedStatement.setInt(1, newNews_Tags.getNews_id());
                preparedStatement.setInt(2, oldNews_Tags.getNews_id());
                preparedStatement.setInt(3, oldNews_Tags.getTag_id());
            } else if (!(oldNews_Tags.getTag_id() == newNews_Tags.getTag_id())) {
                preparedStatement = connection.prepareStatement("UPDATE news_tags SET tag_id = ? WHERE news_id = ? AND tag_id = ?");
                preparedStatement.setInt(1, newNews_Tags.getTag_id());
                preparedStatement.setInt(2, oldNews_Tags.getNews_id());
                preparedStatement.setInt(3, oldNews_Tags.getTag_id());
            } else {
                return oldNews_Tags;
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating news_tags failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return newNews_Tags;
    }


    @Override
    public void deleteNews_Tags(Integer news_id, Integer tag_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news_tags WHERE news_id = ? AND tag_id = ?");
            preparedStatement.setInt(1, news_id);
            preparedStatement.setInt(2, tag_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteNews_TagsByNewsId(Integer news_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news_tags WHERE news_id = ?");
            preparedStatement.setInt(1, news_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
