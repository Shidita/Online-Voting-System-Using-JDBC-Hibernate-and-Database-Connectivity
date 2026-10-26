<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin - E-Vote</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head>
<body>
<c:set var="active" value="admin" scope="request"/>
<%@ include file="nav.jspf" %>
<main class="wrap">
  <div class="page-head">
    <h1>Admin panel</h1>
    <p>Add or remove candidates and keep an eye on the vote counts.</p>
  </div>
  <c:if test="${not empty error}"><div class="alert err"><c:out value="${error}"/></div></c:if>

  <div class="panel">
    <h2>Add a candidate</h2>
    <form class="add-form" method="post" action="${pageContext.request.contextPath}/admin">
      <div class="field"><label>Candidate name</label><input name="name" placeholder="e.g. Priya Sharma" required maxlength="100"></div>
      <div class="field"><label>Party (optional)</label><input name="party" placeholder="e.g. Independent" maxlength="100"></div>
      <button class="btn">+ Add</button>
    </form>
  </div>

  <div class="panel">
    <h2>Candidates (${fn:length(results)})</h2>
    <table>
      <tr><th>Candidate</th><th>Party</th><th>Votes</th><th></th></tr>
      <c:forEach var="r" items="${results}" varStatus="st">
        <tr>
          <td><div class="who-cell"><div class="avatar a${st.index % 5}"><c:out value="${fn:toUpperCase(fn:substring(r.name, 0, 1))}"/></div><c:out value="${r.name}"/></div></td>
          <td class="muted"><c:out value="${empty r.party ? '-' : r.party}"/></td>
          <td><span class="pill">${r.votes}</span></td>
          <td style="text-align:right">
            <form method="post" action="${pageContext.request.contextPath}/admin"
                  onsubmit="return confirm('Delete this candidate and all their votes?')">
              <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${r.id}">
              <button class="btn danger">Delete</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty results}"><tr><td colspan="4" class="empty">No candidates yet. Add the first one above.</td></tr></c:if>
    </table>
  </div>
</main>
</body></html>
