package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthService authService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login","/registration","/product/**","/activationProfile/**","/cartConfirm").permitAll()
                    .antMatchers("/profile","/uploadAvatarPhoto").hasAuthority("ROLE_CUSTOMER")
                    .antMatchers("/confirmOrder","/administrator","/admProduct","/admDelProduct","/admCustomer","/uploadAvatarPhoto").hasAuthority("ROLE_ADMIN")
                    .antMatchers("/","/*.css", "/*.js", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/")
                    .permitAll()
                .and()
                    .logout().logoutSuccessUrl("/")
                    .permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(bCryptPasswordEncoder());
    }
}
