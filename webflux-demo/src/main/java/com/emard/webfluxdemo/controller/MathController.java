package com.emard.webfluxdemo.controller;

import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("math")
public class MathController {
    private final MathService mathService;

    @GetMapping("square/{input}")
    public Response findSquare(@PathVariable int input){
        return this.mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }
}
