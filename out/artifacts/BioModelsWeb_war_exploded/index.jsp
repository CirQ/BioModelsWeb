<%--
  User: cirq
  Date: 17-4-11
  Time: 上午12:34
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:setDataSource var="ds" dataSource="jdbc/BioDB" />
<sql:query sql="select * from species;" var="rs" dataSource="${ds}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>BioModels Database For Species</title>
        <link rel="stylesheet" href="css/mimic.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="img/icon.png">
    </head>
    <body>
    <div class="adjust-center">
        <div class="row:12 align:center">
            <div class="col:2 hidden">@</div>
            <div class="col:8">
                <form action="/SpeciesXML/Upload" enctype="multipart/form-data" method="post">
                    <span class="margin-top:10 margin-bottom:10">
                        <span class="bold">Upload XML File</span>
                        Choose Your XML File of Biomodel Species Information:
                        <input type="file" name="upload" />
                        <input type="submit" value="SUBMIT" />
                    </span>
                </form>
                <h3 class="margin-bottom:20 padding-top:20 border:top bold">Query Results</h3>
                <table class="align:center">
                    <tr>
                        <th class="width:17">id</th>
                        <th class="width:12">initial_amount</th>
                        <th class="width:24">has_only_substance_units</th>
                        <th class="width:17">name</th>
                        <th class="width:14">metaid</th>
                        <th class="width:16">campartment</th>
                    </tr>
                    <c:forEach var="row" items="${rs.rows}">
                    <tr>
                        <td>${row.id}</td>
                        <td>${row.initial_amount}</td>
                        <td>${row.has_only_substance_units}</td>
                        <td>${row.id}</td>
                        <td>${row.metaid}</td>
                        <td>${row.compartment}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col:2 hidden">@</div>
        </div>
    </div>
    </body>
</html>
