<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.net.*" %>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Cache-Control","no-store"); 
response.setDateHeader("Expires", 0); 
%>

<%@ page import="java.io.UnsupportedEncodingException,java.net.URLEncoder" %>
<%!
// Copyright 2009 Google Inc. All Rights Reserved.
private static final String GA_ACCOUNT = "MO-15436829-1";
private static final String GA_PIXEL = "/common/ga.jsp";

private String googleAnalyticsGetImageUrl(
    HttpServletRequest request) throws UnsupportedEncodingException {
  StringBuilder url = new StringBuilder();
  url.append(GA_PIXEL + "?");
  url.append("utmac=").append(GA_ACCOUNT);
  url.append("&utmn=").append(Integer.toString((int) (Math.random() * 0x7fffffff)));
  String referer = request.getHeader("referer");
  String query = request.getQueryString();
  String path = request.getRequestURI();
  if (referer == null || "".equals(referer)) {
    referer = "-";
  }
  url.append("&utmr=").append(URLEncoder.encode(referer, "UTF-8"));
  if (path != null) {
    if (query != null) {
      path += "?" + query;
    }
    url.append("&utmp=").append(URLEncoder.encode(path, "UTF-8"));
  }
  url.append("&guid=ON");
  return url.toString().replace("&", "&amp;");
}
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta name="viewport" content="width=device-width; initial-scale=1.4; minimum-scale=1.0; maximum-scale=2.0"/>
<meta name="MobileOptimized" content="240"/>
<meta name="keywords" content="中国银联,手机支付,WAP支付" />
<meta name="description" content="中国银联,手机支付,WAP支付" />

