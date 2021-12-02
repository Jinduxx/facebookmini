package controller;

import DOA.PostDatabase;
import dbConnectionProvider.DbConnection;
import model.Post;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;


@WebServlet(name = "PostServlet", value = "/PostServlet")
@MultipartConfig
public class PostServlet extends HttpServlet {

    /**
     * Servlet method for posting
     *
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try (PrintWriter out = response.getWriter();) {
            out.println("<html><body>");
            out.println("<h1>" + "Servlet Registration example" + "</h1>");
            out.println("</body></html>");

            HttpSession httpSession = request.getSession();

            //fetch data from post form
            Part part = request.getPart("file");
            String imageName = part.getSubmittedFileName();
            String title = request.getParameter("title");
            String body = request.getParameter("body");

            //get current user from session
            User currentUser = (User) httpSession.getAttribute("user");
            int userId = currentUser.getId();

            //check if image name not empty
            if (imageName.equals("")) {
                httpSession.setAttribute("message", "Enter a picture");
                response.sendRedirect("home.jsp");
                return;
            }

            //path to store image
            String path = "/Users/decagon/IdeaProjects/FacebookClonable/src/main/webapp/image" + File.separator + imageName;

            InputStream in = part.getInputStream();
            boolean success = uploadFile(in, path);

            if (success) {
                Post post = new Post(title, body, imageName);
                PostDatabase postDatabase = new PostDatabase(DbConnection.getConnection());

                if (postDatabase.createPost(userId, post)) {
                    out.println("File uploaded to this directory " + path);
                    httpSession.setAttribute("message", "File uploaded successfully");
                } else {
                    out.print("500 error");
                    httpSession.setAttribute("message", "Error uploading image to database");
                }
            } else {
                out.print("error");
                httpSession.setAttribute("message", "error uploading file");
            }
            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * method for reading images to a specific path
     *
     * @param in
     * @param path
     * @return boolean
     */
    public boolean uploadFile(InputStream in, String path) {
        boolean test = false;

        try {
            byte[] byt = new byte[in.available()];
            in.read(byt);
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();
            test = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return test;
    }
}
