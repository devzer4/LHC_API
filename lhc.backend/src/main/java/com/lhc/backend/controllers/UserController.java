package com.lhc.backend.controllers;

import com.lhc.backend.models.UserModel;
import com.lhc.backend.services.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserSevice userService;

    @PostMapping("/auth/desktop")
    public ResponseEntity<Optional<UserModel>> authUserDesktop(@RequestBody UserModel user) {
        if(userService.authUserDesktop(user)){
            Optional<UserModel> userFound = userService.getUserByLogin(user);
            return ResponseEntity.ok(userFound);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/auth/web")
    public ResponseEntity<Optional<UserModel>> authUserWeb(@RequestBody UserModel user) {
        if(userService.authUserWeb(user)){
            Optional<UserModel> userFound = userService.getUserByLogin(user);
            return ResponseEntity.ok(userFound);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel userSave = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable UUID id) {
        Optional<UserModel> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable UUID id, @RequestBody UserModel userDetails) {
        UserModel updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
