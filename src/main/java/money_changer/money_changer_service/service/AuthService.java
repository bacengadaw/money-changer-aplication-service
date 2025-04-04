package money_changer.money_changer_service.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import money_changer.money_changer_service.entity.User;
import money_changer.money_changer_service.model.request.auth.LoginRequest;
import money_changer.money_changer_service.model.request.auth.OtpRequest;
import money_changer.money_changer_service.model.request.auth.RegisterRequest;
import money_changer.money_changer_service.model.request.auth.UpdatePasswordRequest;
import money_changer.money_changer_service.model.response.auth.LoginResponse;
import money_changer.money_changer_service.model.response.auth.RegisterResponse;
import money_changer.money_changer_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not Found"));
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String generateToken = jwtService.generateToken(userDetails);
        user.setRememberToken(generateToken);
        userRepository.save(user);
        return LoginResponse.builder()
                .token(generateToken)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        validationService.validate(request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Sudah Terdaftar");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number Phone Sudah Terdaftar");
        }

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setOtp(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)));
        user.setExpiredOtp(LocalDateTime.now().plusHours(3));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setReferralCode(request.getReferralCode());
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Hallo Selamat Datang",
                "Kamu berhasil daftar revamp valast ini OTP kamu " + user.getOtp());

        return RegisterResponse.builder()
                .email(user.getEmail())
                .phone(user.getPhone())
                .referralCode(user.getReferralCode())
                .expiredOtp(user.getExpiredOtp())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Transactional
    public void submitOtp(OtpRequest request) {
        validationService.validate(request);

        User user = userRepository.findFirstByPhoneAndOtp(request.getUsername(), request.getOtp())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kode OTP Tidak Sesuai"));

        if (user.getExpiredOtp().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Otp Anda Sudah Expired");
        }

        user.setExpiredOtp(null);
        user.setOtp(null);
        user.setIsSetOtp(1);
        userRepository.save(user);
    }

    @Transactional
    public void otpRequest(OtpRequest otpRequest) {
        validationService.validate(otpRequest);

        User user = userRepository.findByPhone(otpRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number Phone Tidak ada"));
        user.setOtp(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)));
        user.setExpiredOtp(LocalDateTime.now().plusHours(3));
        user.setIsSetOtp(0);
        userRepository.save(user);

        // send email
        emailService.sendEmail(user.getEmail(), "Hallo Selamat Datang",
                "Kamu berhasil daftar revamp valast ini OTP kamu " + user.getOtp());
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        validationService.validate(updatePasswordRequest);

        User user = userRepository.findByPhone(updatePasswordRequest.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number Phone Tidak ada"));

        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        userRepository.save(user);
    }
}
