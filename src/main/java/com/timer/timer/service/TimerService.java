package com.timer.timer.service;

import com.timer.timer.model.TimerRequest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

@Service
public class TimerService {

    public void setTimer(TimerRequest timerRequest) {
        final ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        final Set<BeanDefinition> classes = provider.findCandidateComponents("com.timer.timer.invoke");
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int timerCounter = 0;
            Object invokeValue = null;
            Object obj = null;
            Method getNameMethod = null;
            int reCallRepeatCounter = 0;

            @Override
            public void run() {
                System.out.println("Run Timer:" + timerCounter++);
                if (!timerRequest.getIsRepetitive()) {
                    for (BeanDefinition bean : classes) {
                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName(bean.getBeanClassName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Class = " + clazz.getSimpleName());
                        if (timerRequest.getInstanceName().equals(clazz.getSimpleName())) {
                            for (Method method : clazz.getMethods()) {
                                if (timerRequest.getMethodName().equals(method.getName())) {
                                    newInstance(clazz, method);
                                    reCallRepeatCounter++;
                                    System.out.println("Method Name = " + method.getName());
                                    System.out.println("Invoke Value = " + invokeValue.toString() + " ReCall Repeat: "
                                            + reCallRepeatCounter + " Date:  " + new Date());
                                    timer.cancel();
                                }
                            }
                        }

                    }

                } else if (timerRequest.getIsRepetitive()) {
                    for (BeanDefinition bean : classes) {
                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName(bean.getBeanClassName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Class Name= " + clazz.getSimpleName());
                        if (timerRequest.getInstanceName().equals(clazz.getSimpleName())) {

                            for (Method method : clazz.getMethods()) {
                                if (method.getName().equals(timerRequest.getMethodName())) {
                                    reCallRepeatCounter++;

                                    newInstance(clazz, method);
                                    System.out.println("Method Name = " + method.getName());
                                    System.out.println("Invoke Value = " + invokeValue.toString() + " ReCall Repeat: "
                                            + reCallRepeatCounter + " Date:  " + new Date());
                                }
                            }
                        }
                    }
                }
            }

            private void newInstance(Class<?> clazz, Method method) {
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                try {
                    getNameMethod = obj.getClass().getMethod(method.getName());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    invokeValue = getNameMethod.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, timerRequest.getMillisecondTime() * 100L);
    }
}
