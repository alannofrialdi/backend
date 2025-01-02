package api.todolist.service;

import api.todolist.model.Users;
import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> getAllUsers();

    Users getUserById(Long id);

    Users createUser(Users user);

    Optional<Users> getUserByEmail(String email); // Cari user berdasarkan email

    Optional<Users> getUserByUsername(String username); // Digunakan untuk autentikasi jika memakai JWT

    Optional<Users> getUserByPassword(String password); // Digunakan untuk autentikasi jika memakai JWT

    Users updateUser(Users user);
}