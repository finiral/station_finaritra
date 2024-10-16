<%@page import="mg.fini_station.pompes.Prelevement"%>
<%@page import="mg.fini_station.mvt.Encaissement"%>
<%@page import="java.util.List"%>
<%@page import="client.Client"%>
<%@ include file="header.jsp" %>
<% Encaissement e=(Encaissement)request.getAttribute("encaissement");
    List<Client> clients=(List<Client>)request.getAttribute("clients");
%>
<h2>Details encaissement</h2>
    <table class="table table-hover table-responsive table-bordered">
        <thead>
            <tr>
                <th>Montant encaissement</th>
                <th>Montant avoir</th>
                <th>Montant avoir payé</th>
                <th>Difference</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><%=e.getMontant()%></td>
                <td><%=e.getAvoir()%></td>
                <td><%=e.getAvoirPaie()%></td>
                <td><%=e.getAvoir()-e.getAvoirPaie()%></td>
            </tr>
        </tbody>
    </table>
    <form method="POST" action="">
    <h2>Insertion avoir</h2>
    <label for="">Client</label>
    <select name="id_client" id="id_client">
        <% for(Client client:clients){ %>
        <option value="<%=client.getId()%>"><%=client.getNom()%></option>
        <% }%>
    </select>
    <br>
    <label for="">Date Echeance</label>
    <input type="date" name="dt" required>
    <input type="hidden" name="id_encaissement" value="<%=e.getIdEncaissement()%>">
    <br>
    <label for="">Montant</label>
    <input type="number" name="montant" step="0.1" required>
    <br>
    <input type="submit" value="valider">
</form>
<%@ include file="footer.jsp" %>
