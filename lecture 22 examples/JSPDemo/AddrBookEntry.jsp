<html>
<head>
  <title>AddrBook Lookup Example</title>
</head>
<body>
  <jsp:include page="AddrBookLookupForm.html" />
  <hr/>
  <table>
    <tr>
      <td> Last Name: </td>
      <td> ${entry.lastName} </td>
    </tr>
    <tr>
      <td> First Names: </td>
      <td> ${entry.firstNames} </td>
    </tr>
    <tr>
      <td> Home Phone: </td>
      <td> ${entry.homePhone} </td>
    </tr>
    <tr>
      <td> Cell Phone: </td>
      <td> ${entry.cellPhone} </td>
    </tr>
    <tr>
      <td> Work Phone: </td>
      <td> ${entry.workPhone} </td>
    </tr>
    <tr>
      <td> Fax: </td>
      <td> ${entry.fax} </td>
    </tr>
    <tr>
      <td> E-mail: </td>
      <td> ${entry.email} </td>
    </tr>
    <tr>
      <td> Address: </td>
      <td> ${entry.address} </td>
    </tr>
    <tr>
      <td> City: </td>
      <td> ${entry.city} </td>
    </tr>
    <tr>
      <td> State: </td>
      <td> ${entry.state} </td>
    </tr>
    <tr>
      <td> Zip: </td>
      <td> ${entry.zip} </td>
    </tr>
  </table>
</body>
</html>
