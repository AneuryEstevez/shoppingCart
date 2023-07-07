package org.example.Users;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {

    ADMIN,

    CUSTOMER
}
