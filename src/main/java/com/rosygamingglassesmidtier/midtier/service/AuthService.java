package com.rosygamingglassesmidtier.midtier.service;


import com.rosygamingglassesmidtier.midtier.model.Users;
import com.rosygamingglassesmidtier.midtier.repository.UsersRepository;
import dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    public void signup(RegisterRequest registerRequest){
        Users users = new Users();
        users.setUsername(registerRequest.getUsername());
        users.setPassword(registerRequest.getPassword());
        usersRepository.save(users);
    }
}
