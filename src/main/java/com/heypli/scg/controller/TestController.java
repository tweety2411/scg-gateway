package com.heypli.scg.controller;

import com.heypli.scg.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class TestController {

    //@RequiredArgsConstructor는 final이 붙은 초기화되지 않은 필드 생성자를 만들어준다
    // @NotNull  도 가능
    // 의존성을 높이기 위해 
    private final TestService testService;

    @GetMapping("/test")
    public String test() {
        int randomNo = new Random().nextInt();
        String result = randomNo % 2 == 0? testService.test(): testService.fail();
        return result;
    }

}
