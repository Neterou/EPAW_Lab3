<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:choose>
<c:when test="${not empty user}">
<div class="w3-container w3-card w3-round w3-white w3-section">

  <div class="w3-center w3-padding">
    <c:choose>
      <c:when test="${not empty user.picture}">
        <img src="${user.picture}" class="w3-circle" style="height:90px;width:90px;object-fit:cover" alt="Avatar">
      </c:when>
      <c:otherwise>
        <div class="w3-circle w3-theme w3-center" style="height:90px;width:90px;line-height:90px;font-size:2em;display:inline-block;">U</div>
      </c:otherwise>
    </c:choose>
    <h3 class="w3-text-theme">${user.username}</h3>
    <c:choose>
      <c:when test="${user.status == 'APPROVED'}">
        <span class="w3-tag w3-round w3-green">${user.status}</span>
      </c:when>
      <c:otherwise>
        <span class="w3-tag w3-round w3-orange">${user.status}</span>
      </c:otherwise>
    </c:choose>
  </div>

  <hr>

  <p><i class="fa fa-user fa-fw w3-margin-right"></i> ${user.name}</p>
  <p><i class="fa fa-envelope fa-fw w3-margin-right"></i> ${user.email}</p>
  <c:if test="${not empty user.phone}">
    <p><i class="fa fa-phone fa-fw w3-margin-right"></i> ${user.phoneCountry} ${user.phone}</p>
  </c:if>
  <p><i class="fa fa-id-card fa-fw w3-margin-right"></i> ${user.dni}</p>
  <p><i class="fa fa-map-marker fa-fw w3-margin-right"></i> ${user.zip} ${user.city}, ${user.country}</p>

</div>
</c:when>
<c:otherwise>
  <p class="w3-text-grey w3-center">Not logged in.</p>
</c:otherwise>
</c:choose>
