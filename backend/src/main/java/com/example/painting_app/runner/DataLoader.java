package com.example.painting_app.runner;

import com.example.painting_app.model.User;
import com.example.painting_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("Creating sample users...");
            userRepository.save(new User("Ali", "123"));
            userRepository.save(new User("Amir", "123"));
			userRepository.save(new User("Hasan", "123"));
            System.out.println("Sample users created.");
        }
    }
}