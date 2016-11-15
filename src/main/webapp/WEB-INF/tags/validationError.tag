<%@tag description="Represent a validation error" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="errors" fragment="false" type="ch.heigvd.amt.prl.lab1.dto.ErrorDto" %>
<%@attribute name="fieldName" fragment="false" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:if test="${not empty errors && errors.hasErrors(fieldName)}">
  <div class="alert alert-danger" role="alert">
    <ul>
      <c:forEach var="message" items="${errors.getMessages(fieldName)}">
        <li>${message}</li>
      </c:forEach>
    </ul>
  </div>
</c:if>
