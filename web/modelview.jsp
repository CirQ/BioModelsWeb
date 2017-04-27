<%--
  User: cirq
  Date: 2017-04-27
  Time: 15:01:36
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:setDataSource var = "ds" dataSource="jdbc/BioDB" />
<sql:query var="reactions" dataSource="${ds}">
    SELECT * FROM reaction WHERE mid='${model}';
</sql:query>
<sql:query var="speciess" dataSource="${ds}">
    SELECT * FROM species WHERE mid='${model}';
</sql:query>

<jsp:useBean id="react" class="mypkg.Reaction" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>Model View - ${model}</title>
        <link rel="stylesheet" href="/BioModels/css/mimic.css">
        <link rel="stylesheet" href="/BioModels/css/style.css">
        <link rel="shortcut icon" href="/BioModels/img/icon.png">
    </head>
    <body>
    <div class="adjust-center">
        <div class="row:12 align:center">
            <div class="col:2 hidden">@</div>
            <div class="col:8">
                <h3 class="bold">Relating Reactions</h3>
                <table class="align:center">
                    <tr>
                        <th class="width:20">Reacton_ID</th>
                        <th class="width:30">Name</th>
                        <th class="width:25">Reactants</th>
                        <th class="width:25">Products</th>
                    </tr>
                    <c:forEach var="reaction" items="${reactions.rows}">
                        <tr>
                            <jsp:setProperty name="react" property="reactants" value="${reaction.reactants}" />
                            <jsp:setProperty name="react" property="products" value="${reaction.products}" />

                            <td class="b">${reaction.rid}</td>
                            <td class="b">${reaction.name}</td>
                            <td class="b"><%=react.getReactants()%></td>
                            <td class="b"><%=react.getProducts()%></td>
                        </tr>
                    </c:forEach>
                </table>

                <h3 class="margin-top:20 bold">List of Species</h3>
                <table class="align:center">
                    <tr>
                        <th class="width:30">Species_ID</th>
                        <th class="width:15">Initial_Amount</th>
                        <th class="width:30">Name</th>
                        <th class="width:25">Compartment</th>
                    </tr>
                    <c:forEach var="species" items="${speciess.rows}">
                        <tr>
                            <td class="b">${species.sid}</td>
                            <td class="b">${species.initial_amount}</td>
                            <td class="b">${species.name}</td>
                            <td class="b">${species.compartment}</td>
                        </tr>
                    </c:forEach>
                </table>

                <h3 class="margin-top:20 bold">Graphic View</h3>
            </div>
            <div class="col:2 hidden">@</div>
        </div>
    </div>
    </body>
</html>
