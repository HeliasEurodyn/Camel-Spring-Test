package com.flexi.jwt;


import com.flexi.model.User;
import com.flexi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {


	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if(user != null)
		{
			return new	JwtUserDetails(user.getId(), user.getUsername(),
					user.getPassword(),"ROLE_USER_2");
		}
		else
		{
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

	}


}
