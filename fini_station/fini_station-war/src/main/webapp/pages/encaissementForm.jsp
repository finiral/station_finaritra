<%@page import="mg.fini_station.pompes.Prelevement"%>
<%@page import="mg.fini_station.mvt.Encaissement"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<%
    // Obtenez la liste des pr�l�vements depuis l'attribut de la requ�te
    List<Prelevement> ls_prelevement = (List<Prelevement>) request.getAttribute("prelevements");
        List<Encaissement> encaissements = (List<Encaissement>) request.getAttribute("encaissements");

    // V�rification de l'attribut 'etat' pour les messages de succ�s ou d'erreur
%>

<!-- Formulaire pour cr�er un nouveau pr�l�vement -->
<form method="POST" action="encaissement">
    <h2>Formulaire de Encaissement</h2>

    <!-- Champ pour s�lectionner un pr�l�vement existant -->
    <label for="prelevement">Selectionner un prelevement</label>
    <select name="id_prelevement" id="prelevement">
        <%
                for (Prelevement prelevement : ls_prelevement) {
                    if(prelevement.isStatePair()){

        %>
            <option value="<%= prelevement.getIdPrelevement() %>">
                <%= "Prelevement #" + prelevement.getIdPrelevement() + " - " + prelevement.getDateTime().toString() %>
            </option>
        <% } }
        %>
    </select>
    <br><br>

    <!-- Champ pour saisir la date et l'heure du pr�l�vement -->
    <label for="dt_time">Date et Heure de l'encaissement</label>
    <input type="datetime-local" name="dt_time" id="dt_time" required>
    <br><br>

    <!-- Champ pour saisir le montant du prelevement -->
    <label for="montant">Montant de l'encaissement</label>
    <input type="number" name="montant" id="montant" step="0.01" required>
    <br><br>

    <input type="submit" value="Valider l'encaissement'">
</form>

<h2>Tableau des encaissements</h2>
    <table class="table table-hover table-responsive table-bordered">
        <thead>
            <tr>
                <th>Prelevement</th>
                <th>Montant</th>
                <th>Date</th>
                <th>Plus</th>
            </tr>
        </thead>
        <tbody>
            <%
                for(Encaissement p:encaissements){
            %>
            <tr>
                <td><%=p.getPrelevement().getIdPrelevement()%></td>
                <td><%=p.getMontant()%></td>
                <td><%=p.getDt()%></td>
                <td><a href="detailEncaissement?id=<%=p.getIdEncaissement()%>">Details</a></td>
            </tr>
            <% }%>
        </tbody>
    </table>
<%@ include file="footer.jsp" %>
