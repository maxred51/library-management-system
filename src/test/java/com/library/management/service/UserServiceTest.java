package com.library.management.service;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setRole("USER");
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void saveUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User saved = userService.saveUser(testUser);
        assertEquals("testuser", saved.getUsername());
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        User result = userService.findByUsername("testuser");
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void findByUsername_NotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        User result = userService.findByUsername("unknown");
        assertNull(result);
    }
}