package com.trilogyed.adminapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {


    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);

    }

    public void configure(HttpSecurity httpsecurity) throws Exception {

        httpsecurity.httpBasic();

        httpsecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.DELETE, "/*").hasAuthority("ROLE_ADMIN")

                .mvcMatchers(HttpMethod.POST, "/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")

                .mvcMatchers(HttpMethod.PUT, "/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEAMLEAD")

                .mvcMatchers(HttpMethod.GET, "/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEAMLEAD", "ROLE_EMPLOYEE")

                .mvcMatchers(HttpMethod.POST, "/customers/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEAMLEAD")
                .mvcMatchers(HttpMethod.PUT, "/inventory/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEAMLEAD", "ROLE_EMPLOYEE");


        // Needed to configure CSRF protection - Spring has CSRF protection enabled by default
        httpsecurity
                //Enables logout
                .logout()
                .clearAuthentication(true)
                //specifies the url to be called when requesting to be logged out
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //The url the application will redirect to when logout is complete
                .logoutSuccessUrl("/allDone")
                //specifies the cookies that should be deleted
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                //Explains whether or not the HTTP session should be invalidated on logout
                .invalidateHttpSession(true);

        httpsecurity
                //adds csrf support
                .csrf()
                /* csr is an API - cookiesCsrfTokenRepository is
                  an implementation CsrfTokenRepository that persists the CSRF token
                  in a cookie named "XSRF-TOKEN" and reads from the header
                  "X-XSRF-TOKEN" following the conventions of AngularJS.
                  When using with AngularJS be sure to use withHttpOnlyFalse().
               */
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

                /*
                Spring has CSRF protection enabled by default. However, this mechanism uses
                custom Http headers to exchange tokens. This isn't ideal for frameworks like Angular
                and applications like postman.

                - This project has been configured to use cookies to exchange CSRF tokens
               */
    }
}
