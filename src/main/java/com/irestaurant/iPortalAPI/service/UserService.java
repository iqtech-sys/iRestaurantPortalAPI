package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.enumerators.Roles;
import com.irestaurant.iPortalAPI.exception.InvalidCredentials_1101;
import com.irestaurant.iPortalAPI.exception.InvalidToken_1201;
import com.irestaurant.iPortalAPI.exception.TokenExpired_1301;
import com.irestaurant.iPortalAPI.exception.UserFoundException_1001;
import com.irestaurant.iPortalAPI.model.DbRole;
import com.irestaurant.iPortalAPI.model.DbUser;
import com.irestaurant.iPortalAPI.repository.RoleRepository;
import com.irestaurant.iPortalAPI.repository.UserRepository;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${iPortalApi.resetToken}")
    private int resetToken;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }
    
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        DbUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<DbUser> registerUser(String username, String email, String password) throws UserFoundException_1001 {
        if (userRepository.findByUsername(username) != null || 
            userRepository.findByEmail(email) != null) {
            throw new UserFoundException_1001("Username already exists");
        }
        DbUser user = new DbUser(username, email, passwordEncoder.encode(password));
        Optional<DbRole> userRole = roleRepository.findByName(Roles.User.name());

        if (userRole.isEmpty()) {
            DbRole role = new DbRole(Roles.User.name());
            role = roleRepository.save(role);
            user.addRole(role);
        } else {
            user.addRole(userRole.get());
        }
        return CompletableFuture.completedFuture(userRepository.save(user));
    }

    @Async
    public CompletableFuture<String> loginUser(String email, String password) throws InvalidCredentials_1101 {
        DbUser user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            UserDetails userDetails = loadUserByUsername(user.getUsername());
            return CompletableFuture.completedFuture(jwtUtil.generateToken(userDetails));
        }
        throw new InvalidCredentials_1101("Invalid credentials");
    }

    @Async
    public void processForgotPassword(String email, String language) {
        DbUser user = userRepository.findByEmail(email);
        if (user != null) {
            String token = java.util.UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(resetToken)); // Valid for 15 minutes
            userRepository.save(user);
            emailService.sendEmail(user.getEmail(), token, language);
        }
    }

    @Async
    public void processResetPassword(String token, String newPassword)
            throws InvalidToken_1201, TokenExpired_1301 {
        DbUser user = userRepository.findByResetToken(token);
        if (user == null) {
            throw new InvalidToken_1201("Invalid token");
        }

        if (user.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
            throw new TokenExpired_1301("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
