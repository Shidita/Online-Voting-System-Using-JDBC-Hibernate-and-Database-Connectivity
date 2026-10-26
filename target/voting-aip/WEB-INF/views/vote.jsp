<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Vote - E-Vote</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head>
<body>
<c:set var="active" value="vote" scope="request"/>
<%@ include file="nav.jspf" %>
<main class="wrap">
  <div class="page-head">
    <h1>Cast your vote</h1>
    <p>Select one candidate below. Your vote is final and cannot be changed.</p>
  </div>
  <c:if test="${not empty error}"><div class="alert err"><c:out value="${error}"/></div></c:if>

  <c:choose>
    <c:when test="${voted}">
      <div class="panel done">
        <div class="check">✔</div>
        <h2>Thank you for voting!</h2>
        <p>Your vote has been recorded. Each voter can vote only once.</p>
        <a class="btn" href="${pageContext.request.contextPath}/results">See live results →</a>
      </div>
    </c:when>
    <c:when test="${empty candidates}">
      <div class="panel empty">No candidates have been added yet. Please check back later.</div>
    </c:when>
    <c:otherwise>
      <form id="ballot" method="post" action="${pageContext.request.contextPath}/vote">
        <div class="cands">
          <c:forEach var="cd" items="${candidates}" varStatus="st">
            <label class="cand">
              <input type="radio" name="candidateId" value="${cd.id}" required>
              <div class="cand-body">
                <div class="avatar a${st.index % 5}"><c:out value="${fn:toUpperCase(fn:substring(cd.name, 0, 1))}"/></div>
                <div class="cand-info">
                  <div class="cand-name"><c:out value="${cd.name}"/></div>
                  <div class="cand-party"><c:out value="${empty cd.party ? 'Independent' : cd.party}"/></div>
                </div>
                <div class="tick">✓</div>
              </div>
            </label>
          </c:forEach>
        </div>
        <div class="submit-bar">
          <button id="submitBtn" class="btn" disabled>Submit my vote</button>
          <span class="hint" id="pickHint">Select a candidate to continue</span>
        </div>
      </form>
      <script>
        (function () {
          var form = document.getElementById("ballot"), btn = document.getElementById("submitBtn"), hint = document.getElementById("pickHint");
          form.addEventListener("change", function () {
            btn.disabled = false;
            var sel = form.querySelector("input:checked").closest(".cand").querySelector(".cand-name").textContent;
            hint.textContent = "You selected: " + sel;
          });
          form.addEventListener("submit", function (e) {
            var sel = form.querySelector("input:checked").closest(".cand").querySelector(".cand-name").textContent;
            if (!confirm("Vote for " + sel + "?\nYour vote is final and cannot be changed.")) e.preventDefault();
          });
        })();
      </script>
    </c:otherwise>
  </c:choose>
</main>
</body></html>
