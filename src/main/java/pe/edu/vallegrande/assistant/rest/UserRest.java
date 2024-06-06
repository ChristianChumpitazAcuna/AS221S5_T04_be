package pe.edu.vallegrande.assistant.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.assistant.model.User;
import pe.edu.vallegrande.assistant.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserRest {

    @Autowired
    private UserService userService;

    @GetMapping("/actives")
    public ResponseEntity<Flux<User>> findAllActives() {
        return ResponseEntity.ok(userService.getAll("A"));
    }

    @GetMapping("/inactives")
    public ResponseEntity<Flux<User>> findAllInactives() {
        return ResponseEntity.ok(userService.getAll("I"));
    }

    @PostMapping("/save")
    public ResponseEntity<Mono<User>> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Mono<User>> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Mono<User>> changeStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.changeStatus(id, "I"));
    }

    @PutMapping("activate/{id}")
    public ResponseEntity<Mono<User>> activate(@PathVariable Long id) {
        return ResponseEntity.ok(userService.changeStatus(id, "A"));
    }
}
