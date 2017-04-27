<%--
  User: cirq
  Date: 17-4-11
  Time: 上午12:34
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:setDataSource var = "ds" dataSource="jdbc/BioDB" />
<sql:query sql="select mid from models;" var="model" dataSource="${ds}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>BioModels Database</title>
        <link rel="stylesheet" href="css/mimic.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="img/icon.png">
        <script
            src="http://code.jquery.com/jquery-1.12.4.min.js"
            integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
            crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(function(){
                function footerPosition(){
                    var contentHeight = document.body.scrollHeight,
                        winHeight = window.innerHeight;
                    if(contentHeight < winHeight){
                        $("#foot").addClass("fixed-foot");
                    } else {
                        $("#foot").removeClass("fixed-foot");
                    }
                }
                footerPosition();
                $(window).resize(footerPosition);
            });
        </script>
    </head>
    <body>
    <div class="adjust-center">
        <div class="row:12 align:center">
            <div class="col:2 hidden">@</div>
            <div class="col:8">
                <div>
                    <form action="Upload" enctype="multipart/form-data" method="post">
                         <span class="margin-top:10 margin-bottom:10">
                         <span class="bold">Upload XML File</span>
                         Choose Your XML File of Biomodel Database:
                         <input type="file" name="upload" />
                         <input type="submit" value="SUBMIT" />
                         </span>
                     </form>
                    <h3 class="margin-bottom:20 padding-top:20 border:top bold">List of Curated Models</h3>
                    <table class="align:center">
                        <tr>
                            <th class="width:50">BioModel ID</th>
                            <th class="width:50">View</th>
                        </tr>
                        <c:forEach var="id" items="${model.rows}">
                        <tr>
                            <td>${id.mid}</td>
                            <td><a href="bm/${id.mid}">view model</a></td>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
                <div id="foot">
                    <div class="border:top width:100">
                        <span class="padding-right:10">
                            Designed by <a href="http://cirqqq.xyz">CirQ</a>
                        </span>
                        <span class="padding-left:10">
                            on <a href="http://peterchon.github.io/mimic/">Mimic</a>
                        </span>
                    </div>
                    <div>
                        All models are originated from
                        <a class="underline" href="http://www.ebi.ac.uk/biomodels-main/publmodels">BioModels Database</a>
                    </div>
                </div>
            </div>
            <div class="col:2 hidden">@</div>
        </div>
    </div>
    </body>
</html>
