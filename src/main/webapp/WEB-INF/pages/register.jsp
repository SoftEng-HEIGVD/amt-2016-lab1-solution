<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ch.heigvd.amt.prl.lab1.models.User" %>

<t:container>
  <jsp:attribute name="inner">
    <h1>Register</h1>

    <t:validationError fieldName="general" errors="${requestScope.errors}"/>

    <div class="row">
      <div class="col-md-3"></div>
      <div class="col-md-6">
        <form method="post" action="${pageContext.request.contextPath}/do/register">
          <div class="form-group">
            <label for="username">Username</label>
            <input id="username" class="form-control" type="text" placeholder="Username" name="username"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}" value="${requestScope.userForm.username}">
            <t:validationError fieldName="username" errors="${requestScope.errors}"/>
          </div>

          <div class="form-group">
            <label for="firstname">Firstname</label>
            <input id="firstname" class="form-control" type="text" placeholder="Firstname" name="firstname"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}" value="${requestScope.userForm.firstname}">
            <t:validationError fieldName="firstname" errors="${requestScope.errors}"/>
          </div>

          <div class="form-group">
            <label for="lastname">Lastname</label>
            <input id="lastname" class="form-control" type="text" placeholder="Lastname" name="lastname"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}" value="${requestScope.userForm.lastname}">
            <t:validationError fieldName="lastname" errors="${requestScope.errors}"/>
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input id="password" class="form-control" type="password" placeholder="Password" name="password"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            <t:validationError fieldName="password" errors="${requestScope.errors}"/>
          </div>

          <div class="form-group">
            <label for="passwordConfirmation">Confirm password</label>
            <input id="passwordConfirmation" class="form-control" type="password"
                   placeholder="Confirm password" name="passwordConfirmation"
                   maxlength="${User.LGTH_MAX_FIELDS}" minlength="${User.LGTH_MIN_FIELDS}">
            <t:validationError fieldName="passwordConfirmation" errors="${requestScope.errors}"/>
          </div>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
      </div>
      <div class="col-md-3"></div>
    </div>
  </jsp:attribute>
</t:container>
