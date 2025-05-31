package JeInOne.WeGong.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/musician/signup",
                                "/api/musician/login",
                                "/api/musician/mypage",
                                "/api/musician/mypage/**",
                                "/api/listener/signup",
                                "/api/listener/login",
                                "/api/business-owner",
                                "/api/business-owner/**",
                                "/api/venues",
                                "/api/venues/**",
                                "/api/venues/sort",
                                "/api/performance",
                                "/api/performance/**",
                                "/api/events",
                                "/api/events/**",
                                "/h2-console/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")
//                )

                .headers(headers -> headers
                        .defaultsDisabled()
                        .cacheControl(Customizer.withDefaults())
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .formLogin(form -> form.disable())
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
