package services;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

    public void registration(User user){
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> currentUser = userRepository.findByUsername(username);
        if(currentUser.isPresent()) {
            return currentUser.get();
        }else{
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }
}
