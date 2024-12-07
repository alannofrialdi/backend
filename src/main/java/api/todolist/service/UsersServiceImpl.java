package api.todolist.service;

import api.todolist.model.Users;
import api.todolist.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Users createUser(Users user) {
        // Enkripsi password sebelum disimpan
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
