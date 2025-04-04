package money_changer.money_changer_service.repository;

import money_changer.money_changer_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByPhoneAndOtp(String phone, String otp);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertedId();
}

