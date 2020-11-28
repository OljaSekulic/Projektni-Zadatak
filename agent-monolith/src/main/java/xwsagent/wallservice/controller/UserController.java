package xwsagent.wallservice.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wallservice.domain.Authority;
import xwsagent.wallservice.domain.User;
import xwsagent.wallservice.domain.UserTokenState;
import xwsagent.wallservice.repository.UserRepository;
import xwsagent.wallservice.security.JwtAuthenticationRequest;
import xwsagent.wallservice.security.TokenUtils;
import xwsagent.wallservice.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired 
	TokenUtils tokenUtils;
	
	@Autowired
	UserService userService;

	
	@Autowired
	private UserRepository userRepository;
	
	public Authentication authentication;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<?> signup(@RequestBody User user){
		
		if(userService.findByUsername(user.getUsername()) != null){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }else {
        	userService.save(user);
            String jwt = tokenUtils.generateToken(user.getUsername());
    		int expiresIn = tokenUtils.getExpiredIn();
    		Authority authority = userService.findByname("ROLE_USER").get(0);
    		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, authority, user));
        }

        
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {
		System.out.println(authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());

		authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
  
        User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		System.out.println(SecurityContextHolder.getContext().getAuthentication() + "Auth");
		Collection<? extends GrantedAuthority> auths = user.getAuthorities();
			
		Authority a = (Authority) auths.iterator().next();
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, a, user));
		
	}
	
	@GetMapping("/whoami")
    public ResponseEntity<?> getUser(){
		
		User u = userService.findByUsername(authentication.getName());
		return ResponseEntity.ok(u);
                
    }
	
	@GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
		List<User> users= userRepository.findAll();
		return ResponseEntity.ok(users);
                
    }
	
	
	@GetMapping(value = "/who")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> whoami(Principal p){
		System.out.println(p);
		User u = userService.findByUsername(p.getName());
		return ResponseEntity.ok(u);
	}
	
	
}
