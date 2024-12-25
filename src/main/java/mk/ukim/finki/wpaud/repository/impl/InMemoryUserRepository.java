package mk.ukim.finki.wpaud.repository.impl;

import mk.ukim.finki.wpaud.bootstrap.DataHolder;
import mk.ukim.finki.wpaud.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryUserRepository {
    public Optional<User> findByUsername(String username){
        return DataHolder.users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public Optional<User> findByUsernameAndPassword(String username, String password){
        return DataHolder.users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst();
    }
    public User saveOrUpdate(User user){
        DataHolder.users.removeIf(user1 -> user1.getUsername().equals(user.getUsername()));
        DataHolder.users.add(user);
        return user;
    }
    public void delete(String username){
        DataHolder.users.removeIf(user -> user.getUsername().equals(username));
    }
}
