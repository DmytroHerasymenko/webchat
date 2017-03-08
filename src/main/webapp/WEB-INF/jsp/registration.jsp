<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>registration</title>
</head>
<body>
${requestScope.error}
<sf:form modelAttribute="registration">
    <fieldset>
        <div>
            <label for="name">
                <s:message code="reg.name"/>
            </label>
            <sf:input path="name"/>
            <sf:errors path="name" cssClass="error"/>
        </div>
        <div>
            <label for="login">
                <s:message code="reg.login"/>
            </label>
            <sf:input path="login"/>
            <sf:errors path="login" cssClass="error"/>
        </div>
        <div>
            <label for="password">
                <s:message code="reg.password"/>
            </label>
            <sf:input path="password"/>
            <sf:errors path="password" cssClass="error"/>
        </div>
        <div>
            <input type="submit" value="save"/>
        </div>
    </fieldset>
</sf:form>
</body>
</html>
