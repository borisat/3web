package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("register");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    //sign in
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

//        UserProfile profile = accountService.getUserByLogin(login);
//        if (profile == null || !profile.getPass().equals(pass)) {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null) {
            accountService.addNewUser(new UserProfile(login , pass, login));
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Registered: " + login);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (profile.getLogin().equals(login)){
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Такой пользователь уже существует: " + login);
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }


    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
