package com.example.demo.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.Entities.Enums.Status;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.User.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public User addUser(User user) {
        return this.userRepository.addUser(user);
    }

    public List<User> allUsers() {
        return this.userRepository.allUsers();
    }

    public List<User> pageUsers(Integer page) {
        return this.userRepository.pageUsers(page);
    }

    public User findUser(Integer id) {
        return this.userRepository.findUser(id);
    }

    public User updateUser(User user) {
        return this.userRepository.updateUser(user);
    }

    public void updateStatus(Integer id, String status) throws Exception {
        this.userRepository.updateStatus(id, status);
    }

    public void deleteUser(Integer id) {
        this.userRepository.deleteUser(id);
    }

    public String login(String email, String password) {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findByEmailAndPassword(email,hashedPassword);
        if (user == null) {
            return null;
        }

        Algorithm algorithm = Algorithm.HMAC256("secret");

        Date expirationTime = Date.from(ZonedDateTime.now().plusHours(24).toInstant());

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("user_type", String.valueOf(user.getUser_type()).toUpperCase())
                .withExpiresAt(expirationTime)
                .sign(algorithm);
    }

    public boolean isAuthorized(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        int idUser = jwt.getClaim("id").asInt();

        User user = this.userRepository.findUser(idUser);

        return user != null && user.getStatus() != Status.INACTIVE;
    }

}
