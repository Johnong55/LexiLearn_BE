package com.chummy_backend.serverside.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chummy_backend.serverside.DTO.request.UsersRequest;
import com.chummy_backend.serverside.Exception.BadRequestException;
import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.general.Users;
import com.chummy_backend.serverside.Repository.UsersRepository;
import com.chummy_backend.serverside.Repository.libraryRepository;
import com.chummy_backend.serverside.Security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired

    private libraryRepository libraryRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersRequest request) {
        if (usersRepository.findByEmail(request.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        Users user = new Users();
        logger.info("Received user creation request: {}", request);
        user.setDisplayName(request.getDisplayName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        usersRepository.save(user);
        library lib = library.builder()
                .lib_name("learned")
                .ownerID(user)
                .build();
        libraryRepository.save(lib);
        return ResponseEntity.ok("Register successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsersRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = usersRepository.findByEmail(request.getEmail())  ;
        
        String token = jwtUtil.generateToken(request.getEmail(),user.getId());
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }
}
