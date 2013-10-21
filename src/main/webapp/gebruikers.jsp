<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Gebruikers</title>
        <link href="/MVCWebApp_Hibernate/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2>Gebruikers</h2>
        <c:choose>
            <c:when test="${aantalGebruikers != 0}">
                <!-- Wanneer er gebruikers opgeslagen zijn, worden ze hier getoond -->
                <table class="gebruikers">
                    <tr>
                        <td>
                            <strong>Voornaam</strong>
                        </td>
                        <td>
                            <strong>Achternaam</strong>
                        </td>
                        <td>
                            <strong>email</strong>
                        </td>
                        <td></td>
                    </tr>
                <c:forEach var="tempGebruiker" items="${requestScope.gebruikersUitSessie}">
                        <!-- Per gebruiker wordt nu een rij aangemaakt met daarin zijn gegevens -->
                        <tr>
                            <td>
                                ${tempGebruiker.firstName}
                            </td>
                            <td>
                                ${tempGebruiker.lastName}
                            </td>
                            <td>
                                ${tempGebruiker.email}
                            </td>
                            <td>
                                <a href="gebruikers/wijzig?id=${tempGebruiker.userId}">Wijzig</a> |
                                <a href="javascript:if(confirm('Weet u het zeker dat u deze gebruiker wil verwijderen?'))
                                   window.location='gebruikers/verwijder?id=${tempGebruiker.userId}';">Verwijder</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <!-- Als er geen gebruikers zijn, wordt deze melding getoond -->
                Er zijn geen gebruikers gevonden.
            </c:otherwise>
        </c:choose>
        <p>
            <a href="gebruikers/nieuw">Maak nieuwe gebruiker aan</a>
        </p>
        <p>
            <a href="index">Terug naar de index</a>
        </p>
    </body>
</html>
