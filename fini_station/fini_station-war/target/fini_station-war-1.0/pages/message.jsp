<%
if (request.getAttribute("etat") != null) { 
String etat = (String) request.getAttribute("etat");
%>
    <script>
        window.alert("<%= etat.replace("\"", "\\\"") %>");
    </script>
<% 
}%>