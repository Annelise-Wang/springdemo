package com.ispan.demo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.ispan.demo.model.CustomUserDetails;

public class LoginLogoutLoginUnitTest {
	private CustomUserDetails.Builder userDetailsBuilder;

	@Before
	public void setUp() {
	    
	    userDetailsBuilder = new CustomUserDetails.Builder()
	            .withUsername("username")
	            .withPassword("password")
	            .withName("John Doe")
	            .withEmail("john@example.com")
	            .withAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}

	    @Test
	    public void testLoginLogoutLogin() {
	       
	        CustomUserDetails mockUserDetails = userDetailsBuilder.build();

	        //登入
	        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUserDetails, null);
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        //驗證登入
	        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());

	        //登出
	        SecurityContextHolder.clearContext();

	        //驗證登出
	        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());

	        // 再次登入
	        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(mockUserDetails, null);
	        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

	        //驗證再次登入
	        assertEquals(newAuthentication, SecurityContextHolder.getContext().getAuthentication());
	    }
	}