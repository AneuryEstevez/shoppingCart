package org.example.Users;

import java.util.List;

public interface UserRepository {

    List<User> FindUsers();

    void SaveUser(User user);
}
