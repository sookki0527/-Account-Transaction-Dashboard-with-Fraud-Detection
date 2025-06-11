package org.example.service;

import org.example.dto.RegisterRequest;
import org.example.dto.RoleDto;
import org.example.dto.UserDto;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserService(PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
                       RestTemplate restTemplate, UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String login(String username, String password) {
        System.out.println("login with user:" + username);
        UserDto userDto = new UserDto(
               username,
                null,
                null
        );
       User user = userRepository.findByUserName(username);
        List<RoleDto> roles = user.getRoles().stream().map(role -> new RoleDto(role.getName())).toList();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtUtil.generateToken(user.getUsername(), roles);
    }


    public void register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());


        Role role = roleRepository.findByRoleName(
                request.getIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER"
        ).orElseThrow(() -> new RuntimeException("Role not found"));
        User user = new User(request.getUsername(), encodedPassword, List.of(role));
        userRepository.save(user);
    }

    public void sample() throws InterruptedException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.example.com/data"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("response:" + response);
    }
}
