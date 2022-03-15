package com.example.excel.service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.Map;

public interface ExcelService {
    Boolean getExcel(String templateFile, Map<String, Object> params, OutputStream os);

    Boolean getExcel(String templateFile, String fileName, Map<String, Object> params, HttpServletResponse response);

    Boolean getExcel(String templateFile, Map<String, Object> params, File outputFile);
}
