package controller;

import DOA.UserDatabase;
import dbConnectionProvider.DbConnection;
import model.User;
import utils.PasswordHashing;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignUpServlet", value = "/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Servlet method for user registration
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "Servlet Sign up" + "</h1>");
        out.println("</body></html>");

        HttpSession httpSession = request.getSession();



        //fetch data from registration page
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        //data check

//        if (firstname.length() < 2) {
//            httpSession.setAttribute("Registration Error", " firstname cannot be less than 2 character long");
//            response.sendRedirect("index.jsp");
//            return;
//        }
//
//        if (lastname.length() < 2) {
//            httpSession.setAttribute("Registration Error", "lastname cannot be less than 2 character long");
//            response.sendRedirect("index.jsp");
//            return;
//        }
//
//
//        if(!email.contains("@")){
//            httpSession.setAttribute("Registration Error", "email is invalid");
//            response.sendRedirect("index.jsp");
//            return;
//        }
//        if(password.length() < 7){
//            httpSession.setAttribute("Registration Error", "password cannot be less than 6 character long");
//            response.sendRedirect("index.jsp");
//            return;
//        }
        //================================== end of validation ====================================//

        //Password encryption
        password = PasswordHashing.encryptPassword(password);

        User userModel = new User(firstname,lastname,email,password);

        //from user DataBase
        UserDatabase regUser = new UserDatabase(DbConnection.getConnection());

//        if (!regUser.registerUser(userModel)) {
//            String errorMessage = "failed";
//            httpSession.setAttribute("Registration Error", errorMessage);
//        }else{
//            httpSession.setAttribute("Registration Error", "successfully registered");
//
//        }
        regUser.registerUser(userModel);

        response.sendRedirect("index.jsp");

    }
}
