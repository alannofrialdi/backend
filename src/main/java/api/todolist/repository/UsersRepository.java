package api.todolist.repository;

import api.todolist.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPassword(String password); // Untuk mencari user berdasarkan password

    Optional<Users> findByUsername(String username); // Untuk mencari user berdasarkan username

    Optional<Users> findByEmail(String email); // Jika perlu validasi email
}
