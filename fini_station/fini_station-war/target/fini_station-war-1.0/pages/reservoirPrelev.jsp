<%@ include file="header.jsp" %>
<%@page import="mg.fini_station.stock.*"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<%
    // Obtenez la liste des pr�l�vements depuis l'attribut de la requ�te
    List<Reservoir> ls_reservoir = (List<Reservoir>) request.getAttribute("ls_reservoir");
%>

<form method="POST" action="reservoirPrelev">
    <h2>Prelevement RESERVOIR</h2>
    <label for="">Reservoir</label>
    <select name="id_reservoir" id="">
    <%
        for(Reservoir r:ls_reservoir){
            %><option value="<%=r.getIdReservoir()%>"><%=r.getIdReservoir()%> - <%=r.getTypeLiquide().getNomLiquide()%></option>
    <%    }
    %>
    </select>
    <br>
    <label for="">Date heure prelevement</label>
    <input type="datetime-local" name="dt_time" required>
    <br>
    <label for="">Longueur mesure</label>
    <input type="text" name="mesure" required>
    <br>
    <input type="submit" value="valider">
</form>

<%@ include file="footer.jsp" %>
