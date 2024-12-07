package api.todolist.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import api.todolist.model.Users;
import api.todolist.repository.UsersRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOptional = usersRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Users user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, // isEnabled
                true, // isAccountNonExpired
                true, // isCredentialsNonExpired
                true, // isAccountNonLocked
                new ArrayList<>() // authorities
        );
    }
}
