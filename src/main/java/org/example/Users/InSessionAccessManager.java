package org.example.Users;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class InSessionAccessManager implements AccessManager {

    private final UserRepository userRepository;

    public InSessionAccessManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<? extends RouteRole> allowedRoles) throws Exception {
        if (allowedRoles.isEmpty()) {
            handler.handle(ctx);
            return;
        }

        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.redirect("/register");
        }

        var userHasEnoughPermissions = false;
        for (var allowedRole : allowedRoles) {
            for (var userRole : user.getRoles()) {
                if (allowedRole.equals(userRole)) {
                    userHasEnoughPermissions = true;
                    break;
                }
            }
        }

        if (userHasEnoughPermissions) {
            handler.handle(ctx);
        } else {
            ctx.redirect("/register");
        }
    }
}
