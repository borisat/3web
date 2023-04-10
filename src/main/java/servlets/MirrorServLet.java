package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/mirror")
public class MirrorServLet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String key = request.getParameter("key");
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        if (key != null) {
            printWriter.write(key);
        } else {
            printWriter.write("Key is null");
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

//    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
//        Map<String, Object> pageVariables = new HashMap<>();
//        pageVariables.put("method", request.getMethod());
//        pageVariables.put("URL", request.getRequestURL().toString());
//        pageVariables.put("pathInfo", request.getPathInfo());
//        pageVariables.put("sessionId", request.getSession().getId());
//        pageVariables.put("parameters", request.getParameterMap().toString());
//        pageVariables.put("mirror", request.getParameterMap().toString());
//
//        return pageVariables;
//    }

}
