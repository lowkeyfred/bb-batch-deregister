<%@ taglib prefix="BBng" uri="/bbNG" %>
<%@include file="common.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<bbNG:genericPage>
    <bbNG:breadcrumbBar environment="sys_admin" navItem="admin_main">
        <bbNG:breadcrumb>B2 Batch Deregister</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>

    <bbNG:pageHeader>
        <bbNG:pageTitleBar>B2 Batch Deregister</bbNG:pageTitleBar>
    </bbNG:pageHeader>

    <% if (null != request.getAttribute("err")) {
        String errMsg = (String) request.getAttribute("err");
        %>
        <p style="color:red"> <%=errMsg%> </p>
    <% } %>

    <bbNG:form action="./upload" method="post" enctype="multipart/form-data">
        <bbNG:dataCollection>
            <bbNG:step title="Warning:" id="ackWarning">
                <bbNG:dataElement>
                    <bbNG:checkboxElement value="acked" name="ackCheck" required="true"
                                          optionLabel="I totally understand this is an extremely dangerous operation and want to continue."/>
                </bbNG:dataElement>
            </bbNG:step>
            <bbNG:step title="Please choose the Excel file contains the user and course info:" id="filePicker">
                <bbNG:dataElement>
                    <bbNG:filePicker baseElementName="cuFile" var="excelFile" mode="LOCAL_FILE_ONLY" required="true"/>
                </bbNG:dataElement>
            </bbNG:step>
            <bbNG:stepSubmit title="Submit">
                <bbNG:stepSubmitButton label="Submit" onClick="fileValidation(event)" primary="true"></bbNG:stepSubmitButton>
                <bbNG:stepSubmitButton label="Cancel" onClick="go_parent()"></bbNG:stepSubmitButton>
            </bbNG:stepSubmit>
        </bbNG:dataCollection>
    </bbNG:form>

    <bbNG:jsBlock>
        <script type="text/javascript">
            jQuery(function () {
                let ackCheck = jQuery("#ackCheck");
                if (true === ackCheck.is(":checked")) {
                    page.steps.hideShowAndRenumber(page.steps.SHOW, ["filePicker"]);
                } else {
                    page.steps.hideShowAndRenumber(page.steps.HIDE, ["filePicker"]);
                }

                ackCheck.change(function () {
                    if (true === ackCheck.is(":checked")) {
                        page.steps.hideShowAndRenumber(page.steps.SHOW, ["filePicker"]);
                    } else {
                        page.steps.hideShowAndRenumber(page.steps.HIDE, ["filePicker"]);
                    }
                })
            })

            function fileValidation(event) {
                let fileInput =
                    jQuery("#cuFile_chooseLocalFile");

                let filePath = fileInput.val();

                // Allowing file type
                let allowedExtensions =
                    /(\.csv)$/i;

                if (!allowedExtensions.exec(filePath)) {
                    alert('auto exported csv file only.');
                    event.preventDefault();
                    return false;
                }
            }

            function go_parent() {
                document.location = "/webapps/portal/execute/tabs/tabAction?tabType=admin";
            }

        </script>
    </bbNG:jsBlock>

</bbNG:genericPage>