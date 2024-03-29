package recipes.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String username);
    //User findByEmail(String email);
    boolean existsUserByEmail(String email);


}
