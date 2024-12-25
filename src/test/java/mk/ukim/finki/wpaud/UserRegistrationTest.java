package mk.ukim.finki.wpaud;

import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.enumerations.Role;
import mk.ukim.finki.wpaud.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpaud.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wpaud.repository.jpa.UserRepository;
import mk.ukim.finki.wpaud.service.impl.UserServiceImpl;
import mk.ukim.finki.wpaud.model.exceptions.InvalidArgumentsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "password", "name", "surname", Role.ROLE_USER);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        this.service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder));
    }

    @Test
    public void testSuccessRegister() {
        User user = this.service.register("username", "password", "password", "name", "surname", Role.ROLE_USER);
        Mockito.verify(this.service).register("username", "password", "password", "name", "surname", Role.ROLE_USER);

        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("name does not match", "name", user.getName());
        Assert.assertEquals("username does not match", "username", user.getUsername());
        Assert.assertEquals("password does not match", "password", user.getPassword());
        Assert.assertEquals("surname does not match", "surname", user.getSurname());
        Assert.assertEquals("role does not match", Role.ROLE_USER, user.getRole());
    }

    @Test
    public void testNullUsername() {
        String username = null;
        Assert.assertThrows("InvalidArgumentException expected", InvalidArgumentsException.class, () -> this.service.register(username, "password", "password", "name", "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name", "surname", Role.ROLE_USER);
    }
    @Test
    public void testEmptyUsername(){
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected", InvalidArgumentsException.class, () -> this.service.register(username, "password", "password", "name", "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name", "surname", Role.ROLE_USER);
    }
    @Test
    public void testEmptyPassword(){
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected", InvalidArgumentsException.class, () -> this.service.register(username, password, "password", "name", "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password", "name", "surname", Role.ROLE_USER);
    }
    @Test
    public void testNullPassword(){
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidArgumentException expected", InvalidArgumentsException.class, () -> this.service.register(username, password, "password", "name", "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password", "name", "surname", Role.ROLE_USER);
    }
    @Test
    public void testPasswordsDoNotMatch() {
        String password = "password";
        String passwordConfirm = "otherPassword";

        Assert.assertThrows("PasswordsDoNotMatchException expected", PasswordsDoNotMatchException.class, () ->
                this.service.register("username", password, passwordConfirm, "name", "surname", Role.ROLE_USER));

        Mockito.verify(this.service).register("username", password, passwordConfirm, "name", "surname", Role.ROLE_USER);
    }

    @Test
    public void testUsernameAlreadyExists() {
        User existingUser = new User("username", "password", "name", "surname", Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername("username")).thenReturn(Optional.of(existingUser));

        Assert.assertThrows("UsernameAlreadyExistsException expected", UsernameAlreadyExistsException.class, () ->
            this.service.register("username", "password", "password", "name", "surname", Role.ROLE_USER));

        Mockito.verify(this.service).register("username", "password", "password", "name", "surname", Role.ROLE_USER);
    }

}
