<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Live Results - E-Vote</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"></head>
<body>
<c:set var="active" value="results" scope="request"/>
<%@ include file="nav.jspf" %>
<main class="wrap">
  <div class="page-head">
    <h1>Live results</h1>
    <p><span class="live"><i></i> Live</span> &nbsp;Updates automatically every 3 seconds.</p>
  </div>
  <c:if test="${param.voted == '1'}"><div class="alert ok">✔ Your vote was recorded. Thank you!</div></c:if>

  <div class="stats">
    <div class="stat"><div class="label">Total votes</div><div class="value" id="total">0</div></div>
    <div class="stat"><div class="label">Candidates</div><div class="value" id="count">0</div></div>
    <div class="stat"><div class="label">Currently leading</div><div class="value" id="leader">-</div></div>
  </div>

  <div class="panel" id="list">
    <c:forEach var="r" items="${results}">
      <div class="res"><div class="res-main"><div class="res-top"><span class="res-name"><c:out value="${r.name}"/></span><span class="res-num">${r.votes} votes</span></div></div></div>
    </c:forEach>
  </div>
</main>
<script>
  var url = "${pageContext.request.contextPath}/results-data";
  var colors = ["#4f46e5", "#0891b2", "#db2777", "#ea580c", "#059669"];
  function el(tag, cls, text) { var e = document.createElement(tag); if (cls) e.className = cls; if (text !== undefined) e.textContent = text; return e; }

  function render(d) {
    document.getElementById("total").textContent = d.total;
    document.getElementById("count").textContent = d.candidates.length;
    var top = d.candidates.length && d.candidates[0].votes > 0 ? d.candidates[0] : null;
    document.getElementById("leader").textContent = top ? top.name : "-";

    var list = document.getElementById("list");
    if (!d.candidates.length) { list.innerHTML = ""; list.appendChild(el("div", "empty", "No candidates yet.")); return; }
    var old = {};
    list.querySelectorAll(".fill[data-name]").forEach(function (f) { old[f.dataset.name] = f.style.width; });
    list.innerHTML = "";
    d.candidates.forEach(function (c, i) {
      var pct = d.total ? Math.round(c.votes * 100 / d.total) : 0;
      var lead = top && c.votes === top.votes;
      var row = el("div", "res" + (lead ? " lead" : ""));
      row.appendChild(el("div", "rank", String(i + 1)));
      var main = el("div", "res-main");
      var head = el("div", "res-top");
      var name = el("span", "res-name", c.name);
      if (c.party) name.appendChild(el("span", "hint", "  " + c.party));
      if (lead) name.appendChild(el("span", "badge", "LEADING"));
      head.appendChild(name);
      head.appendChild(el("span", "res-num", c.votes + (c.votes === 1 ? " vote" : " votes") + "  -  " + pct + "%"));
      var track = el("div", "track");
      var fill = el("div", "fill");
      fill.dataset.name = c.name;
      fill.style.background = colors[i % colors.length];
      fill.style.width = old[c.name] || "0%";
      track.appendChild(fill);
      main.appendChild(head); main.appendChild(track);
      row.appendChild(main);
      list.appendChild(row);
      requestAnimationFrame(function () { requestAnimationFrame(function () { fill.style.width = pct + "%"; }); });
    });
  }
  function refresh() {
    fetch(url, { cache: "no-store" }).then(function (r) { return r.ok ? r.json() : null; })
      .then(function (d) { if (d) render(d); }).catch(function () {});
  }
  refresh();
  setInterval(refresh, 3000);
</script>
</body></html>
