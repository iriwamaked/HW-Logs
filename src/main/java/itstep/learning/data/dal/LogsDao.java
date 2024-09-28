package itstep.learning.data.dal;

import com.google.inject.Inject;
import itstep.learning.data.dto.LogsEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogsDao {
    private final Connection connection;

    @Inject
    public LogsDao(Connection connection){
        this.connection=connection;
        createLogTable();
    }

    private void createLogTable(){
        String query="CREATE TABLE IF NOT EXISTS logi(" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "url VARCHAR(255)," +
                "date_time TIMESTAMP)";
        try(PreparedStatement stmt=connection.prepareStatement(query)){
            stmt.executeUpdate();
        }
        catch (SQLException ex){
            throw new RuntimeException("Error creating log table", ex);
        }
    }

    public void logRequest(LogsEntity logEntity){
        String insertSql="INSERT INTO logi (url, date_time) VALUES(?,?)";
        try(PreparedStatement insertStmt=connection.prepareStatement(insertSql)){
            insertStmt.setString(1,logEntity.getUrl());
            insertStmt.setTimestamp(2, Timestamp.valueOf(logEntity.getDateTime()));
            insertStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw new RuntimeException("Error inserting log entity", ex);
        }
    }
}
