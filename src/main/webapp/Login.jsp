<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty registered}">
  <div class="w3-panel w3-green w3-round">
    <p>Registration successful! Please log in.</p>
  </div>
</c:if>

<h2 class="w3-text-theme">Login</h2>

<form id="loginForm" action="Login" method="POST">

  <div>
    <label for="username" class="w3-text-theme">Username:</label>
    <input type="text" class="w3-input w3-border w3-light-grey"
        id="username" name="username" required minlength="4" maxlength="30"
        value="${user.username}"
        title="Your username." />
  </div>
  <div>
    <label for="password" class="w3-text-theme">Password:</label>
    <input type="password" class="w3-input w3-border w3-light-grey"
        id="password" name="password" required />
  </div>

  <button type="submit" class="w3-button w3-theme w3-section">Log in</button>

</form>

<script>
  App.Errors = {
    <c:forEach var="error" items="${errors}">
      "${error.key}": "${error.value}",
    </c:forEach>
  };
  App.initLoginValidation(App.Errors);
</script>
