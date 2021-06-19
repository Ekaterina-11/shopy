package services;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.UserRepository;

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
        UserDetails u = userRepository.findByUsername(username).get();
        if(u == null) {
            throw new UsernameNotFoundException("Пользователя не существует");
        } else {
            return u;
        }
    }
}
