<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<h2 class="w3-text-theme">My Feed</h2>
<p class="w3-text-grey">Posts from your Bubble will appear here.</p>

<div class="w3-card w3-round w3-white w3-section w3-padding">
  <div class="w3-row">
    <div class="w3-col" style="width:48px">
      <div class="w3-circle w3-theme w3-center" style="height:40px;width:40px;line-height:40px;">U</div>
    </div>
    <div class="w3-rest w3-padding-left">
      <textarea class="w3-input w3-border w3-light-grey" placeholder="What's happening in your Bubble?" rows="2" disabled></textarea>
      <button class="w3-button w3-theme w3-small w3-right w3-margin-top" disabled>Post</button>
    </div>
  </div>
</div>

<div class="w3-card w3-round w3-white w3-section w3-padding">
  <strong>BubbleBot</strong> <span class="w3-text-grey w3-small">· just now</span>
  <p>Welcome to BubbleNet! Your local feed will appear here once members start posting.</p>
</div>
<div class="w3-card w3-round w3-white w3-section w3-padding">
  <strong>LocalAlert</strong> <span class="w3-text-grey w3-small">· 2 h ago</span>
  <p>Road works on Carrer de Provença until Friday. Plan your commute accordingly.</p>
</div>
<div class="w3-card w3-round w3-white w3-section w3-padding">
  <strong>NeighbourNews</strong> <span class="w3-text-grey w3-small">· 5 h ago</span>
  <p>Community meeting at the civic centre this Saturday at 11:00 AM. All welcome!</p>
</div>

<p class="w3-center w3-text-grey w3-small">[Mockup — backend not yet implemented]</p>
