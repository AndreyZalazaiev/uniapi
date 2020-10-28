package andrew.projects.uniapi.Repos;

import andrew.projects.uniapi.Entities.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepo extends CrudRepository<Account,Integer> {
    @Query("from Account a where a.nickname = :nickname and a.password=:password")
    Optional<Account> login(@Param("nickname") String nickname, @Param("password") String password);
    @Query("from Account a where a.nickname=:nickname or a.email=:email")
    Optional<Account> isRegistered(@Param("nickname") String nickname, @Param("email") String email);


}
