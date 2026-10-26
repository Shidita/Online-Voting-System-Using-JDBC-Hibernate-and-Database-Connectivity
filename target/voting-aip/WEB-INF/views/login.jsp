<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login - E-Vote</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head>
<body>
<div class="auth">
  <section class="auth-hero">
    <div class="big">🗳️</div>
    <h1>Your vote.<br>Your voice.</h1>
    <p>A simple, secure online voting system. Cast your vote once and watch live results update in real time.</p>
    <ul class="points">
      <li><span>🔒</span> One vote per voter, enforced by the database</li>
      <li><span>📊</span> Live results that refresh every 3 seconds</li>
      <li><span>🛡️</span> Hashed passwords and session-based security</li>
    </ul>
  </section>
  <section class="auth-form">
    <div class="auth-card">
      <h2>Welcome back</h2>
      <p class="sub">Log in to cast your vote.</p>
      <c:if test="${param.registered == '1'}"><div class="alert ok">Account created. Please log in.</div></c:if>
      <c:if test="${not empty error}"><div class="alert err"><c:out value="${error}"/></div></c:if>
      <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="field"><label>Username</label><input name="username" placeholder="Enter your username" required autofocus></div>
        <div class="field"><label>Password</label><input name="password" type="password" placeholder="Enter your password" required></div>
        <button class="btn block">Log in</button>
      </form>
      <p class="swap">New voter? <a href="${pageContext.request.contextPath}/register"><b>Create an account</b></a></p>
      <div class="demo">Admin demo login: <b>admin</b> / <b>admin123</b></div>
    </div>
  </section>
</div>
</body></html>
