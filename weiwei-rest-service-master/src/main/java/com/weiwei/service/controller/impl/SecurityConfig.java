package com.weiwei.service.controller.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.weiwei.controller.common.Constants;
import com.weiwei.service.externalwebservice.SomeExternalServiceAuthenticator;
import com.weiwei.service.register.RegisterFilter;
import com.weiwei.service.register.RegisterTokenAuthenticationProvider;
import com.weiwei.service.register.RegisterTokenService;
import com.weiwei.svr.manage.ICustomerManager;

@Configuration
@EnableWebMvcSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Value("${backend.admin.role}")
    private String backendAdminRole;
	private ApplicationContext context;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()
				.antMatchers("/weiwei/**").permitAll()
				.antMatchers(actuatorEndpoints()).hasRole(backendAdminRole)
				.anyRequest().authenticated();
		
		http
			.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
			.addFilterBefore(new ManagementEndpointAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
			.addFilterBefore(new RegisterFilter(authenticationManager()), BasicAuthenticationFilter.class);
	}
	
	private String[] actuatorEndpoints() {
        return new String[]{Constants.AUTOCONFIG_ENDPOINT, Constants.BEANS_ENDPOINT, Constants.CONFIGPROPS_ENDPOINT,
                Constants.ENV_ENDPOINT, Constants.MAPPINGS_ENDPOINT,
                Constants.METRICS_ENDPOINT, Constants.SHUTDOWN_ENDPOINT};
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.authenticationProvider(domainUsernamePasswordAuthenticationProvider())
			.authenticationProvider(backendAdminUsernamePasswordAuthenticationProvider())
			.authenticationProvider(tokenAuthenticationProvider())
			.authenticationProvider(registerTokenAuthenticationProvider());
	}
	
	@Bean
	public TokenService tokenService(){
		return new TokenService();
	}
	
	@Bean
	public RegisterTokenService registerTokenService(){
		return new RegisterTokenService();
	}
	
	@Bean
	public ExternalServiceAuthenticator someExternalServiceAuthenticator(ICustomerManager icm){
		SomeExternalServiceAuthenticator exAuthenticator = new SomeExternalServiceAuthenticator();
		exAuthenticator.setCustomerManager(icm);
		return exAuthenticator;
	}
	
	@Bean
	public AuthenticationProvider domainUsernamePasswordAuthenticationProvider(){
		return new DomainUsernamePasswordAuthenticationProvider(tokenService(), someExternalServiceAuthenticator(customerManagerInfraService()));
	}
	
	@Bean
	public AuthenticationProvider backendAdminUsernamePasswordAuthenticationProvider(){
		return new BackendAdminUsernamePasswordAuthenticationProvider();
	}
	
	@Bean
	public AuthenticationProvider tokenAuthenticationProvider(){
		return new TokenAuthenticationProvider(tokenService());
	}
	
	@Bean
	public AuthenticationProvider registerTokenAuthenticationProvider(){
		return new RegisterTokenAuthenticationProvider(registerTokenService());
	}
	
	public ICustomerManager customerManagerInfraService(){
		context = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		return (ICustomerManager)context.getBean("customerManagerImpl");
	}

}

