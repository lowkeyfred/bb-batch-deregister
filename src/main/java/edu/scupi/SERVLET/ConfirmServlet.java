package edu.scupi.SERVLET;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.user.User;
import blackboard.persist.Id;
import blackboard.servlet.tags.ajax.facade.nautilus.NautilusEwsRuleDisplayHelper;
import edu.scupi.HANDLER.BBHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ConfirmServlet extends HttpServlet {
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ServletContext app = request.getSession().getServletContext();
        Object content = app.getAttribute("COURSEUSERLIST");
        TreeMap<String, String> result = new TreeMap<>();
        TreeMap<String, List<String>> delList = null;
        ArrayList<String> necourses = new ArrayList<>();
        String NECOURSE = "Not exist course, ignored";
        necourses.add(NECOURSE);
        String NOTFOUND = "Not found in course, ignored";
        String NOTDEL = "Not deleted, try delete manually";
        if (content instanceof TreeMap<?, ?>) {
            TreeMap<String, List<String>> cuList = (TreeMap<String, List<String>>) content;
            delList = new TreeMap<>();
            for (String key : cuList.keySet()) {
                Course course = BBHandler.loadCourseByCourseId(key);
                if (null == course) {
                    delList.put(key, necourses);
                    continue;
                }
                String fullName = key + ": " + course.getTitle();
                delList.put(fullName, new ArrayList<>());
                for (String user : cuList.get(key)) {
                    User cu = BBHandler.loadUserByUserName(user);
                    if (null != cu) {
                        Id courseId = course.getId();
                        Id userId = cu.getId();
                        CourseMembership cmu = BBHandler.loadCourseMemberShipByCourseIdAndUserId(courseId, userId);
                        if (null == cmu) {
                            delList.get(fullName).add(user + " " + NOTFOUND);
                            continue;
                        }
                        try {
                            BBHandler.deleteCourseUser(cmu);
                            delList.get(fullName).add(user);
                        } catch (Exception e) {
                            delList.get(fullName).add(user + " " + NOTDEL);
                            result.put("RESULT", "ERROR PERSISTING");
                        }
                    }
                }
            }
            result.put("RESULT", "SUCCESS");
            app.removeAttribute("COURSEUSERLIST");
        } else {
            result.put("RESULT", "ERROR COURSEUSERLIST");
        }
        request.setAttribute("result", result);
        request.setAttribute("delList", delList);
        request.getRequestDispatcher("/execute/result.jsp").forward(request, response);
    }
}
