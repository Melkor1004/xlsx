package com.example.excel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@RequestMapping("/api")
public interface UserResource {

    @GetMapping("/users/export")
    ResponseEntity<Map<String, String>> export() throws IOException;

    @GetMapping("/test/export")
    void exportTest(HttpServletResponse response);
}
