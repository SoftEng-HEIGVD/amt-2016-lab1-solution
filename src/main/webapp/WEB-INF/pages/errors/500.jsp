<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:container>
    <jsp:attribute name="inner">
        <h1>Internal server error</h1>
        <p>
            Oops! Looks like the server encounters some difficulties right now...
            <br>You can try to come back later.
        </p>
        <a href="${pageContext.request.contextPath}/">Try to go back</a>
    </jsp:attribute>
</t:container>
