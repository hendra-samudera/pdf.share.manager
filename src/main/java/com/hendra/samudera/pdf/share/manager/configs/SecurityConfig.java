package com.hendra.samudera.pdf.share.manager.configs;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**").permitAll() // Izinkan registrasi, login dan aset statis
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(form -> form
			    .loginPage("/login")
			    .defaultSuccessUrl("/home", true) // Arahkan ke home setelah login berhasil
			    .permitAll()
            )
			.logout(logout -> logout
				.logoutSuccessUrl("/login")
				.permitAll()
			);

		return http.build();
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
