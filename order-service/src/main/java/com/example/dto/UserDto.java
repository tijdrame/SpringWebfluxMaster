package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private Integer balance;
}
