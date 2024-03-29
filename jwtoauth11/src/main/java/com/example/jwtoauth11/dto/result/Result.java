package com.example.jwtoauth11.dto.result;

import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
public class Result implements Serializable {
    private boolean success;

    private int code;
    private String msg;
}
