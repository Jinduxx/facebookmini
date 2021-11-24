package controller;

import DOA.PostDatabase;
import dbConnectionProvider.DbConnection;
import model.Post;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "UpdatePostServlet", value = "/UpdatePostServlet")
public class UpdatePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>" + "update post" + "</h1>");
            out.println("</body></html>");
            HttpSession httpSession = request.getSession();

            //requests from the client
            String title = request.getParameter("title");
            String body = request.getParameter("body");
            int postId = Integer.parseInt(request.getParameter("postId"));

            Post post = new Post(title, body);

            //from post database
            PostDatabase postDatabase = new PostDatabase(DbConnection.getConnection());

            if(postDatabase.updatePost(postId, post)){
                out.println("File uploaded to this directory");
                httpSession.setAttribute("message", "successful");
            }else{
                out.print("500 error");
                httpSession.setAttribute("message", "Error uploading data");
            }

            response.sendRedirect("home.jsp");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
