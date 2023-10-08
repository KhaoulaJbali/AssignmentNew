package com.example.assignment.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("admin".equals(username)) {
			return User.builder().username("admin")
					.password("$2a$12$X893Z1IAkWX/McZIHYyG9uGITvUTjbouen8Db2Ppd29B8CtPtcdzy").roles("ADMIN").build();
		} else if ("user".equals(username)) {
			return User.builder().username("user")
					.password("$2a$12$PPcS3ARF0ktS3BaNxukwVOAuJj5Pqu2ewIg2N5vEZKlWYQabx5gj.") // Encoded password for
																								// "user"
					.roles("USER").build();
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
