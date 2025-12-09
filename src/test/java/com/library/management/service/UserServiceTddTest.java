package com.library.management.service;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 1. Stworzono najpierw test bez funkcji ChangePassword (RED)
// 2. Dodano metodę ChangePassword do UserService (GREEN)
// 3. Dokonano refaktoryzacji kodu (REFACTOR)

@ExtendWith(MockitoExtension.class)
class UserServiceTddTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void changePassword_ShouldChangePassword_WhenOldPasswordMatches() {
        User user = new User();
        user.setId(1L);
        user.setPassword("old123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.changePassword(1L, "old123", "new12345");

        assertEquals("new12345", result.getPassword());
    }

    @Test
    void changePassword_ShouldThrowException_WhenOldPasswordIncorrect() {
        User user = new User();
        user.setId(1L);
        user.setPassword("old123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,
                () -> userService.changePassword(1L, "wrong", "new12345"));
    }

    @Test
    void changePassword_ShouldThrowException_WhenNewPasswordTooShort() {
        User user = new User();
        user.setId(1L);
        user.setPassword("old123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class,
                () -> userService.changePassword(1L, "old123", "123"));
    }

    @Test
    void changePassword_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> userService.changePassword(1L, "old123", "new12345"));
    }
}

