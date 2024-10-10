<%@ include file="header.jsp" %>
<%@page import="mg.fini_station.stock.*"%>
<%@page import="mg.fini_station.mvt.StockReservoir"%>
<%@page import="java.util.List"%>

<%
List<Reservoir> reservoirs=(List<Reservoir>)request.getAttribute("reservoirs");

%>
<h1>Stock pour chaque reservoir</h1>
<table class="table table-responsive table-bordered">
    <thead>
        <tr>
            <th>Reservoir</th>
            <th>Quantite en stock</th>
            <th>Type liquide</th>
        </tr>
    </thead>
    <tbody>
    <% for(Reservoir r : reservoirs){
        %>
        <tr>
            <td><%="RESER"+r.getIdReservoir()%></td>
            <td><%=r.getQteActuel()%></td>
            <td><%=r.getTypeLiquide().getNomLiquide()%></td>
        </tr>
        <%
    } %>
    <tr>
        <th>Total</th>
        <th><%=new StockReservoir().getQteTotal()%></th>
        <th></th>
    </tr>
    </tbody>
</table>

<form action="stock" method="POST">
    <input type="date" name="dt" id="" required>
<input type="submit" value="Afficher les anomalies">
</form>

<h1>Anomalies</h1>
<% if(request.getAttribute("anomalies")==null){
    %> <h3>Aucune anomalie a afficher</h3>
    <%
}   else { 
    List<Anomalie> anomalies=(List<Anomalie>)request.getAttribute("anomalies");%>
<table class="table table-responsive table-bordered">
    <thead>
        <tr>
            <th>Reservoir</th>
            <th>Quantite du reservoir en stock</th>
            <th>Quantite de prelevement</th>
            <th>Difference</th>
            <th>Date</th>
        </tr>
    </thead>
    <tbody>
    <% for(Anomalie a : anomalies){
        %>
        <tr>
            <th><%="RESER"+a.getReservoir().getIdReservoir()%></th>
            <th><%=a.getQteReservoir()%></th>
            <th><%=a.getQtePrelevement()%></th>
            <th><%=a.getDifference()%></th>
            <th><%=a.getDt()%></th>
        </tr>
        <%
    } %>
    </tbody>
</table>
<% } %>

<%@ include file="footer.jsp" %>
