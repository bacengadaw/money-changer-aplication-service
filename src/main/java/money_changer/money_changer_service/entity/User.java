package money_changer.money_changer_service.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Long id;

    @NotBlank
    private String uuid;

    private String name;

    private String username;

    private String password;

    private String email;

    @Column(name = "identity_type")
    private String identityType;

    @Column(name = "date_of_birth")
    private Date DateOfBirth;

    private String otp;

    @Column(name = "expired_otp")
    private LocalDateTime expiredOtp;

    private String phone;

    @Column(name = "phone_blast")
    private String phoneBlast;

    @Column(name = "is_set_otp")
    private Integer isSetOtp;

    private String pin;

    @Column(name = "photo_profil")
    private String photoProfil;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "referal_code")
    private String referralCode;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //TODO: add proper boolean checks
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
