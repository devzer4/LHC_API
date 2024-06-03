package com.lhc.backend.services;

import com.lhc.backend.config.PasswordService;
import com.lhc.backend.models.UserModel;
import com.lhc.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSevice {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> getUserByLogin(UserModel userModel) {
        return userRepository.findByLogin(userModel.getLogin());
    }

    public UserModel createUser(UserModel user) {
        user.setPassword(passwordService.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public UserModel updateUser(UUID id, UserModel userDetails) {
        UserModel user = userRepository.findById(id).orElseThrow();
        user.setName(userDetails.getName());
        user.setLogin(userDetails.getLogin());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordService.hashPassword(userDetails.getPassword()));
        }
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public boolean authUserDesktop(UserModel userAuth) {
        UserModel user = userRepository.findByLogin(userAuth.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return passwordService.checkPassword(userAuth.getPassword(), user.getPassword()) && user.getRole().equals(UserModel.Role.ADMIN);
    }

    public boolean authUserWeb(UserModel userAuth) {
        UserModel user = userRepository.findByLogin(userAuth.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return passwordService.checkPassword(userAuth.getPassword(), user.getPassword());
    }
}
