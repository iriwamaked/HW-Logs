package itstep.learning.data.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private String avatar;
    private Date deleteDt;

    public User() {
        setId(UUID.randomUUID());
    }

    public User(ResultSet rs) throws RuntimeException {
        try{
            setId(UUID.fromString(rs.getString("id")));
            setName(rs.getString("name"));
            setEmail(rs.getString("email"));
            setPasswordHash(rs.getString("passwordHash"));
            setAvatar(rs.getString("avatar"));
            setDeleteDt(new Date (rs.getLong("deleteDt")));
        }
        catch (Exception ex){
            //просто прокидываем его в формализме Runtime
            //может быть UUID неправильно распарсенное, если неправильный string,
            //может быть неправильное название поля, тип и т.д., поэтому все виды исключения
            //превращаются в Runtime (если мы не смогли построить объект)
            throw new RuntimeException(ex);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDeleteDt() {
        return deleteDt;
    }

    public void setDeleteDt(Date deleteDt) {
        this.deleteDt = deleteDt;
    }
}
