<%@ taglib prefix="bbng" uri="/bbData" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.List" %>
<%@include file="common.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<bbNG:genericPage>
    <bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
        <bbNG:breadcrumb>B2 Batch Deregister</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>

    <bbNG:pageHeader>
        <bbNG:pageTitleBar>B2 Batch Deregister</bbNG:pageTitleBar>
    </bbNG:pageHeader>
    <bbng:context>


        <div class="clearfix">
            <% TreeMap<String, List<String>> cnuList = (TreeMap<String, List<String>>) request.getAttribute("content");
                if (null != cnuList) { %>

            <ul>
                <%
                    for (String course : cnuList.keySet()) { %>
                <li>
                    <i>The following users will be removed from the course: </i><b><%=course%>
                </b>
                    <ul><br>
                        <% for (String user : cnuList.get(course)) { %>
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

        <bbNG:form action="./confirm" method="post">
            <bbNG:dataCollection>
                <bbNG:step title="Warning:" id="ackWarning">
                    <bbNG:dataElement>
                        <bbNG:checkboxElement value="acked" name="ackCheck" required="true"
                                              optionLabel="I totally understand this is an extremely dangerous operation and want to continue."/>
                    </bbNG:dataElement>
                </bbNG:step>

                <bbNG:stepSubmit title="Submit"/>
            </bbNG:dataCollection>
        </bbNG:form>

    </bbng:context>
</bbNG:genericPage>
