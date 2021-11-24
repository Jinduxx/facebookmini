package controller;

import DOA.UserDatabase;
import dbConnectionProvider.DbConnection;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Servlet method for user login
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession httpSession = request.getSession();
        out.println("<html><body>");
        out.println("<h1>" + "FACEBOOK CLONE" + "</h1>");
        out.println("</body></html>");

        //get request data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //from user DOA
        UserDatabase userDatabase = new UserDatabase(DbConnection.getConnection());
        User user = userDatabase.loginUser(email, password);

        if(user != null){
            httpSession.setAttribute("user", user);
            response.sendRedirect("home.jsp");
        }else{
            httpSession.setAttribute("Registration Error", "User not found, Enter Correct Password or Email");
            response.sendRedirect("index.jsp");
        }
    }
}
