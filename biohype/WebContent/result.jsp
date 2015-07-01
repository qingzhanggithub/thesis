<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="edu.umassmed.biohyp.Searcher" %>
<%
	String query = (String)request.getParameter("symbol");
	String result = null;
	if(query != null){
		Searcher searcher = new Searcher("interaction");
		result = searcher.testSearch(query);
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
<title>BioHype</title>
</head>
<body>
<table width="100%" align="center" >
<tr>
<!--  
<td><img src=figure/umassmed.jpg></td>
-->
<td><center><h2>BioHype - Hypothesis Generation for Protein Interaction</h2></center></td>
</tr>
</table>

<!-- Search box -->
<form action=result.jsp method=get>
<table id="main_search" width=800 align="center" >
<tr>
<td>Protein Symbol&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="symbol" >&nbsp;&nbsp;&nbsp;&nbsp;<input type=submit name="Search"></td>
</tr>
</table>
</form>
<!--  Display search result -->
<%if(result !=null){ %>

<table id="result" width="80%" align="right" >
<%=result %>
</table>

<% result= null; query=null;}%>
<!-- Info -->
<table id="info" width="100%">
<tr>
<td>University of Massachusetts Medical School</td>
</tr>
<tr>
<td>Powered by <a href="http://thebiogrid.org/">BioGRID</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="http://www.nlm.nih.gov/bsd/pmresources.html">MEDLINE</a></td>
</tr>
<tr>
<td>Author: <a href="mailto:zq.zhangqing@gmail.com">Qing Zhang</a></td>
</tr>
<tr>
<td>&copy;2013</td>
</tr>
</table>
</body>
</html>