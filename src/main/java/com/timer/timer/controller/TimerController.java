package com.timer.timer.controller;

import com.timer.timer.model.TimerRequest;
import com.timer.timer.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @PostMapping("/timer")
    public void setTimer(@Validated @RequestBody TimerRequest timerRequest) {
        timerService.setTimer(timerRequest);
    }
}
