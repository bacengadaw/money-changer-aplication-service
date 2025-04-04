package money_changer.money_changer_service.service;

import money_changer.money_changer_service.entity.User;
import money_changer.money_changer_service.model.response.user.UserResponse;
import money_changer.money_changer_service.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse getDataUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return UserResponse.builder()
                .uuid(currentUser.getUuid())
                .email(currentUser.getEmail())
                .phone(currentUser.getPhone())
                .name(currentUser.getName())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }
}
