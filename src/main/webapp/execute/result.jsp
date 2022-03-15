<%@ taglib prefix="bbng" uri="/bbNG" %>
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
    TreeMap<String, List<String>> delList = (TreeMap<String, List<String>>) request.getAttribute("delList");
%>

<bbNG:genericPage>
    <bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
        <bbNG:breadcrumb>B2 Batch Deregister</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>

    <bbNG:pageHeader>
        <bbNG:pageTitleBar>B2 Batch Deregister</bbNG:pageTitleBar>
    </bbNG:pageHeader>

<%--    <%=res%>--%>
    <div class="clearfix">
        <% if (null != delList) { %>

        <ul>
            <%
                for (String course : delList.keySet()) { %>
            <li>
                <i>The following users had been removed from the course: </i><b><%=course%>
            </b>
                <ul><br>
                    <% for (String user : delList.get(course)) { %>
                    <li><%=user%>
                    </li>
                    <%}%>
                </ul>
            </li>
            <br>
            <%}%>
        </ul>
        <%
            } else {
                out.println("Error parsing data.");
            }
        %>
    </div>
    <bbNG:okButton></bbNG:okButton>

</bbNG:genericPage>