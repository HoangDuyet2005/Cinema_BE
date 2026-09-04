package com.example.goldenticketnew.config;


import com.example.goldenticketnew.security.CustomUserDetailsService;
import com.example.goldenticketnew.security.JwtAuthenticationEntryPoint;
import com.example.goldenticketnew.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/",
                "/*",
                "/webjars/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/api-docs/**",
                "/swagger-resources/**",
                "/api/auth/**"
            ).permitAll()
            .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
            .permitAll()
            // Chỉ các API ĐỌC (GET) mang tính công khai (danh mục phim, lịch chiếu, rạp, ghế...)
            // mới được permitAll. Mọi thao tác ghi (POST/PUT/DELETE) trên các resource này
            // đều rơi xuống .anyRequest().authenticated() bên dưới và bắt buộc phải có JWT hợp lệ.
            // (Trước đây permitAll không giới hạn method khiến toàn bộ POST/PUT/DELETE của các
            // API này bị public hoàn toàn - xem báo cáo audit để biết chi tiết lỗ hổng đã vá.)
            .antMatchers(HttpMethod.GET,
                "/api/concessions/**", "/api/theaters/**", "/api/article/**", "/api/interaction/**",
                "/api/movies/**", "/api/schedule/**", "/api/rooms/**", "/api/seats/**", "/api/branches/**",
                "/api/user/**"
            )
            .permitAll()
            .antMatchers(HttpMethod.GET, "/api/bills/check-ticket")
            .permitAll()
            // /api/tickets/** và mọi method khác (POST/PUT/DELETE) trên các resource ở trên
            // (bao gồm /api/bills/**, /api/user/**...) bắt buộc phải authenticated - các endpoint
            // nội bộ/admin (dashboard, xoá, cập nhật...) tự bảo vệ thêm bằng @PreAuthorize ở
            // controller/service tương ứng.
            .anyRequest()
            .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}