<%@page import="mg.fini_station.pompes.Prelevement"%>
<%@page import="mg.fini_station.mvt.Encaissement"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<% Encaissement e=(Encaissement)request.getAttribute("encaissement");%>
<h2>Details encaissement</h2>
    <table class="table table-hover table-responsive table-bordered">
        <thead>
            <tr>
                <th>Montant encaissement</th>
                <th>Montant censer etre encaisser (prelevement)</th>
                <th>Difference</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><%=e.getMontant()%></td>
                <td><%=e.getPrelevement().getDifferenceCompteurVola()%></td>
                <td><%=e.getDifferencePrelevementEtEncaisser()%></td>
            </tr>
        </tbody>
    </table>
    <form method="POST" action="">
    <h2>Insertion avoir</h2>
    <label for="">Client</label>
    <input type="text" name="client" >
    <br>
    <label for="">Date Echeance</label>
    <input type="date" name="dt" required>
    <br>
    <label for="">Montant</label>
    <input type="number" name="montant" step="0.1" required>
    <br>
    <input type="submit" value="valider">
</form>
<%@ include file="footer.jsp" %>
