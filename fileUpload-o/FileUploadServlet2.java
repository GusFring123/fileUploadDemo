package com.myshop.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(name = "FileUploadServlet2", urlPatterns = "/fileUpload2")
public class FileUploadServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //接受文件上传
            //1.创建磁盘文件工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2.设置缓存文件大小(1M)，以及临时文件的存储位置
            factory.setSizeThreshold(1024 * 1024);
            String temp_path = this.getServletContext().getRealPath("temp");
            factory.setRepository(new File(temp_path));
            //3.创建文件上传的核心类
            ServletFileUpload upload = new ServletFileUpload(factory);
            //4.设置上传文件的名称的编码模式
            upload.setHeaderEncoding("UTF-8");
            //5.ServletFileUpload的API
            boolean multipartContent = upload.isMultipartContent(request);
            //6.判断表单是否为文件上传的表单
            if (multipartContent) {
                //7.如果是文件上传的表单，则解析request来获得文件项集合
                List<FileItem> parseRequest = upload.parseRequest(request);
                //8.遍历集合来判断是普通表单项还是文件上传表单
                for (FileItem item : parseRequest) {
                    boolean formField = item.isFormField();
                    if (formField) {
                        //9.普通表单项获取name与value,并设置普通表单项的编码方式，打印处理
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getString("UTF-8");
                        System.out.println(fieldName+" "+ fieldValue);
                    } else {
                        //10.文件上传表单项则获取文件上传名
                        String fileName = item.getName();
                        //11.获得文件上传的内容
                        InputStream inputStream = item.getInputStream();
                        //12.指定存储位置
                        String store_path = this.getServletContext().getRealPath("upload");
                        OutputStream outputStream = new FileOutputStream(store_path+"/"+fileName);
                        //13.复制文件
                        IOUtils.copy(inputStream,outputStream);
                        //14.关闭流
                        inputStream.close();
                        outputStream.close();
                        //15.删除临时文件
                        item.delete();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
