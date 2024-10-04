<%@page import="mg.fini_station.pompes.Prelevement"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<%
    // Obtenez la liste des prélèvements depuis l'attribut de la requête
    List<Prelevement> ls_prelevement = (List<Prelevement>) request.getAttribute("prelevements");

    // Vérification de l'attribut 'etat' pour les messages de succès ou d'erreur
    if (request.getAttribute("etat") != null) { 
        String etat = (String) request.getAttribute("etat");
%>
        <script type="text/javascript">
            alert("<%= etat.replace("\"", "\\\"") %>"); // Afficher un message d'alerte en fonction de l'état
        </script>
<% 
    }
%>

<!-- Formulaire pour créer un nouveau prélèvement -->
<form method="POST" action="prelevement">
    <h2>Formulaire de Prélèvement</h2>

    <!-- Champ pour sélectionner un prélèvement existant -->
    <label for="prelevement">Sélectionner un prélèvement</label>
    <select name="id_prelevement" id="prelevement">
        <%
                for (Prelevement prelevement : ls_prelevement) {
        %>
            <option value="<%= prelevement.getIdPrelevement() %>">
                <%= "Prélèvement #" + prelevement.getIdPrelevement() + " - " + prelevement.getDateTime().toString() %>
            </option>
        <% }
        %>
    </select>
    <br><br>

    <!-- Champ pour saisir la date et l'heure du prélèvement -->
    <label for="dt_time">Date et Heure du prélèvement</label>
    <input type="datetime-local" name="dt_time" id="dt_time" required>
    <br><br>

    <!-- Champ pour saisir le montant du prélèvement -->
    <label for="montant">Montant du prélèvement</label>
    <input type="number" name="montant" id="montant" step="0.01" required>
    <br><br>

    <input type="submit" value="Valider le prélèvement">
</form>

<%@ include file="footer.jsp" %>
