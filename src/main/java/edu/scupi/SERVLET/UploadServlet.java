package edu.scupi.SERVLET;

import blackboard.data.course.Course;
import edu.scupi.HANDLER.BBHandler;
import edu.scupi.HANDLER.BOMHandler;
import edu.scupi.HANDLER.FileHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ServletContext app = request.getSession().getServletContext();
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        String TEMP = "/usr/local/blackboard/apps/tomcat/temp/";
        String NOTFOUND = "COURSE NOT FOUND";
        File file = null;
        TreeMap<String, List<String>> cuList;
        TreeMap<String, List<String>> cnuList = new TreeMap<>();
        String errMsg = null;
        try {
            List<FileItem> list = upload.parseRequest(request);
            if (list.size() > 0) {
                FileItem item;
                if (list.get(0).getName() != null) {
                    item = list.get(0);
                } else {
                    item = list.get(1);
                }
                file = new File(TEMP, item.getName());
                InputStream ins = item.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024000];
                while (true) {
                    int n = ins.read(b);
                    if (n == -1)
                        break;
                    fos.write(b, 0, n);
                }
                ins.close();
                fos.close();
            }
        } catch (FileUploadException fileUploadException) {
            errMsg = "Error occurred when uploading the file, please try again.";
        }

        if (!file.getName().endsWith(".csv")) {
            errMsg = "Only csv file (exported from the software) supported, please try again.";
        }

        if (null != errMsg) {
            request.setAttribute("err", errMsg);
            request.getRequestDispatcher("/execute/index.jsp").forward(request, response);
        }

        cuList = FileHandler.getMap(file);
        ArrayList<String> nekeys = new ArrayList<>();

        for (String key : cuList.keySet()) {
            Course course = BBHandler.loadCourseByCourseId(key);
            if (null != course) {
                cnuList.put(key + ": " + course.getTitle(), cuList.get(key));
            } else {
                cnuList.put(key + ": " + NOTFOUND, cuList.get(key));
                nekeys.add(key);
            }
        }
        //delete non-exist keys
        cuList.keySet().removeIf(nekeys::contains);

        app.setAttribute("COURSEUSERLIST", cuList);
        request.setAttribute("content", cnuList);
        request.getRequestDispatcher("/execute/preview.jsp").forward(request, response);
    }

}
