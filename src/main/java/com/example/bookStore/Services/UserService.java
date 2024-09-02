package com.example.bookStore.Services;

import com.example.bookStore.Models.User;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface UserService {
    List<User> listUsers();
    Long countUserLength();
}
