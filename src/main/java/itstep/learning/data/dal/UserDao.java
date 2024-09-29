package itstep.learning.data.dal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import itstep.learning.data.dto.User;
import itstep.learning.services.hash.HashService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Singleton
public class UserDao {
    private final Connection connection;
    private final Logger logger;
    private final HashService hashService;
    @Inject
    UserDao(Connection connection, Logger logger, @Named("digest")HashService hashService){
        this.connection=connection;
        this.logger = logger;

        this.hashService = hashService;
    }

    public void installTable(){
        String sql="CREATE TABLE IF NOT EXISTS users ("+
                "id           CHAR(36)           PRIMARY KEY DEFAULT(UUID()),"+
                "name         VARCHAR(256)       NOT NULL ,"+
                "email        VARCHAR(256)       UNIQUE    NOT NULL,"+
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

    public boolean signupUser(User user){

        String sql="INSERT INTO users (id, name, email, avatar, deleteDt, passwordHash)" +
                " VALUES (?,?,?,?,NULL,?)";
        try (PreparedStatement prep=connection.prepareStatement(sql)){
            UUID id=user.getId();
            if(id!=null){
                user.setId(UUID.randomUUID());
            }
            prep.setString(1, user.getId().toString());
            prep.setString(2, user.getName());  //отсчет с 1
            prep.setString(3, user.getEmail());
            prep.setString(4, user.getAvatar());
            prep.setString(5, hashService.digest(user.getPasswordHash()));
            prep.executeUpdate();
            return true;
        }
        catch (Exception ex){
            logger.warning(ex.getMessage());
            return false;
        }
    }

    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT passwordHash FROM users WHERE email = ?";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, email);
            try (ResultSet rs = prep.executeQuery()) {
                if (!rs.next()) {
                    // Если пользователь с таким email не найден
                    logger.warning("User with email " + email + " not found.");
                    return false;
                }

                String storedPasswordHash = rs.getString("passwordHash");
                // Проверьте, совпадает ли хэш пароля
                if (hashService.matches(password, storedPasswordHash)) {
                    return true;  // Аутентификация успешна
                } else {
                    // Неправильный пароль
                    logger.warning("Invalid password for user with email " + email);
                    return false;
                }
            }
        } catch (SQLException ex) {
            logger.warning("Error during authentication: " + ex.getMessage());
        }
        return false;  // Если произошла ошибка
    }

    public boolean updateUser(String userId, String name, String email) {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { // Подготовленный запрос

            pstmt.setString(1, name);  // Устанавливаем имя
            pstmt.setString(2, email); // Устанавливаем email
            pstmt.setString(3, userId); // Устанавливаем ID пользователя

            int affectedRows = pstmt.executeUpdate(); // Выполняем обновление
            return affectedRows > 0; // Возвращаем true, если строки обновлены
        } catch (SQLException e) {
            e.printStackTrace(); // Логируем ошибку
            return false; // Возвращаем false в случае ошибки
        }
    }

    public boolean deleteUser(String userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { // Подготовленный запрос

            pstmt.setString(1, userId); // Устанавливаем ID пользователя

            int affectedRows = pstmt.executeUpdate(); // Выполняем удаление
            return affectedRows > 0; // Возвращаем true, если строки удалены
        } catch (SQLException e) {
            e.printStackTrace(); // Логируем ошибку
            return false; // Возвращаем false в случае ошибки
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, email FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
