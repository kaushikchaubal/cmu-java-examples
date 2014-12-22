<html>
<head>
  <title>AddrBook Lookup Example</title>
</head>
<body>
  <%@ page import="databeans.Entry" %>
  <% Entry[] list = (Entry[]) request.getAttribute("list"); %>

  <jsp:include page="AddrBookLookupForm.html" />
  <hr/>

  <h3>Found <%=list.length%> entries:</h3>
  <table>
<%
      for (int i=0; i<list.length; i++) {
          Entry e = list[i];
%>
    <tr>
      <td>
        <a href="idsearch?id=<%=e.getId()%>">
          <%=e.getLastName()%>, <%=e.getFirstNames()%>
        </a>
      </td>
    </tr>
<%
      }
%>
  </table>
</body>
</html>
