package com.projectweb.ProjectWeb.configuration;

import com.projectweb.ProjectWeb.model.User_Entity;
import com.projectweb.ProjectWeb.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	private final UserService userService;

	public SecurityConfiguration(UserService userService) {
		this.userService = userService;
	}

	@Bean
	SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/login", "/register", "/newuserregister", "/test", "/test2").permitAll() // Cho phép truy cập các URL không cần xác thực
						.requestMatchers("/**").hasRole("USER") // Các URL khác yêu cầu quyền USER
				)
				.formLogin(form -> form
						.loginPage("/login") // Trang đăng nhập
						.loginProcessingUrl("/userloginvalidate") // URL xử lý đăng nhập
						.defaultSuccessUrl("/") // Chuyển hướng về trang chủ khi đăng nhập thành công
						.failureUrl("/login?error=true") // Chuyển hướng khi đăng nhập thất bại
				)
				.logout(logout -> logout
						.logoutUrl("/logout") // URL xử lý đăng xuất
						.logoutSuccessUrl("/login") // Chuyển hướng về trang đăng nhập sau khi đăng xuất
						.deleteCookies("JSESSIONID") // Xóa cookie phiên
				)
				.exceptionHandling(exception -> exception
						.accessDeniedPage("/403") // Trang khi không đủ quyền truy cập
				);
		http.csrf(csrf -> csrf.disable()); // Vô hiệu hóa CSRF nếu không cần thiết
		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			User_Entity user = userService.getUsersByEmail(username);
			if (user == null) {
				throw new UsernameNotFoundException("User with username " + username + " not found.");
			}
			String role = "USER"; // Chỉ sử dụng quyền USER cho người dùng

			return org.springframework.security.core.userdetails.User
					.withUsername(username)
					.password(user.getPASSWORD_ACC()) // Đảm bảo mật khẩu đã mã hóa bằng BCrypt
					.roles(role)
					.build();
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
	}
}
