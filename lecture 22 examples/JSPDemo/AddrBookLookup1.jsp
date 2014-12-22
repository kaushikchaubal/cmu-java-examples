<html>
<head>
  <title>AddrBook Lookup Example -- Model 1</title>
</head>
<body>
  <%@ page import="databeans.Entry" %>
  <%@ page import="dao.EntryDAO" %>

<form method="GET" action="AddrBookLookup1.jsp">
  <table border="1">
    <tr>
      <td align="right"><span style="font-size: 14pt">Lastname:</span></td>
      <td><input type="text" name="lastName" size="35" style="font-size: 12pt"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="submit" value="Submit" name="B1" style="font-size: 12pt"></font></td>
    </tr>
  </table>
</form>

<%
    String lastName = request.getParameter("lastName");
    if (lastName != null) {
        EntryDAO dao = new EntryDAO(application.getInitParameter("jdbcDriverName"),application.getInitParameter("jdbcURL"),false);
        Entry[] list = dao.lookupByLastName(request.getParameter("lastName"));
%>
  
  <table>
    <tr><td colspan="2"><hr/></td></tr>
    <tr><td colspan="2">Found <%= list.length %> entries</td></tr>
<% 
        for (Entry e : list) {
%>
    <tr><td colspan="2"><hr/></td></tr>
    <tr>
      <td> Last Name: </td>
      <td> <%= e.getLastName() %> </td>
    </tr>
    <tr>
      <td> First Names: </td>
      <td> <%= e.getFirstNames() %> </td>
    </tr>
    <tr>
      <td> Home Phone: </td>
      <td> <%= e.getHomePhone() %> </td>
    </tr>
    <tr>
      <td> Cell Phone: </td>
      <td> <%= e.getCellPhone() %> </td>
    </tr>
    <tr>
      <td> Work Phone: </td>
      <td> <%= e.getWorkPhone() %> </td>
    </tr>
    <tr>
      <td> Fax: </td>
      <td> <%= e.getFax() %> </td>
    </tr>
    <tr>
      <td> E-mail: </td>
      <td> <%= e.getEmail() %> </td>
    </tr>
    <tr>
      <td> Address: </td>
      <td> <%= e.getAddress() %> </td>
    </tr>
    <tr>
      <td> City: </td>
      <td> <%= e.getCity() %> </td>
    </tr>
    <tr>
      <td> State: </td>
      <td> <%= e.getState() %> </td>
    </tr>
    <tr>
      <td> Zip: </td>
      <td> <%= e.getZip() %> </td>
    </tr>
    <tr>
      <td> Country: </td>
      <td> <%= e.getCountry() %> </td>
    </tr>
<% 
        }
%>
  </table>
<% 
    }
%>
</body>
</html>
