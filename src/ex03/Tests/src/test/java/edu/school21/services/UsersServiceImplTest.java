package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UsersServiceImplTest {
    private UsersRepository repository;
    private UsersServiceImpl service;
    private User user;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(UsersRepository.class);
        service = new UsersServiceImpl(repository);
        user = new User(1L, "user1", "user1", false);
        Mockito.when(repository.findByLogin(user.getLogin())).thenReturn(user);
    }

    @Test
    void incorrectLogin() {
        Assertions.assertFalse(service.authenticate(user.getLogin(), "wrong"));
    }

    @Test
    void incorrectPassword() {
        Assertions.assertFalse(service.authenticate(user.getPassword(), "wrong"));
    }

    @Test
    void correctAuthentication() {
        Mockito.doAnswer(update -> {
            user.setIsAuthenticated(true);
            return null;
        }).when(repository).update(user);

        Mockito.doNothing().when(repository).update(user);

        Assertions.assertTrue(service.authenticate(user.getLogin(), user.getPassword()));
        Assertions.assertTrue(user.getIsAuthenticated());
    }

    @Test
    void AlreadyAuthenticated() {
        user.setIsAuthenticated(true);
        Assertions.assertThrows(AlreadyAuthenticatedException.class,
                () -> service.authenticate(user.getLogin(), user.getPassword()));
    }
}