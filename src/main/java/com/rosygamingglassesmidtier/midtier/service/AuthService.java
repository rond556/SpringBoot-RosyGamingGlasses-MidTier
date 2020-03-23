package com.rosygamingglassesmidtier.midtier.service;


import com.rosygamingglassesmidtier.midtier.dto.LoginRequest;
import com.rosygamingglassesmidtier.midtier.model.Users;
import com.rosygamingglassesmidtier.midtier.repository.UsersRepository;
import com.rosygamingglassesmidtier.midtier.dto.RegisterRequest;
import com.rosygamingglassesmidtier.midtier.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest){
        Users users = new Users();
        users.setUsername(registerRequest.getUsername());
        users.setPassword(encodePassword(registerRequest.getPassword()));
        users.setEmail(registerRequest.getEmail());

        usersRepository.save(users);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public String login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }
}
