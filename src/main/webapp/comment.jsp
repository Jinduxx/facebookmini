<%@ page import="model.User" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="DOA.PostDatabase" %>
<%@ page import="dbConnectionProvider.DbConnection" %>
<%@ page import="DOA.CommentDatabase" %><%--
  Created by IntelliJ IDEA.
  User: protek
  Date: 5/4/21
  Time: 6:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Comment Facebook Post</title>

    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <title>timeline</title>
    <!-- JQuery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
            crossorigin="anonymous"
    />
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"
    ></script>
    <script src="${pageContext.request.contextPath}/js/comment.js"></script>
</head>
<body>
<%
    String query = request.getQueryString();
    int postId = Integer.parseInt(query.substring(query.indexOf("=")+1));
    CommentDatabase commentDatabase = new CommentDatabase(DbConnection.getConnection());
    List<Comment> commentList = commentDatabase.getComments(postId);

    User user = (User) session.getAttribute("user");
    if(user == null){
        session.setAttribute("Registration Error", "!!!Please Login first");
        response.sendRedirect("index.jsp");
    }
%>

<nav style="background: #3b5998" class="navbar navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="#" style="color:#fff;"><h1>Facebook</h1></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" style="color: #fff;" href="${pageContext.request.contextPath}/home.jsp"><h3>Home</h3></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<section style="margin: 60px auto; width: 60%; border: 2px solid #3b5998; padding: 15px">
    <%if(commentList.size() != 0){%>
             <img src="/image/<%=commentList.get(0).getPostImage()%>" width="100%" height="60%"/>
             <h3>Post Title: <%=commentList.get(0).getTitle() %></h3>
             <%for (Comment comment:commentList) {%>
                <hr/>
                <p><%=comment.getComment()%> </p>
                <p><em>Edit Comment</em></p>
                <textarea class="edit-comment"
                          placeholder="Edit comment here...">
                </textarea>

                <button onclick="del(<%=postId%>, <%=comment.getUserId()%>)" class="btn btn-primary btn-md mt-3 btn-block"
                    <%
                        if(comment.getUserId() != user.getId()){%>
                         disabled
                    <%}%>>
                    Delete
                </button>
                <button onclick="edit(<%=postId%>, <%=comment.getUserId()%>)" class="btn btn-primary btn-md mt-3 btn-block"
                    <%
                        if(comment.getUserId() != user.getId()){%>
                    disabled
                    <%}%>>
                    Edit
                </button>
                <hr/>
            <%}
        }else{ %>
            <h1>No Comments !!!!</h1>
        <%}
    %>
</section>
</script>
</body>
</html>
