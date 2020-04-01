package alti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String uid;
  private String email;
  private String mobile;
  private String password;
  private String token;
}


//@Size(min = 8, message = "Minimum password length: 8 characters")
//@Column(unique = true, nullable = false)