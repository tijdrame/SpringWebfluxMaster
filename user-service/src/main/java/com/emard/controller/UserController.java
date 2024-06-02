package com.emard.controller;

import com.emard.dto.UserDto;
import com.emard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService service;
    @GetMapping("all")
    public Flux<UserDto> all(){
        return service.all();
    }
    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(
            @PathVariable Integer id){
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(
                    ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<UserDto> createUser(@RequestBody Mono<UserDto> dto){
        return service.createUser(dto);
    }
    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(
            @PathVariable Integer id,
            @RequestBody Mono<UserDto> userDto){
        return service.updateUser(id, userDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(
                    ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(
            @PathVariable Integer id){
        return service.deleteUser(id);
    }

}
