package xwsagent.wallservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import xwsagent.wallservice.domain.Authority;
import xwsagent.wallservice.domain.Role;
import xwsagent.wallservice.domain.User;
import xwsagent.wallservice.repository.AuthorityRepository;
import xwsagent.wallservice.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User findById(Long id) throws AccessDeniedException {
		User user = userRepository.findById(id).orElseGet(null);
		return user;
	}
	
	public User findByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			System.out.println("Not found!");
		}else {
			System.out.println("Successfully found");
		}
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			System.out.println("Not found");
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			System.out.println("Successfully found");
			return user;
		}
	}
	
	public User save(User userRequest) {
	
		User user = new User();
		user.setRole(Role.USER);
		List<Authority> auth = this.findByname("ROLE_USER");
		user.setAuthorities(auth);
		user.setUsername(userRequest.getUsername());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		
		user = this.userRepository.save(user);
		return user;
		
	}
	
	public List<Authority> findByname(String name) {
	    Authority auth = this.authRepository.findByName(name);
	    List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
	    return auths;
	  }


	
}
