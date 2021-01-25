package com.timer.timer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimerRequest implements Serializable {


    private String instanceName;

    private String methodName;

    private int millisecondTime;

    private Boolean isRepetitive;

}
