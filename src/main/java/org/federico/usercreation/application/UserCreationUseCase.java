package org.federico.usercreation.application;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserCreationUseCase {
    public User createUser(User user) {
        return user;
    }
}
