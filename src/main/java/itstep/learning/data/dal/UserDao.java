package itstep.learning.data.dal;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
public class UserDao {
    private final Connection connection;
    @Inject
    UserDao(Connection connection){
        this.connection=connection;
    }

    public void installTable(){
        String sql="CREATE TABLE IF NOT EXISTS users ("+
                "id           CHAR(36)           PRIMARY KEY DEFAULT(UUID()),"+
                "name         VARCHAR(256)       NOT NULL ,"+
                "email        VARCHAR(256)       NOT NULL,"+
                "avatar       VARCHAR(256)       NULL," +
                "deleteDt     TIMESTAMP          NULL," +
                "passwordHash CHAR(32)           NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

        try (Statement stmt = connection.createStatement()){
            //попав в execute он начинает подсвечивать, что это sql-команда,
            //что позволяет дополнительно проверить, что в ней нет ошибок, чтобы оно валидировало синтаксис
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }

    }

    public void deleteUserTable() {
        String sql = "DROP TABLE IF EXISTS users"; // Удаляем таблицу, если она существует

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
