package com.albaraka.albaraka.config.security;

import com.albaraka.albaraka.model.dto.user.LoginDTO;
import com.albaraka.albaraka.model.entity.User;
import com.albaraka.albaraka.model.entity.UserOAuth;
import com.albaraka.albaraka.model.enums.OauthProvider;
import com.albaraka.albaraka.repository.RoleRepository;
import com.albaraka.albaraka.repository.UserOAuthRepository;
import com.albaraka.albaraka.repository.UserRepository;
import com.albaraka.albaraka.service.impl.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final UserOAuthRepository userOAuthRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = authToken.getAuthorizedClientRegistrationId();

        String email;
        String providerId;
        String fullName;
        OauthProvider provider;

        if (registrationId.equalsIgnoreCase("google")) {
            provider = OauthProvider.GOOGLE;
            OidcUser oidcUser = (OidcUser) authToken.getPrincipal();

            providerId = oidcUser.getSubject();
            email = oidcUser.getEmail();
            fullName = oidcUser.getFullName();
        } else {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            providerId = oAuth2User.getAttribute("id");
            email = oAuth2User.getAttribute("email");
            fullName = oAuth2User.getAttribute("name");

            switch (registrationId.toLowerCase()){
                case "facebook" -> provider = OauthProvider.FACEBOOK;
                default -> throw new BadRequestException(
                        "Fournisseur OAuth non pris en charge: " + registrationId
                );
            }
        }

        User user = userRepository.findByEmail(email).orElseGet( () ->
                userRepository.save(User.builder()
                                .email(email)
                                .fullName(fullName)
                                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                                .role(roleRepository.findByName("ROLE_CLIENT").orElse(null))
                                .build()
                )
        );

        if (userOAuthRepository.findByUserAndProvider(user, provider).isEmpty())
            userOAuthRepository.save(
                    UserOAuth.builder()
                            .uuid(UUID.randomUUID())
                            .user(user)
                            .provider(provider)
                            .providerId(providerId)
                            .build()
            );


        String accessToken = jwtService.generateToken(user);
        LoginDTO login = LoginDTO.builder()
                .uuid(user.getUuid())
                .role(user.getRole().getName())
                .accessToken(accessToken)
                .build();

        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(login));
    }
}
