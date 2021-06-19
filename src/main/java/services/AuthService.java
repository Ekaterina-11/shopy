package services;

import entity.Role;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public User findByUsername(String username){
        if(userRepository.findByUsername(username).isPresent()){
            return userRepository.findByUsername(username).get();
        }else{
            return null;
        }
    }

    public User findByEmail(String email){
        if(userRepository.findByEmail(email).isPresent()){
            return userRepository.findByEmail(email).get();
        }else{
            return null;
        }
    }

    public void registration(User user){
        user.setImg("https://www.pngitem.com/pimgs/m/22-224287_computer-icons-user-profile-avatar-caller-id-icon.png");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_CUSTOMER);
        userRepository.save(user);
    }

    public void uploadPhoto(User user, String img){
        user.setImg(img);
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
