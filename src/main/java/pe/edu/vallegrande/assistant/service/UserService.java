package pe.edu.vallegrande.assistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.assistant.model.User;
import pe.edu.vallegrande.assistant.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<User> getAll(String status) {
        return userRepository.findByStatus(status);
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    public Mono<User> update(Long id, User user) {
        return userRepository.findById(id)
                .flatMap(userUpdate -> {
                    if (user.getName() != null) {
                        userUpdate.setName(user.getName());
                    }
                    if (user.getEmail() != null) {
                        userUpdate.setEmail(user.getEmail());
                    }
                    if (user.getPassword() != null) {
                        userUpdate.setPassword(user.getPassword());
                    }
                    if (user.getStatus() != null) {
                        userUpdate.setStatus(user.getStatus());
                    }

                    return userRepository.save(userUpdate);
                });
    }

    public Mono<User> changeStatus(Long id, String status) {
        return userRepository.updateStatusById(id, status)
                .then(userRepository.findById(id));
    }
}
