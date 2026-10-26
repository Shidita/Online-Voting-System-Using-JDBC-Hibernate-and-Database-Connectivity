<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Register - E-Vote</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head>
<body>
<div class="auth">
  <section class="auth-hero">
    <div class="big">✍️</div>
    <h1>Join and<br>make it count.</h1>
    <p>Create your voter account in seconds. You can vote once, and your choice stays secure.</p>
    <ul class="points">
      <li><span>1️⃣</span> Pick a username and password</li>
      <li><span>🗳️</span> Choose your candidate</li>
      <li><span>📊</span> Follow the live results</li>
    </ul>
  </section>
  <section class="auth-form">
    <div class="auth-card">
      <h2>Create account</h2>
      <p class="sub">Register as a voter.</p>
      <c:if test="${not empty error}"><div class="alert err"><c:out value="${error}"/></div></c:if>
      <form method="post" action="${pageContext.request.contextPath}/register">
        <div class="field"><label>Username</label><input name="username" placeholder="3-20 letters, numbers or _" required autofocus></div>
        <div class="field"><label>Password</label><input name="password" type="password" placeholder="At least 6 characters" required></div>
        <button class="btn block">Register</button>
      </form>
      <p class="swap">Already registered? <a href="${pageContext.request.contextPath}/login"><b>Log in</b></a></p>
    </div>
  </section>
</div>
</body></html>
