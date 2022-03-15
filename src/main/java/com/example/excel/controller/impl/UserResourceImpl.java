package com.example.excel.controller.impl;

import com.example.excel.controller.UserResource;
import com.example.excel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    @Override
    public ResponseEntity<Map<String, String>> export() throws IOException {
        return ResponseEntity.ok(this.userService.getInfoToExccel());
    }

    @Override
    public void exportTest(HttpServletResponse response) {
        this.userService.exportTest(response);
    }
}
