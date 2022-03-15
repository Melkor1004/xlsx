package com.example.excel.service.impl;

import com.example.excel.dto.UserDTO;
import com.example.excel.service.ExcelService;
import com.example.excel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_REPORT = "user_report.xlsx";

    private final ExcelService excelService;

    @Override
    public Map<String, String> getInfoToExccel() throws IOException {
        UserDTO userDTO = UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .username("hello-mother-fucker")
                .fullName("Hế lô ma thờ phắc cơ")
                .build();
        List<UserDTO> userDTOs = new ArrayList<>();
        userDTOs.add(userDTO);
        UUID uuid = UUID.randomUUID();

        try (InputStream is = new ClassPathResource("/jxls/user_report.xlsx").getInputStream()) {
            try ( OutputStream os = new FileOutputStream(
                    System.getProperty("java.io.tmpdir") + "user_report_" + uuid + ".xlsx" )) {
                Context context = new Context();
                context.putVar("lists", userDTOs);

                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("fileName", uuid.toString());
        return map;
    }

    @Override
    public void exportTest(HttpServletResponse response) {
        List<UserDTO> users = new ArrayList<>();
        UserDTO user1 = UserDTO.builder().id("123").username("123").fullName("hello").build();
        UserDTO user2 = UserDTO.builder().id("234").username("234").fullName("mother").build();
        UserDTO user3 = UserDTO.builder().id("345").username("345").fullName("fucker").build();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        Map<String, Object> params = new HashMap<>();
        params.put("params", users);

        excelService.getExcel(USER_REPORT, "hellomotherfucker.xlsx", params, response);
    }
}
