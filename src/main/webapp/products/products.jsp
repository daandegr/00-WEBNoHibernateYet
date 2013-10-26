<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Producten</title>
        <link href="/MVCWebApp_Hibernate/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2>Producten</h2>
        <c:choose>
            <c:when test="${aantalProducten != 0}">
                <!-- Wanneer er gebruikers opgeslagen zijn, worden ze hier getoond -->
                <table class="gebruikers">
                    <tr>
                        <td>
                            <strong>Name</strong>
                        </td>
                        <td>
                            <strong>Price</strong>
                        </td>
                        <td>
                            <strong>UserId</strong>
                        </td>
                        <td></td>
                    </tr>
                <c:forEach var="producten" items="${requestScope.productenLijst}">
                        <!-- Per gebruiker wordt nu een rij aangemaakt met daarin zijn gegevens -->
                        <tr>
                            <td>
                                ${producten.productName}
                            </td>
                            <td>
                                ${producten.price}
                            </td>
                            <td>
                                ${producten.user.getUserId()}
                            </td>
                            <td>
                                <a href="http://localhost:8084/MVCWebApp_Hibernate/products/update?id=${producten.productId}">Wijzig</a> |
                                <a href="javascript:if(confirm('Weet u het zeker dat u deze gebruiker wil verwijderen?'))
                                   window.location='http://localhost:8084/MVCWebApp_Hibernate/products/remove?id=${producten.productId}';">Verwijder</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <!-- Als er geen gebruikers zijn, wordt deze melding getoond -->
                Er zijn geen producten gevonden.
            </c:otherwise>
        </c:choose>
        <p>
            <a href="http://localhost:8084/MVCWebApp_Hibernate/products/new">voeg product toe</a>
        </p>
        <p>
            <a href="http://localhost:8084/MVCWebApp_Hibernate/index">Terug naar de index</a>
        </p>
    </body>
</html>
