package alti.security;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import alti.model.User;
import alti.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(MyUserDetails.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException { 
    final User user = userRepository.findByUid(uid);
    
    if (user == null) { 
    	log.info("user destails"+user);
	       throw new UsernameNotFoundException("Unauthorized Please try again!!");
	}
    return new org.springframework.security.core.userdetails.User(user.getUid(),user.getPassword(),new ArrayList<>());
	 
  }
}
