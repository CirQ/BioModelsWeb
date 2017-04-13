<%--
  User: cirq
  Date: 2017-04-11
  Time: 10:51:19
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:setDataSource var="ds" dataSource="jdbc/BioDB" />
<sql:query sql="select author, title from books;" var="rs" dataSource="${ds}" />

<html>
    <head>
        <title>Book Query for Database Connection Pooling</title>
    </head>

    <body>
        <h2>Query Results</h2>
        <table>
            <c:forEach var="row" items="${rs.rows}">
            <tr>
                <td>${row.author}</td><td>${row.title}</td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>