package alti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import alti.service.UserService;

@SpringBootApplication
public class JwtAuthServiceApp implements CommandLineRunner {

  @Autowired
  UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(JwtAuthServiceApp.class, args);
  }



  @Override
  public void run(String... params) throws Exception {
		/*
		 * User admin = new User(); admin.setUsername("admin");
		 * admin.setPassword("admin"); admin.setEmail("admin@email.com");
		 * userService.signup(admin);
		 */
  }

}
