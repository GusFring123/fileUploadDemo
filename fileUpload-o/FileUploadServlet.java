package com.myshop.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "FileUploadServlet", urlPatterns = "/fileUpload")
public class FileUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //接收文件上传
            //1.创建磁盘文件工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2.创建文件上传的核心类
            ServletFileUpload upload = new ServletFileUpload(factory);
            //3.解析request---获得文件项集合
            List<FileItem> parseRequest = upload.parseRequest(request);
            //4.遍历文件项集合
            for (FileItem item : parseRequest) {
                //5.判断文件项是普表单项还是文件上传项
                boolean formField = item.isFormField();//是否是一个普通表单项
                if (formField) {
                    //是一个普通表单项
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    System.out.println(fieldName + "  " + fieldValue);
                } else {
                    //文件上传项
                    //获取上传文件的名称
                    String fileName = item.getName();
                    //获取上传文件的内容
                    InputStream inputStream = item.getInputStream();

                    //将流中的数据拷贝到服务器上
                    String path = this.getServletContext().getRealPath("upload");
                    OutputStream outputStream = new FileOutputStream(path + "/" + fileName);
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }

                    //关闭流
                    inputStream.close();
                    outputStream.close();
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
