package ru.luxoft.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.luxoft.client.property.QueueProperty;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(securedEnabled = true)//secure methods are having ann @Secured
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final QueueProperty queueProperty;


    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, QueueProperty queueProperty) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.queueProperty = queueProperty;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration/", "/login").permitAll()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**", "/**/edit", "/**/delete","/crud/**").hasAuthority("ROLE_ADMIN")//Поиск один в один
                .antMatchers("/any/**").hasRole("ADMIN")//Spring сам подставит преффикс ROLE_
                .anyRequest().permitAll()
                .and()
                .formLogin().successHandler(authenticationSuccessHandler)
                .usernameParameter("login")
                .loginPage("/login")
                .loginProcessingUrl("/login");
        http.csrf().disable();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    @Profile("dev")
    public UserDetailsService userDetailsService(JmsTemplate jmsTemplate) {
        return login -> {
            jmsTemplate.convertAndSend(queueProperty.getAuthentication(), login);
            return (ru.luxoft.client.model.User) jmsTemplate.receiveAndConvert(queueProperty.getAuthenticationAnswer());
        };
    }

    @Bean
    @Profile("test")
    public UserDetailsService inMemory(PasswordEncoder encoder) {
        UserDetails user = User.builder().
                username("user")
                .password(encoder.encode("pass"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder().
                username("admin")
                .password(encoder.encode("pass"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(11);
    }

    public UserDetailsService getUserDetailsService(UserDetailsService userDetailsService) {
        return userDetailsService;
    }
}
