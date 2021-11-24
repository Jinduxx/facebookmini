package controller;

import DOA.CommentDatabase;
import dbConnectionProvider.DbConnection;
import model.Comment;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CommentServlet", value = "/CommentServlet")
public class CommentServlet extends HttpServlet {

    /**
     * Servlet method for viewing comment
     * @param request
     * @param response
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()){

            //fetch data from post form
            int postId = Integer.parseInt(request.getParameter("postId"));

            //get current user
            HttpSession httpSession = request.getSession();
            User currentUser = (User) httpSession.getAttribute("user");
            int userId = currentUser.getId();

            //from the comment DOA
            CommentDatabase commentDatabaseDatabase = new CommentDatabase(DbConnection.getConnection());
            List<Comment> comments = commentDatabaseDatabase.getComments(postId);

            if(!comments.isEmpty()){
                out.println("comments retrieved successfully");
                httpSession.setAttribute("message", "successful");
                httpSession.setAttribute("user",currentUser);
                httpSession.setAttribute("comments",comments);
            }else{
                out.print("404 not found");
                httpSession.setAttribute("user",currentUser);
                httpSession.setAttribute("message", "No comment associated with post");
            }

            response.sendRedirect("comment.jsp");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Servlet method for comment creation
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter();){
            out.println("<html><body>");
            out.println("<h1>" + "Servlet Registration example" + "</h1>");
            out.println("</body></html>");

            //fetch data from post form
            String comment = request.getParameter("comment");
            int postId = Integer.parseInt(request.getParameter("postId"));

            //get current user
            HttpSession httpSession = request.getSession();
            User currentUser = (User) httpSession.getAttribute("user");
            int userId = currentUser.getId();

            //from the comment DOA
            CommentDatabase commentDatabaseDatabase = new CommentDatabase(DbConnection.getConnection());

            if(commentDatabaseDatabase.createComment(userId,postId,comment)){
               out.println("File uploaded to this directory");
               httpSession.setAttribute("message", "successful");
               httpSession.setAttribute("user",currentUser);
            }else{
               out.print("500 error");
               httpSession.setAttribute("user",currentUser);
               httpSession.setAttribute("message", "Error posting comment");
            }

            response.sendRedirect("home.jsp");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
