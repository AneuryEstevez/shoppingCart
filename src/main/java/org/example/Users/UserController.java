package org.example.Users;

import io.javalin.http.Context;
import io.javalin.http.Cookie;
import org.hibernate.Session;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    private final UserRepository userRepository;
    private final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    private final AES256TextEncryptor textEncryptor = new AES256TextEncryptor();


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.textEncryptor.setPassword("password");
    }

    public void ShowLoginView(Context ctx) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/Login.ftlh", data);
    }

    public void CreateUser(Context ctx) throws IOException {
        var username = ctx.formParam("username");
        String password = ctx.formParam("password");
        var users = userRepository.FindUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                ctx.redirect("/register");
            } else {
                userRepository.SaveUser(new User(username, passwordEncryptor.encryptPassword(password)));
                user.AddRole(Role.CUSTOMER);
                ValidateUser(ctx);
                break;
            }
        }
    }

    public void ShowRegisterView(Context ctx) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/Register.ftlh", data);
    }

    public void ValidateUser(Context ctx) throws IOException {
        var username = ctx.formParam("username");
        var password = ctx.formParam("password");
        var checked = ctx.formParam("checked");
        var users = userRepository.FindUsers();
        if (users != null) {
            for (User user : users) {
                if (user.getUsername().equals(username) && passwordEncryptor.checkPassword(password, user.getPassword())) {
                    System.out.println(user.getPassword());
                    ctx.sessionAttribute("user", user);
                    System.out.println("IM HERE");
                    if (checked != null) {
//                        textEncryptor.setPassword(myEncryptionPassword);
//                        textEncryptor.encrypt(myText);
                        Cookie loginCookie = new Cookie("loginData", username + ":" + textEncryptor.encrypt(password));
                        loginCookie.setMaxAge(7 * 24 * 60 * 60);
                        loginCookie.setPath("/");
                        ctx.cookie(loginCookie);
                    }
                    ctx.redirect("/products");
                } else {
                    System.out.println("IM HERE 2");
                    ctx.redirect("/login");
                }
            }
        } else {
            ctx.redirect("/register");
        }
    }

    public void Logout(Context ctx) {
        ctx.consumeSessionAttribute("user");
        ctx.removeCookie("loginData");
        ctx.sessionAttribute("amount", 0);
        ctx.redirect("/products");
    }

    public void CookieLogin(Context ctx) {
        String username = null;
        String password = null;
        if (ctx.cookie("loginData") != null) {
            String[] loginParts = ctx.cookie("loginData").split(":");
            if (loginParts.length == 2) {
                username = loginParts[0];
                password = textEncryptor.decrypt(loginParts[1]);
            }
            var users = userRepository.FindUsers();
            for (User user : users) {
//                System.out.println(passwordEncryptor.checkPassword(password, user.getPassword()));
                if (user.getUsername().equals(username) && passwordEncryptor.checkPassword(password, user.getPassword())) {
                    ctx.sessionAttribute("user", user);
                    ctx.redirect("/products");
                }
            }
        } else {
            ctx.redirect("/products");
        }
    }
}
