package com.example.demo.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataIntegrityViolationException.class})
    public String handleError() {
        return "bad_category_error";
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
