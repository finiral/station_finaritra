<%@ include file="header.jsp" %>

<%@page import="mg.fini_station.mvt.Prevision"%>
<%@page import="java.util.List"%>

<%
List<Prevision> previsions=(List<Prevision>)request.getAttribute("previsions");

%>
<form action="prevision" method="POST">
    <p>Date <input type="month" name="dt"> </p>
    <input type="submit" value="VAlider">
</form>
<h1>TABLEAU DE PREVISION</h1>
<table class="table table-bordered table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Designation</th>
            <th>Montant</th>
            <th>ID Tiers</th>
            <th>Date</th>
        </tr>
    </thead>
    <tbody>
    <% for(Prevision p: previsions){ %>
        <tr>
            <td><%=p.getId()%></td>
            <td><%=p.getDesignation()%></td>
            <td><%=p.getMontant()%></td>
            <td><%=p.getIdTier()%></td>
            <td><%=p.getDt()%></td>
        </tr>
    <% }%>
    </tbody>
</table>


<%@ include file="footer.jsp" %>
