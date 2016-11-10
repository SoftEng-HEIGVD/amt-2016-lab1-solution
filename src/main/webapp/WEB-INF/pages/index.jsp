<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base>
  <jsp:attribute name="head">
    <link href="${pageContext.request.contextPath}/static/css/index.css" rel="stylesheet">
  </jsp:attribute>

  <jsp:attribute name="container">
    <header id="top" class="header">
      <div class="text-vertical-center">
        <h1>AMT - Demo</h1>
        <h3>Welcome to the demo app !</h3>
        <br>
        <a href="https://github.com/SoftEng-HEIGVD/amt-2016-lab1-solution" target="_blank" class="btn btn-dark btn-lg">Find Out More</a>
      </div>
    </header>
  </jsp:attribute>
</t:base>

