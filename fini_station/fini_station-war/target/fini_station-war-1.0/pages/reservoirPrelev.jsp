<%@ include file="header.jsp" %>
<%@page import="mg.fini_station.stock.*"%>
<%@page import="mg.fini_station.mvt.PrelevementReservoir"%>
<%@page import="java.util.List"%>
<%
    // Obtenez la liste des pr�l�vements depuis l'attribut de la requ�te
    List<Reservoir> ls_reservoir = (List<Reservoir>) request.getAttribute("ls_reservoir");
    List<PrelevementReservoir> prelevements = (List<PrelevementReservoir>) request.getAttribute("prelevements");
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
    <label for="">Date prelevement</label>
    <input type="date" name="dt_time" required>
    <br>
    <label for="">Longueur mesure</label>
    <input type="text" name="mesure" required>
    <br>
    <input type="submit" value="valider">
</form>


<div class="table-responsive table-bordered">
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Longueur</th>
                <th>Date prelevement</th>
                <th>Volume</th>
                <th>Reservoir</th>
            </tr>
        </thead>
        <tbody>
            <%
                for(PrelevementReservoir p:prelevements){
            %>
            <tr>
                <td><%=p.getLongueur()%></td>
                <td><%=p.getdtPrelevement()%></td>
                <td><%=p.getVolume()%></td>
                <td><%=p.getR().getIdReservoir()%> - <%=p.getR().getTypeLiquide().getNomLiquide()%></td>
            </tr>
            <% }%>
        </tbody>
    </table>
</div>

<%@ include file="footer.jsp" %>
