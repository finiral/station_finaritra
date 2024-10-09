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


<%@ include file="footer.jsp" %>
