package nanucloud.nanuid.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import nanucloud.nanuid.global.error.ExceptionFilter
import nanucloud.nanuid.global.security.jwt.JwtFilter
import nanucloud.nanuid.global.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider, // JwtProvider 주입
    private val objectMapper: ObjectMapper // ObjectMapper 주입
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/auth/register", "/auth/login").permitAll()
                    .requestMatchers("/auth/update", "/auth/delete").authenticated()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(ExceptionFilter(objectMapper), JwtFilter::class.java)
        return http.build()
    }
}