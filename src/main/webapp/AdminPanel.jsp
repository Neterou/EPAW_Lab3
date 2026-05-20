<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2 class="w3-text-theme">Admin Panel</h2>
<p class="w3-text-grey">Manage users, Bubbles, and platform settings.</p>

<div class="w3-row-padding">
  <div class="w3-third">
    <div class="w3-card w3-round w3-white w3-padding w3-section w3-center">
      <i class="fa fa-users fa-2x w3-text-theme"></i>
      <h4>Manage Users</h4>
      <p class="w3-text-grey w3-small">Ban, suspend, reset passwords, approve pending accounts.</p>
      <button class="w3-button w3-theme w3-round w3-small" disabled>Open</button>
    </div>
  </div>
  <div class="w3-third">
    <div class="w3-card w3-round w3-white w3-padding w3-section w3-center">
      <i class="fa fa-map-marker fa-2x w3-text-theme"></i>
      <h4>Manage Bubbles</h4>
      <p class="w3-text-grey w3-small">Create, edit, delete Bubbles and assign users.</p>
      <button class="w3-button w3-theme w3-round w3-small" disabled>Open</button>
    </div>
  </div>
  <div class="w3-third">
    <div class="w3-card w3-round w3-white w3-padding w3-section w3-center">
      <i class="fa fa-flag fa-2x w3-text-theme"></i>
      <h4>Moderate Content</h4>
      <p class="w3-text-grey w3-small">Review reported posts and remove inappropriate content.</p>
      <button class="w3-button w3-theme w3-round w3-small" disabled>Open</button>
    </div>
  </div>
</div>

<h3>Pending Approvals</h3>
<table class="w3-table w3-striped w3-bordered w3-white">
  <tr class="w3-theme">
    <th>Username</th><th>Name</th><th>Bubble</th><th>Actions</th>
  </tr>
  <tr>
    <td>pending_user1</td><td>John Doe</td><td>Bubble Paris</td>
    <td>
      <button class="w3-button w3-green w3-small" disabled>Approve</button>
      <button class="w3-button w3-red w3-small" disabled>Reject</button>
    </td>
  </tr>
</table>

<p class="w3-center w3-text-grey w3-small">[Mockup — backend not yet implemented]</p>
