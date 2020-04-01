package alti.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alti.model.AuthRequest;
import alti.model.User;
import alti.model.common.ResponseObjectModel;
import alti.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest loginUser) {
		return userService.login(loginUser);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		return userService.register(user);
	}

	@DeleteMapping(value = "/{username}")
	public String delete(@PathVariable String username) {
		userService.delete(username);
		return username;
	}

	
	@GetMapping("/test")
    public ResponseEntity<?> test1() {
		Map<String,String> map=new HashMap<>();
		map.put("uid",SecurityContextHolder.getContext().getAuthentication().getName());
		return new ResponseEntity<>(new ResponseObjectModel(true, "test successfully", map),	HttpStatus.OK);
	  
	}
	
	@GetMapping(value = "/{username}")
	public User search(@PathVariable String username) {
		return userService.search(username);
	}

	@GetMapping("/refresh")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}
	

}
