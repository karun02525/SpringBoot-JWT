package alti.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alti.model.AuthRequest;
import alti.model.User;
import alti.model.common.ResponseModel;
import alti.model.common.ResponseObjectModel;
import alti.repository.UserRepository;
import alti.security.JwtTokenProvider;
import alti.utils.Utils;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public ResponseEntity<?> login(AuthRequest authRequest) {
		final User userPayload = userRepository.findByMobile(authRequest.getMobile());
		if (userPayload == null) {
			return new ResponseEntity<>(new ResponseModel(false, "Mobile number or password invalid ! please try again"),HttpStatus.BAD_REQUEST);
		} else {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userPayload.getUid(), authRequest.getPassword()));	
				userPayload.setToken(jwtTokenProvider.createToken(userPayload.getUid()));
				userPayload.setPassword("");
				return new ResponseEntity<>(new ResponseObjectModel(true, "Your login successfully", userPayload),HttpStatus.OK);

			} catch (AuthenticationException e) {
				return new ResponseEntity<>(new ResponseModel(false, "Mobile number or password invalid ! please try again"),HttpStatus.BAD_REQUEST);
			}
		}
	}

	public ResponseEntity<?> register(User user) {
		if (!userRepository.existsByMobile(user.getMobile())) {
			String uid = Utils.timeStamp();
			user.setUid(uid);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			user.setPassword("");
			user.setToken(jwtTokenProvider.createToken(uid));
			return new ResponseEntity<>(new ResponseObjectModel(true, "Your registration successfully", user),
					HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(new ResponseModel(false, "Mobile is already in use"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/*
	 * @Override public ResponseEntity<?> login(LoginUser loginUser) { Query query =
	 * new Query(); User userData;
	 * query.addCriteria(Criteria.where("mobile").is(loginUser.getMobile()));
	 * userData = mongoTemplate.findOne(query, User.class);
	 * 
	 * if (userData == null) { return new ResponseEntity<>(new
	 * ResponseObjectModel(false,
	 * "mobile number or password invalid ! please try again", null),
	 * HttpStatus.BAD_REQUEST); } else { Authentication auth = authenticate(new
	 * AJwtRequest(loginUser.getMobile(), loginUser.getPassword())); final String
	 * token = jwtTokenUtil.generateToken(auth); userData.setToken(token);
	 * userData.setPassword(""); return new ResponseEntity<>(new
	 * ResponseObjectModel(true, "Your login successfully", userData),
	 * HttpStatus.OK); } }
	 */

	public void delete(String username) {
		userRepository.deleteByMobile(username);
	}

	public User search(String username) {
		User user = userRepository.findByMobile(username);
		if (user == null) {
		   //	throw new CustomUnauthories401Exception("The user doesn't exist");
		}
		return user;
	}

	public User whoami(HttpServletRequest req) {
		return userRepository.findByMobile(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(username);
	}

}
