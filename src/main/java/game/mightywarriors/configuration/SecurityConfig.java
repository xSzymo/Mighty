package game.mightywarriors.configuration;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
    }

    //.csrf() is optional, enabled by default, if using WebSecurityConfigurerAdapter constructor
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().permitAll()
                .and().csrf().disable()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password").successForwardUrl("/hello")

                .and()
                .logout().logoutSuccessUrl("/login?logout");

    }

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private DataSource dataSource;
//
//    private String usersQuery = "select login, password, active from user where login=?";
//    private String rolesQuery = "select u.email, r.role from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.email=?\n";
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("admin");
////        auth.
////                jdbcAuthentication()
////                .usersByUsernameQuery(usersQuery)
//////                .authoritiesByUsernameQuery(rolesQuery)
////                .dataSource(dataSource)
////                .passwordEncoder(bCryptPasswordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/admin/**").access("hasRole('ADMIN')")
//                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//                .and().formLogin().loginPage("/login")
//                .usernameParameter("user").passwordParameter("password")
//                .and().csrf()
//                .and().exceptionHandling().accessDeniedPage("/Access_Denied");
//
//
////        http.
////                authorizeRequests()
////                .antMatchers("/").permitAll()
////                .antMatchers("/login").permitAll()
////                .antMatchers("/registration").permitAll()
////                .antMatchers("/user").permitAll()
////                .antMatchers("/user/{id}").permitAll()
////                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
////                .authenticated().and().csrf().disable()
////
////                .formLogin().loginPage("/logincustom").loginProcessingUrl("/doLogin")
////                .usernameParameter("ssoId").passwordParameter("password");
////
//////                .logout()
//////                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//////                .logoutSuccessUrl("/").and().exceptionHandling()
//////                .accessDeniedPage("/access-denied");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }
}