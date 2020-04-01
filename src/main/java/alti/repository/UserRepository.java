package alti.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import alti.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByMobile(String mobile);

  User findByMobile(String mobile);
  
  User findByUid(String uid);

  @Transactional
  void deleteByMobile(String mobile);

}
