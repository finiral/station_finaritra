<%@ include file="header.jsp" %>
<%@page import="mg.fini_station.stock.*"%>
<%@page import="java.util.List"%>
<%
    // Obtenez la liste des pr�l�vements depuis l'attribut de la requ�te
    List<Reservoir> ls_reservoir = (List<Reservoir>) request.getAttribute("ls_reservoir");
%>

<form method="POST" action="reservoir">
    <h2>ACHAT LIQUIDE RESERVOIR</h2>
    <label for="">Reservoir</label>
    <select name="id_reservoir" id="">
    <%
        for(Reservoir r:ls_reservoir){
            %><option value="<%=r.getIdReservoir()%>"><%=r.getIdReservoir()%> - <%=r.getTypeLiquide().getNomLiquide()%></option>
    <%    }
    %>
    </select>
    <br>
    <label for="">Date</label>
    <input type="datetime-local" name="dt" required>
    <br>
    <label for="">Quantite achete</label>
    <input type="number" name="qte" step="0.1" required>
    <br>
    <input type="submit" value="valider">
</form>

<%@ include file="footer.jsp" %>
