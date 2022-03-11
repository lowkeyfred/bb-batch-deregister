<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.List" %>
<%@include file="common.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%
    TreeMap<String, String> result = (TreeMap<String, String>) request.getAttribute("result");
    String res = "";
    if (null != result) {
        res = result.get("RESULT");
    } else {
        res = "Error";
    }
%>

<bbNG:genericPage>
    <bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
        <bbNG:breadcrumb>B2 Batch Deregister</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>

    <bbNG:pageHeader>
        <bbNG:pageTitleBar>B2 Batch Deregister</bbNG:pageTitleBar>
    </bbNG:pageHeader>

    <%=res%>
    <%=request.getAttribute("dellist")%>

</bbNG:genericPage>