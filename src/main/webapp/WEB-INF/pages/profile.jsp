<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ch.heigvd.amt.prl.lab1.models.User" %>

<t:container>
  <jsp:attribute name="inner">
    <h1>Profile</h1>

    <c:if test="${not empty requestScope.valid}">
      <c:choose>
        <c:when test="${requestScope.valid == 'profile'}">
          <div class="alert alert-success" role="alert">Your details have been successfully updated.</div>
        </c:when>
        <c:when test="${requestScope.valid == 'password'}">
          <div class="alert alert-success" role="alert">Your password have been successfully updated.</div>
        </c:when>
      </c:choose>
    </c:if>

    <t:validationError fieldName="general" errors="${requestScope.errors}"/>

    <div class="row">
      <div class="col-md-1"></div>
      <div class="col-md-5">
        <h2>Your details</h2>

        <form method="post" action="${pageContext.request.contextPath}/do/profile">
          <input type="hidden" name="updateType" value="profile" />

          <div class="form-group">
            <label for="username">Username</label>
            <input id="username" class="form-control" type="text" placeholder="Username" name="username"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}" 
                   value="${sessionScope.user.username}">
            <t:validationError fieldName="username" errors="${requestScope.profileErrors}"/>
          </div>

          <div class="form-group">
            <label for="firstname">Firstname</label>
            <input id="firstname" class="form-control" type="text" placeholder="Firstname" name="firstname"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}"
                   value="${sessionScope.user.firstname}">
            <t:validationError fieldName="firstname" errors="${requestScope.profileErrors}"/>
          </div>

          <div class="form-group">
            <label for="lastname">Lastname</label>
            <input id="lastname" class="form-control" type="text" placeholder="Lastname" name="lastname"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}"
                   value="${sessionScope.user.lastname}">
            <t:validationError fieldName="lastname" errors="${requestScope.profileErrors}"/>
          </div>

          <t:validationError fieldName="profileGeneral" errors="${requestScope.profileErrors}"/>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>    

      </div>

      <div class="col-md-5">
        <h2>Change your password</h2>

        <form method="post" action="${pageContext.request.contextPath}/do/profile">
          <input type="hidden" name="updateType" value="password" />

          <div class="form-group">
            <label for="password">Password</label>
            <input id="password" class="form-control" type="password" placeholder="Password" name="password"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            <t:validationError fieldName="password" errors="${requestScope.passwordErrors}"/>
          </div>

          <div class="form-group">
            <label for="passwordConfirmation">Confirm password</label>
            <input id="passwordConfirmation" class="form-control" type="password"
                   placeholder="Confirm password" name="passwordConfirmation"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            <t:validationError fieldName="passwordConfirmation" errors="${requestScope.passwordErrors}"/>
          </div>

          <t:validationError fieldName="passwordGeneral" errors="${requestScope.passwordErrors}"/>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
      </div>           
      <div class="col-md-1"></div>            
    </div>
  </jsp:attribute>
</t:container>
