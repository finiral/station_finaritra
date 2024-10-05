<%@page import="mg.fini_station.pompes.Prelevement"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<%
    // Obtenez la liste des pr�l�vements depuis l'attribut de la requ�te
    List<Prelevement> ls_prelevement = (List<Prelevement>) request.getAttribute("prelevements");

    // V�rification de l'attribut 'etat' pour les messages de succ�s ou d'erreur
    if (request.getAttribute("etat") != null) { 
        String etat = (String) request.getAttribute("etat");
%>
        <script type="text/javascript">
            alert("<%= etat.replace("\"", "\\\"") %>"); // Afficher un message d'alerte en fonction de l'�tat
        </script>
<% 
    }
%>

<!-- Formulaire pour cr�er un nouveau pr�l�vement -->
<form method="POST" action="encaissement">
    <h2>Formulaire de Encaissement</h2>

    <!-- Champ pour s�lectionner un pr�l�vement existant -->
    <label for="prelevement">S�lectionner un pr�l�vement</label>
    <select name="id_prelevement" id="prelevement">
        <%
                for (Prelevement prelevement : ls_prelevement) {
        %>
            <option value="<%= prelevement.getIdPrelevement() %>">
                <%= "Pr�l�vement #" + prelevement.getIdPrelevement() + " - " + prelevement.getDateTime().toString() %>
            </option>
        <% }
        %>
    </select>
    <br><br>

    <!-- Champ pour saisir la date et l'heure du pr�l�vement -->
    <label for="dt_time">Date et Heure du pr�l�vement</label>
    <input type="datetime-local" name="dt_time" id="dt_time" required>
    <br><br>

    <!-- Champ pour saisir le montant du pr�l�vement -->
    <label for="montant">Montant du pr�l�vement</label>
    <input type="number" name="montant" id="montant" step="0.01" required>
    <br><br>

    <input type="submit" value="Valider le pr�l�vement">
</form>

<%@ include file="footer.jsp" %>
