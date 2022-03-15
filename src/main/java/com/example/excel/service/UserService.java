package com.example.excel.service;

import com.example.excel.dto.UserDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, String> getInfoToExccel() throws IOException;

    public void exportTest(HttpServletResponse response);
}
