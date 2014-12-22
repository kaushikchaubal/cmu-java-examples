<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>hello-from-cmu.jsp</TITLE>
</HEAD>
<BODY>
<table border="0" width="100%">
  <tr>
    <td width="50%" align="right"><font size="5">Hello  from</font></td>
    <td width="50%"><img border="0" src="cfa.jpg" width="167" height="110"></td>
  </tr>
  <tr>
    <td width="50%" align="right"><font size="5">Scenic</font></td>
    <td width="50%"><img border="0" src="gsia.gif" width="168" height="130"></td>
  </tr>
  <tr>
    <td width="100%" colspan="2">
      <p align="center"><font size="6">Carnegie-Mellon University on
      <%= (new java.util.Date()).toString().substring(0,10) %>
      </font></td>
  </tr>
  <tr>
    <td width="100%" colspan="2">
      <p align="center"><img border="0" src="hammerschlag.jpg" width="768" height="512"></td>
  </tr>
</table>
<h2>
Where in Pittsburgh the time is now
<%= (new java.util.Date()).toString().substring(11,19) %>.</h2>
</BODY>
</HTML>
