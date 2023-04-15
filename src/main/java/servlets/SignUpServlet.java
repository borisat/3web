package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.DBException;
import dbService.DBService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;
    private final DBService dbService;

    public SignUpServlet(AccountService accountService, DBService dbService) {
        this.accountService = accountService;
        this.dbService = dbService;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (login == null || pass == null || login.equals("") || pass.equals("")) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Введите корректный логин и пароль");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if (dbService.getUserID(login) > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println("Логин уже существует, введите другой логин");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        accountService.addNewUser(new UserProfile(login, pass), dbService);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Registered: " + login);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

