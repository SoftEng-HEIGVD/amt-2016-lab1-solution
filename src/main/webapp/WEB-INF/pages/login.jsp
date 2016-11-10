<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ch.heigvd.amt.prl.lab1.models.User" %>

<t:container>
  <jsp:attribute name="head">
    <link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet">
  </jsp:attribute>
  <jsp:attribute name="inner">
    <h1>Login</h1>
    <form method="post" action="${pageContext.request.contextPath}/do/login">
      <div class="form-group">
        <label for="username">Username</label>
        <input id="username" class="form-control" type="text" placeholder="Username" name="username"
          maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input id="password" class="form-control" type="password" placeholder="Password" name="password"
          maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
      </div>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">
          ${error}
        </div>
      </c:if>

      <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    <div id="register">
      <a href="${pageContext.request.contextPath}/pages/register">No account yet ? Register.</a>
    </div>
  </jsp:attribute>
</t:container>
