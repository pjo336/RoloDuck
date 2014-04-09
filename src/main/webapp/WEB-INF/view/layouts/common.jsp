<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="/static/img/favicon.ico?v=4" />
    <title><tiles:insertAttribute name="title" defaultValue="RoloDuck" /></title>
    <tiles:insertAttribute name="html-header" />
</head>
<body>
<tiles:insertAttribute name="top-navbar" />
<tiles:insertAttribute name="exception" />
<tiles:insertAttribute name="header" defaultValue=""/>
<tiles:insertAttribute name="body" />
<tiles:insertAttribute name="custom-styles " defaultValue=""/>
<tiles:insertAttribute name="footer" />
<tiles:insertAttribute name="custom-scripts" defaultValue=""/>
</body>
</html>
