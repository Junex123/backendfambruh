package coms.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import coms.model.user.*;
import coms.repository.*;



@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register a new user
    public User createUser(User user, Set<UserRole> userRoles) {
        // Check if the user already exists
        User existingUser = this.userRepo.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Username already exists!");
        }

        // Save user roles
        for (UserRole userRole : userRoles) {
            this.roleRepo.save(userRole.getRole());
        }

        // Assign user roles
        user.getUserRoles().addAll(userRoles);

        // Encrypt password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // Save the user
        return this.userRepo.save(user);
    }

    // Retrieve user by username
    public User getByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    // Delete user by ID
    public void deleteUserById(Long userId) {
        this.userRepo.deleteById(userId);
    }




}
