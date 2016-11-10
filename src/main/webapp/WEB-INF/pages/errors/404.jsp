<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:container>
    <jsp:attribute name="inner">
        <h1>Page not found</h1>
        <p>Oops! Looks like the page you're looking for doesn't exist...</p>
        <a href="${pageContext.request.contextPath}/">Go back</a>
    </jsp:attribute>
</t:container>
