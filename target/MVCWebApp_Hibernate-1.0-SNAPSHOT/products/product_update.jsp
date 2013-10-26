<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>${paginaTitel}</title>
        <link href="/MVCWebApp_Hibernate/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2>${paginaTitel}</h2>
        <c:if test="${not empty foutMelding}">
            <!-- Mochten er foutmeldingen zijn, dan worden ze hier getoond -->
            <p>${foutMelding}</p>
        </c:if>
        <c:choose>
            <c:when test="${empty id}">
                <!-- Als er geen id is meegegeven, ga je een gebruiker toevoegen -->
                <form id="newProduct" action="create" method="post">
            </c:when>
            <c:otherwise>
                <!-- Anders ga je een gebruiker wijzigen -->
                <form id="updateProduct" action="doUpdate" method="post">
            </c:otherwise>
        </c:choose>
            <p>
                <c:if test="${not empty id}">
                    <!-- Het id wordt meegestuurd, om te bepalen welke gebruiker je gaat wijzigen -->
                    <input type="hidden" name="id" id="id" value="${id}"></input>
                </c:if>
                <table border="0">
                    <tr>
                        <td>
                            <label for="name">Product naam</label>
                        </td>
                        <td>
                            <input type="textfield" id="name" name="name" value="${name}"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="price">prijs</label>
                        </td>
                        <td>
                            <input type="textfield" id="price" name="price" value="${price}"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="userId">userId</label>
                        </td>
                        <td>
                            <input type="textfield" id="userId" name="userId" value="${userId}"></input>
                        </td>
                    </tr>
                </table>
            </p>
            <p>
                <input class="submit" type="submit" value="Verzenden">
            </p>
        </form>
    </body>
</html>