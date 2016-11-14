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

    <t:validationError fieldName="general" errors="${requestScope.errors}"/>

    <c:if test="${not empty messageService && messageService.hasMessages()}">
      <div class="alert alert-success" role="alert">${messageService.pop()}</div>
    </c:if>
    
    <div class="row">
      <div class="col-md-3"></div>
      <div class="col-md-6">
        <form method="post" action="${pageContext.request.contextPath}/do/login">
          <div class="form-group">
            <label for="username">Username</label>
            <input id="username" class="form-control" type="text" placeholder="Username" name="username"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}" value="${requestScope.username}">
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input id="password" class="form-control" type="password" placeholder="Password" name="password"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
          </div>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>

        <div id="register">
          <a href="${pageContext.request.contextPath}/pages/register">No account yet? Register.</a>
        </div>
      </div>
      <div class="col-md-3"></div>
    </div>
  </jsp:attribute>
</t:container>
