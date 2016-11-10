<%@tag description="Base with container template" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true" %>
<%@attribute name="inner" fragment="true" %>
<%@attribute name="scripts" fragment="true" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base>
  <jsp:attribute name="head">
    <link href="${pageContext.request.contextPath}/static/css/container.css" rel="stylesheet">
    <jsp:invoke fragment="head"/>
  </jsp:attribute>
  <jsp:attribute name="scripts">
    <jsp:invoke fragment="scripts"/>
  </jsp:attribute>
  <jsp:attribute name="container">
    <div id="container" class="container">
      <jsp:invoke fragment="inner"/>
    </div>
  </jsp:attribute>
</t:base>
