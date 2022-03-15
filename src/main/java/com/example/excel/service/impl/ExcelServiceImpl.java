package com.example.excel.service.impl;

import com.example.excel.service.ExcelService;
import com.example.excel.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
    private static final String templatePath = "classpath:jxls/";

    @Override
    public Boolean getExcel(String templateFile, Map<String, Object> params, OutputStream os) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ResourceUtils.getFile(templatePath + templateFile));
            ExcelUtil.exportExcel(inputStream, params, os);
        } catch (IOException e) {
            logger.error("excel export has error" + e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean getExcel(String templateFile, String fileName, Map<String, Object> params, HttpServletResponse response) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ResourceUtils.getFile(templatePath + templateFile));
            ExcelUtil.exportExcel(fileName, inputStream, params, response);
        } catch (IOException e) {
            logger.error("excel export has error" + e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean getExcel(String templateFile, Map<String, Object> params, File outputFile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(templatePath + templateFile);
            File dFile = outputFile.getParentFile();
            if (dFile.isDirectory()) {
                if (!dFile.exists()) {
                    dFile.mkdir();
                }
            }
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            ExcelUtil.exportExcel(inputStream, params, new FileOutputStream(outputFile));
        } catch (IOException e) {
            logger.error("excel export has error" + e);
            return false;
        }
        return false;
    }
}
