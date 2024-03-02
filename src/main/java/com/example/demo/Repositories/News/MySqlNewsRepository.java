package com.example.demo.Repositories.News;

import com.example.demo.Entities.News;
import com.example.demo.Repositories.MySqlAbstractRepository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @Override
    public News addNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            //timestamp se automatski generise u bazi
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO news (title, text, visit_count, author_id, category_id, creation_time) " +
                            "VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getText());
            preparedStatement.setInt(3, news.getVisit_count());
            preparedStatement.setInt(4, news.getAuthor_id());
            preparedStatement.setInt(5, news.getCategory_id());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                //iz resultseta uzimam id i trazim vest pod tim id da bih iz nje uzela timestamp
                int id = resultSet.getInt(1);
                preparedStatement.close();
                preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    news.setId(resultSet.getInt("id"));
                    Timestamp ts = resultSet.getTimestamp("creation_time");
                    news.setCreation_time(sdf.format(ts));
                    news.setVisit_count(resultSet.getInt("visit_count"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }

        return news;
    }


    @Override
    public List<News> allNews(Integer page) {
        List<News> newsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startIndex = (page - 1) * 10;

            String query = "SELECT * FROM news ORDER BY creation_time DESC LIMIT ?, 11";
            statement = connection.prepareStatement(query);
            statement = connection.prepareStatement(query);
            statement.setInt(1, startIndex);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                News news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("category_id")
                );
                Timestamp ts = resultSet.getTimestamp("creation_time");
                news.setCreation_time(sdf.format(ts));
                news.setVisit_count(resultSet.getInt("visit_count"));
                newsList.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public News findNews(Integer id) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int newsId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                Timestamp ts = resultSet.getTimestamp("creation_time");
                String creation_time = sdf.format(ts);
                int visit_count = resultSet.getInt("visit_count");
                int author_id = resultSet.getInt("author_id");
                int category_id = resultSet.getInt("category_id");
                news = new News(newsId, title, text, author_id, category_id);
                news.setCreation_time(creation_time);
                news.setVisit_count(visit_count);
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

        return news;
    }

    @Override
    public List<News> searchNews(String search, Integer page) {
        List<News> searchResults = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startIndex = (page - 1) * 10;

            String query = "SELECT * FROM news WHERE title LIKE ? OR text LIKE ? ORDER BY creation_time DESC LIMIT ?, 11";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + search + "%");
            statement.setString(2, "%" + search + "%");
            statement.setInt(3, startIndex);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                News news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("category_id")
                );
                Timestamp ts = resultSet.getTimestamp("creation_time");
                news.setCreation_time(sdf.format(ts));
                news.setVisit_count(resultSet.getInt("visit_count"));
                searchResults.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return searchResults;
    }

    @Override
    public List<News> findNewsByCategoryId(Integer id, Integer page) {
        List<News> newsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startIndex = (page - 1) * 10;

            String query = "SELECT * FROM news WHERE category_id = ? ORDER BY creation_time DESC LIMIT ?, 11";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, startIndex);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                News news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("category_id")
                );
                Timestamp ts = resultSet.getTimestamp("creation_time");
                news.setCreation_time(sdf.format(ts));
                news.setVisit_count(resultSet.getInt("visit_count"));
                newsList.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> getPopularNews() {
        List<News> popularNewsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -30);
            Timestamp thirtyDaysAgo = new Timestamp(calendar.getTimeInMillis());

            String query = "SELECT * FROM news WHERE creation_time >= ? ORDER BY visit_count DESC LIMIT 10";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, thirtyDaysAgo);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                News news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("category_id")
                );
                Timestamp ts = resultSet.getTimestamp("creation_time");
                news.setCreation_time(sdf.format(ts));
                news.setVisit_count(resultSet.getInt("visit_count"));
                popularNewsList.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return popularNewsList;
    }

    @Override
    public News updateNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE news SET title = ?, text = ?, visit_count = ?, author_id = ?, category_id = ? WHERE id = ?"
            );
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getText());
            preparedStatement.setInt(3, news.getVisit_count());
            preparedStatement.setInt(4, news.getAuthor_id());
            preparedStatement.setInt(5, news.getCategory_id());
            preparedStatement.setInt(6, news.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating news failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public void updateVisitCount(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE news SET visit_count = visit_count + 1 WHERE id = ?"
            );
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE news_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM news_tags WHERE news_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

}
