package com.timer.timer.invoke;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class TimerUserInvoke {

    public String loginUserName() {
        String loginUserNameMessage = "Login olundu";
        System.out.println("Belirtilen sürede userName girdiniz");
        return loginUserNameMessage;
    }

    public String loginPassword() {
        String loginPasswordMessage="Şifre giriniz";
        System.out.println("Belirtilen sürede şifrenizi girdiniz");
        return loginPasswordMessage;
    }
}
