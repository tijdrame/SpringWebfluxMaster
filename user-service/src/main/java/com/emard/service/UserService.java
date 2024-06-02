package com.emard.service;

import com.emard.dto.UserDto;
import com.emard.repo.UserRepository;
import com.emard.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;
    public Flux<UserDto> all(){
        return repository.findAll()
                .map(EntityDtoUtil::toUserDto);
    }
    public Mono<UserDto> getUserById(final int id){
        return repository.findById(id)
                .map(EntityDtoUtil::toUserDto);
    }
    public Mono<UserDto> createUser(Mono<UserDto> userDto){
        return userDto.map(EntityDtoUtil::toUser)
                .flatMap(repository::save)
                .map(EntityDtoUtil::toUserDto);
    }
    public Mono<UserDto> updateUser(int id, Mono<UserDto>userDto){
        return repository.findById(id)
                .flatMap(u-> userDto.map(EntityDtoUtil::toUser)
                        .doOnNext(e-> e.setId(id)))
                .flatMap(repository::save)
                .map(EntityDtoUtil::toUserDto);
    }
    public Mono<Void> deleteUser(int id){
        return repository.deleteById(id);
    }
}
