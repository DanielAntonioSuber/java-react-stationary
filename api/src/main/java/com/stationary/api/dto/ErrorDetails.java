package com.stationary.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private Date time;
    private String message;
    private String details;
}