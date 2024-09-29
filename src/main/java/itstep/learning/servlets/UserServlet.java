package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.dal.UserDao;
import itstep.learning.data.dto.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class UserServlet extends HttpServlet {
    private final UserDao userDao;
    @Inject
    public UserServlet(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name=req.getParameter("name");
//        String email=req.getParameter("email");
//        String pass=req.getParameter("password");
        //поток вычитки тела (body), прямой доступ к телу запроса
        //req.getInputStream();
        //req.getReader(); -- для чтения как String
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
        // Читаем тело запроса как JSON
        JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);

        // Извлекаем action из JSON
        String action = body.has("action") && !body.get("action").isJsonNull() ? body.get("action").getAsString() : null;


        System.out.println(action);
        if("registration".equals(action)){
            //если в json есть поле со значением null,
            //оно не сериализируется, исключается из передачи данных

//        JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
//        String name = body.has("name") && !body.get("name").isJsonNull() ? body.get("name").getAsString() : null;
//        String email = body.has("email") && !body.get("email").isJsonNull() ? body.get("email").getAsString() : null;
//        String password = body.has("password") && !body.get("password").isJsonNull() ? body.get("password").getAsString() : null;
//
//        JsonObject respBody;
            //НЕ ЗАХОДИТ В IF, потому что пустая строка не считается null в JAVA
//        if(name==null||email==null||password==null){
//            respBody=getErrorBody(req, "Bad request: 'name' or 'password' parameter(s) missed or empty");
//        }

            // Читаем тело запроса
//            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
//        System.out.println("Incoming JSON body: " + body);

// Проверяем поля на наличие и пустоту
            String name = body.has("name") && !body.get("name").isJsonNull() ? body.get("name").getAsString().trim() : null;
            String email = body.has("email") && !body.get("email").isJsonNull() ? body.get("email").getAsString().trim() : null;
            String password = body.has("password") && !body.get("password").isJsonNull() ? body.get("password").getAsString().trim() : null;

//        // Логируем значения полей
//        System.out.println("Name: " + name);
//        System.out.println("Email: " + email);
//        System.out.println("Password: " + password);

            JsonObject respBody;
            if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
                System.out.println("Validation failed: Missing or empty 'name', 'email', or 'password'");
                respBody = getErrorBody(req, "Bad request: 'name', 'email', or 'password' parameter(s) missed or empty");
            }
            else {
                userDao.installTable();
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(password);
                if(userDao.signupUser(user)){
                    respBody=getSuccessBody(req, gson.toJsonTree(user));
                }
                else if (!userDao.signupUser(user)) {
                    respBody = getErrorBody(req, "Email already exists or other issue occurred");
                }
                else{
                    respBody=getErrorBody(req,"See server logs for more details");
                }

            }
            resp.getWriter().print(gson.toJson(respBody));
        }
        if ("updateUser".equals(action)) {
            String userId = body.get("userId").getAsString();
            String name = body.get("name").getAsString();
            String email = body.get("email").getAsString();

            // Вызов метода обновления в вашем UserDao
            boolean success = userDao.updateUser(userId, name, email);
            JsonObject respBody;

            if (success) {
                respBody = getSuccessBody(req, null);
            } else {
                respBody = getErrorBody(req, "Не удалось обновить пользователя");
            }

            resp.getWriter().print(gson.toJson(respBody));
        }

        if ("deleteUser".equals(action)) {
            String userId = body.get("userId").getAsString();

            // Вызов метода удаления в вашем UserDao
            boolean success = userDao.deleteUser(userId);
            JsonObject respBody;

            if (success) {
                respBody = getSuccessBody(req, null);
            } else {
                respBody = getErrorBody(req, "Не удалось удалить пользователя");
            }

            resp.getWriter().print(gson.toJson(respBody));
        }
    }



    //передаем request, чтобы посчитать ContextPath, и текст
    private JsonObject getErrorBody(HttpServletRequest req, String text){
        JsonObject respBody=new JsonObject();
        JsonObject status=new JsonObject();
        status.addProperty("isOk", false);
        status.addProperty("code", -1);
        status.addProperty("httpCode", 400);
        status.addProperty("phrase", text);
        respBody.add("status", status);

        JsonObject meta = new JsonObject();
        meta.addProperty("service", "user");
        meta.addProperty("action", "Sing Up");
        meta.addProperty("location", req.getContextPath()+"/user");
        meta.addProperty("serverTime", System.currentTimeMillis());
        meta.addProperty("count", 0);
        meta.addProperty("locale", "uk-UA");
        respBody.add("meta", meta);

        JsonObject restBody=new JsonObject();
        respBody.add("body", restBody);

        return respBody;
    }

    private JsonObject getSuccessBody(HttpServletRequest req, JsonElement body){
        JsonObject respBody=new JsonObject();
        JsonObject status=new JsonObject();
        status.addProperty("isOk", true);
        status.addProperty("code", 0);
        status.addProperty("httpCode", 200);
        status.addProperty("phrase", "Ok");
        respBody.add("status", status);

        JsonObject meta = new JsonObject();
        meta.addProperty("service", "user");
        meta.addProperty("action", "Sing Up");
        meta.addProperty("location", req.getContextPath()+"/user");
        meta.addProperty("serverTime", System.currentTimeMillis());
        meta.addProperty("count", 0);
        meta.addProperty("locale", "uk-UA");
        respBody.add("meta", meta);

        JsonObject restBody=new JsonObject();
        respBody.add("body", body);

        return respBody;
    }
}

