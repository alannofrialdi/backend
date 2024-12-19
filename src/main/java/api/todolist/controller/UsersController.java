package api.todolist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import api.todolist.model.Users;
import api.todolist.service.UsersService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UsersService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UsersService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // GET all users
    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<Users> users = userService.getAllUsers();

        if (users.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No users found");
            return ResponseEntity.status(404).body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Users retrieved successfully");
        response.put("status", "ok");
        response.put("code", 200);
        response.put("content", users);
        return ResponseEntity.ok(response);
    }

    // GET user by param (email or username)
    @GetMapping("/find")
    public ResponseEntity<?> getUserByParam(@RequestParam String param) {

        LOGGER.info("Searching for user with parameter: {}", param);

        Object user;

        if (param.contains("@")) {
            LOGGER.info("Parameter detected as email.");
            user = userService.getUserByEmail(param).orElse(null);
        } else {
            LOGGER.info("Parameter detected as username.");
            user = userService.getUserByUsername(param).orElse(null);
        }

        if (user == null) {
            LOGGER.warn("User with parameter '{}' not found.", param);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", 404,
                            "message", "User with parameter '" + param + "' not found."));
        }

        return ResponseEntity.ok(Map.of(
                "content", user,
                "status", 200,
                "message", "User found successfully."));
    }

    // POST create user
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody Users user, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        // Cek apakah username sudah ada di database
        Optional<Users> existingUser = userService.getUserByUsername(user.getUsername());
        Optional<Users> existingUserByEmail = userService.getUserByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            response.put("status", "validation");
            response.put("code", 200);
            response.put("message", "Username already exists");
            return ResponseEntity.ok(response);
        }

        if (existingUserByEmail.isPresent()) {
            response.put("status", "validation");
            response.put("code", 200);
            response.put("message", "Email already exists");
            return ResponseEntity.ok(response);
        }

        // Cek apakah ada error pada validasi input
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();

            // Gabungkan semua error ke dalam satu string, menggunakan "&" sebagai pemisah
            result.getFieldErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append(" & ");
            });

            // Hapus "&" terakhir yang tidak diperlukan
            if (errorMessages.length() > 0) {
                errorMessages.setLength(errorMessages.length() - 3);
            }

            response.put("status", "validation");
            response.put("code", 200);
            response.put("message", errorMessages.toString());
            return ResponseEntity.ok(response);
        }

        // Simpan pengguna ke database
        userService.createUser(user);
        response.put("status", "ok");
        response.put("code", 200);
        response.put("message", "User created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> handleLogin(@RequestBody Users user) {
        LOGGER.info("USER INFO {}", user.toString());
        // Cari user berdasarkan username/email
        Optional<Users> existingUser = userService.getUserByUsername(user.getUsername());

        Map<String, Object> response = new HashMap<>();
        if (existingUser.isPresent()) {
            Users foundUser = existingUser.get();

            // Cocokkan password
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                response.put("status", "ok");
                response.put("code", 200);
                response.put("message", "Welcome " + user.getUsername());
            } else {
                response.put("status", "error");
                response.put("code", 200);
                response.put("message", "Invalid credentials");
            }
        } else {
            response.put("status", "error");
            response.put("code", 200);
            response.put("message", "User not found");
        }

        return ResponseEntity.status((int) response.get("code")).body(response);
    }

}
