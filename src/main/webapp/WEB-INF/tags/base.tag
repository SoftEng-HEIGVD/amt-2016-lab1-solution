<%@tag description="Overall page template" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true" %>
<%@attribute name="container" fragment="true" %>
<%@attribute name="scripts" fragment="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

  <head>
    <meta charset="utf-8">
    <title>AMT project</title>

    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
      crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/base.css">

    <jsp:invoke fragment="head"/>
  </head>

  <body>
    <div class="winter-background">
      <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
          <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">AMT Demo</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-left">
              <c:if test="${not empty sessionScope.user}">
                <li><a href="${pageContext.request.contextPath}/pages/profile">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/pages/users">Users</a></li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <c:choose>
                <c:when test="${not empty sessionScope.user}">
                  <li>
                    <a href="${pageContext.request.contextPath}/do/logout">Logout</a>
                  </li>
                </c:when>
                <c:otherwise>
                  <li>
                    <a href="${pageContext.request.contextPath}/pages/login">Login</a>
                  </li>
                  <li>
                    <a href="${pageContext.request.contextPath}/pages/register">Register</a>
                  </li>
                </c:otherwise>
              </c:choose>
            </ul>
          </div>
        </div>
      </nav>

      <jsp:invoke fragment="container"/>
      <jsp:invoke fragment="scripts"/>
    </div>
  </body>
</html>
