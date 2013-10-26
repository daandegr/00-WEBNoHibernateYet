<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="windows-1252"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Klantensysteem</title>
        <link href="/MVCWebApp_Hibernate/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2>Welkom</h2>
          <h2>Zorg dat de opgevoerde gebruikers in een DB komen!</h2>
            <h2>Zodat ze niet na iedere sessie verdwijnen, doe dit met hibernate</h2>
        <h3>Menu</h3>
        <ul>
            <li>
                <form method="post" action="readall">      
                    <td><input type="submit" value="gebruikers"/></td>
                </form>
                <form method="post" action="products">      
                    <td><input type="submit" value="Producten"/></td>
                </form>
            </li>
        </ul>
    </body>
</html>
