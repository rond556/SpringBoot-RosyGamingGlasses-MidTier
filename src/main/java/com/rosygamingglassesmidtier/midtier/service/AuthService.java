package com.rosygamingglassesmidtier.midtier.service;


import com.rosygamingglassesmidtier.midtier.model.Users;
import com.rosygamingglassesmidtier.midtier.repository.UsersRepository;
import com.rosygamingglassesmidtier.midtier.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(RegisterRequest registerRequest){
        Users users = new Users();
        users.setUsername(registerRequest.getUsername());
        users.setPassword(encodePassword(registerRequest.getPassword()));
        usersRepository.save(users);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
