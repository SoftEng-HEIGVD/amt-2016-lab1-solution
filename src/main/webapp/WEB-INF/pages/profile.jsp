<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ch.heigvd.amt.prl.lab1.models.User" %>

<t:container>
  <jsp:attribute name="inner">
    <h1>Profile</h1>

    <c:if test="${not empty error}">
      <div class="alert alert-danger">
        ${error}
      </div>
    </c:if>
    
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <h2>Your details</h2>
          
          <form method="post" action="${pageContext.request.contextPath}/do/profile">
            <input type="hidden" name="updateType" value="profile" />
            
            <div class="form-group">
              <label for="username">Username</label>
              <input id="username" class="form-control" type="text" placeholder="Username" name="username"
                maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            </div>

            <div class="form-group">
              <label for="firstname">Firstname</label>
              <input id="firstname" class="form-control" type="text" placeholder="Firstname" name="firstname"
                maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            </div>

            <div class="form-group">
              <label for="lastname">Lastname</label>
              <input id="lastname" class="form-control" type="text" placeholder="Lastname" name="lastname"
                maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            </div>

            <c:if test="${not empty profileErrors}">
              <div class="alert alert-danger">
                <c:forEach var="message" items="errors">
                  <p>${message}</p>
                </c:forEach>
              </div>
            </c:if>

            <button type="submit" class="btn btn-primary">Submit</button>
          </form>    
          
        </div>
            
        <div class="col-md-6">
          <h2>Change your password</h2>
          
          <form method="post" action="${pageContext.request.contextPath}/do/profile">
            <input type="hidden" name="updateType" value="password" />
            
            <div class="form-group">
              <label for="password">Password</label>
              <input id="password" class="form-control" type="password" placeholder="Password" name="password"
                maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            </div>

            <div class="form-group">
              <label for="passwordConfirmation">Confirm password</label>
              <input id="passwordConfirmation" class="form-control" type="password"
                placeholder="Confirm password" name="passwordConfirmation"
                maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            </div>

            <c:if test="${not empty passwordErrors}">
              <div class="alert alert-danger">
                <c:forEach var="message" items="errors">
                  <p>${message}</p>
                </c:forEach>
              </div>
            </c:if>

            <button type="submit" class="btn btn-primary">Submit</button>
          </form>
        </div>            
      </div>
    </div>
  </jsp:attribute>
</t:container>
