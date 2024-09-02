package com.example.bookStore.Services.ServiceImpl;

import com.example.bookStore.Models.User;
import com.example.bookStore.Repositories.UserRepository;
import com.example.bookStore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public Long countUserLength() {
        return userRepository.countUserLength();
    }
}
