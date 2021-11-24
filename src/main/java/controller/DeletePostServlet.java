package controller;

import DOA.PostDatabase;
import dbConnectionProvider.DbConnection;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeletePostServlet", value = "/DeletePostServlet")
public class DeletePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Servlet method for post deletion
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            // post id from client
            int postId = Integer.parseInt(request.getParameter("postId"));

            //get current user in session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            //comment DOA
            PostDatabase postDatabase = new PostDatabase(DbConnection.getConnection());

            if(postDatabase.deletePost(user.getId(), postId)){
                response.getWriter().write("Success deleting post");
            }else{
                response.getWriter().write("Failed do delete post or you don't have access to delete this post");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
