package com.iig.gcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Value( "${ad.domain.name}" )
	private String domain;
	
	@Value( "${ad.lds.url}" )
	private String ldapurl;
	
	
	@Autowired 
	private UserDetailsContextMapper userDetailsContextMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
		http.csrf().disable();
		http
		
		.authorizeRequests()
		.antMatchers("/","/login","/hip/**","/hip","/hipmaster","/hipmaster/**","/feature","/register","/accessDenied","/error").permitAll()
		
		.antMatchers("/cdg_home").hasAnyAuthority(AppRole.GENERIC_USER)
		.antMatchers("/extraction/ConnectionHome").hasAnyAuthority(AppRole.Source_Details)
		.antMatchers("/extraction/TargetDetails").hasAnyAuthority(AppRole.Target_Details)
		.antMatchers("/extraction/SystemHome").hasAnyAuthority(AppRole.Feed_Details)
		.antMatchers("/extraction/DataHome").hasAnyAuthority(AppRole.Data_Details)
		.antMatchers("/extraction/ExtractHome").hasAnyAuthority(AppRole.Extract_Data)
		.antMatchers("/scheduler/alljobs").hasAnyAuthority(AppRole.Scheduler)
		.antMatchers("/scheduler/scheduledjobs").hasAnyAuthority(AppRole.Master_Feed_Details)
		.antMatchers("/scheduler/runstats").hasAnyAuthority(AppRole.Current_Feed_Details)
		.antMatchers("/admin/project").hasAnyAuthority(AppRole.Add_Project)
		.antMatchers("/admin/user").hasAnyAuthority(AppRole.Add_User)
		.antMatchers("/systemOnboardForm").hasAnyAuthority(AppRole.Add_System)
		.antMatchers("/bg/cubg").hasAnyAuthority(AppRole.Manage_Business_Glossary)
		.antMatchers("/bg/sbg").hasAnyAuthority(AppRole.Search_Business_Glossary)
		.antMatchers("/publishing/addMetaDataHome").hasAnyAuthority(AppRole.Add_Metadata)
		
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied")
        .and()
        .formLogin()
        .loginPage("/login").defaultSuccessUrl("/login/submit")
        .permitAll();
		
		
		
		/*.formLogin().loginPage("/index").loginProcessingUrl("/index").defaultSuccessUrl("/login/submit")
		// Configures the logout function
		.and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/index")
		// Configures url based authorization
		.and().authorizeRequests()
		// Anyone can access the urls
		.antMatchers("/","/hip/**","/hip","/hipmaster","/hipmaster/**","/feature","/register","/accessDenied","/error").permitAll()
		// The rest of the our application is protected.
		.antMatchers("/cdg_home").hasAnyAuthority("ADMIN");*/
		
		
		
		/*http.formLogin().defaultSuccessUrl("/login/submit").
		and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/index").
		and().authorizeRequests().and().antMatchers("/cdg_home").hasAnyAuthority(AppRole.GENERIC_USER)*/
		
		/*http
				// Configures form login
				.formLogin().loginPage("/login").loginProcessingUrl("/login/authenticate")
				.failureUrl("/login?error=bad_credentials")
				// Configures the logout function
				.and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/login")
				// Configures url based authorization
				.and().authorizeRequests()
 
				// Pages do not require login
				.antMatchers("/","/hip/**","/hip","/hipmaster","/hipmaster/**","/feature","/register","/accessDenied","/error").permitAll()
				// The rest of the our application is protected.
				//.antMatchers("/feature").hasAnyAuthority("user")
				.and()
				.exceptionHandling().accessDeniedPage("/accessDenied")
				;
 
       //http.authorizeRequests().antMatchers("/userInfo","/extract/**","/publishing/**").access("hasRole('" + AppRole.ROLE_USER + "')")
       
       //.anyRequest().authenticated() ;
 */
     
 
    }
 
   
    @Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.authenticationProvider(
				activeDirectoryLdapAuthenticationProvider())/* .userDetailsService(userDetailsService()) */;
	}
    
    
    @Bean
	public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(
				domain, ldapurl);
		provider.setConvertSubErrorCodesToExceptions(true);
		provider.setUseAuthenticationRequestCredentials(true);
		provider.setUserDetailsContextMapper(userDetailsContextMapper);
		//provider.setAuthoritiesMapper(GrantedAuthoritiesMapper);
		return provider;
	}
    
    @Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/assets/**");
	}
 
}