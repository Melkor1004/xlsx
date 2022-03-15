package com.example.excel.util;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.WritableCellValue;
import org.jxls.transform.poi.WritableHyperlink;
import org.jxls.util.TransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static void exportExcel(String fileName, InputStream templateFile, Map<String, Object> params, HttpServletResponse response) throws IOException {
        response.reset();
        response.setHeader("Accept-Ranges", "bytes");
        OutputStream os = null;
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
        response.setContentType("application/octet-stream;charset=UTF-8");
        try {
            os = response.getOutputStream();
            exportExcel(templateFile, params, os);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void exportExcel(InputStream templateFile, Map<String, Object> params, OutputStream os) throws IOException {
        try {
            Context context = new Context();
            Set<String> keySet = params.keySet();
            for(String key: keySet) {
                context.putVar(key, params.get(key));
            }
            context.putVar("hello", "hello mother fucker");
            Map<String, Object> myFunction = new HashMap<>();
            myFunction.put("fun", new ExcelUtil());
            Transformer trans = TransformerFactory.createTransformer(templateFile, os);
            JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) trans.getTransformationConfig().getExpressionEvaluator();
            evaluator.getJexlEngine().setFunctions(myFunction);

            AreaBuilder areaBuilder = new XlsCommentAreaBuilder(trans);
            List<Area> areaList = areaBuilder.build();
            areaList.get(0).applyAt(new CellRef("sheet1!A1"), context);
            trans.write();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (templateFile != null) {
                    templateFile.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static Object formatData(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);
            return dateStr;
        }
        return"--";
    }

    public static WritableCellValue getLink(String address, String title) {
        return new WritableHyperlink(address, title);
    }
}
