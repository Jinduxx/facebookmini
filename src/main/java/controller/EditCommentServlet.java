package controller;

import DOA.CommentDatabase;
import dbConnectionProvider.DbConnection;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EditCommentServlet", value = "/EditCommentServlet")
public class EditCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Servlet method for comment editing
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter();) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            //post id from client
            int postId = Integer.parseInt(request.getParameter("postId"));
            String comment = request.getParameter("editedComment");

            //get current user in session
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            //from comment DOA
            CommentDatabase commentDatabase = new CommentDatabase(DbConnection.getConnection());

            if(commentDatabase.editComment(user.getId(), postId, comment)){
                response.getWriter().write("Success editing post");
            }else{
                response.getWriter().write("Error editing post or you don't have access to delete this comment");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
