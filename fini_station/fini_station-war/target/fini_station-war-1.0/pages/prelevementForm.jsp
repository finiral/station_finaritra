<%@page import="pompe.Pompe"%>
<%@page import="mg.fini_station.pompes.Pompiste"%>
<%@page import="mg.fini_station.pompes.Prelevement"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<%
List<Pompiste> ls_pompiste=(List<Pompiste>)request.getAttribute("pompistes");
List<Prelevement> prelevements=(List<Prelevement>)request.getAttribute("prelevements");
List<mg.fini_station.pompes.Pompe> ls_pompe=(List<mg.fini_station.pompes.Pompe>)request.getAttribute("pompes");

%>
<form method="POST" action="prelevement">
    <h2>Formulaire Prelevement</h2>
    <label for="">Pompe</label>
    <select name="id_pompe" id="">
        <%
            for(int i=0 ; i<ls_pompe.size();i++){
        %>
        <option value="<%=ls_pompe.get(i).getIdPompe()%>"><%=ls_pompe.get(i).getNumeroPompe()%></option>
        <% } %>
    </select>
    <br>
    <label for="">Pompiste</label>
    <select name="id_pompiste" id="">
        <%
            for(int i=0 ; i<ls_pompiste.size();i++){
        %>
        <option value="<%=ls_pompiste.get(i).getIdPompiste()%>"><%=ls_pompiste.get(i).getNomPompiste()%></option>
        <% } %>
    </select>
    <br>
    <label for="">Date</label>
    <input type="datetime-local" name="dt_time">
    <br>
    <label for="">Montant</label>
    <input type="number" name="montant" step="0.1">
    <br>
    <input type="submit" value="valider">
</form>


<div class="table-responsive table-bordered">
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Pompiste</th>
                <th>Pompe</th>
                <th>Compteur</th>
                <th>Date heure prelevement</th>
            </tr>
        </thead>
        <tbody>
            <%
                for(Prelevement p:prelevements){
            %>
            <tr>
                <td><%=p.getPompiste().getNomPompiste()%></td>
                <td><%=p.getPompe().getNumeroPompe()%></td>
                <td><%=p.getCompteur()%></td>
                <td><%=p.getDateTime()%></td>
            </tr>
            <% }%>
        </tbody>
    </table>
</div>


<%@ include file="footer.jsp" %>
