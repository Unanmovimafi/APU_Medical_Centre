<h2>Create Counter Staff</h2>
<c:if test="${not empty error}">
  <p style="color: red;">${error}</p>
</c:if>

<form method="post">
  <label>Name: <input type="text" name="name" required /></label><br />
  <label>Email: <input type="email" name="email" /></label><br />
  <label>Phone: <input type="text" name="phoneNumber" /></label><br />
  <label>Username: <input type="text" name="username" required /></label><br />
  <label>Status: 
    <select name="status">
      <option value="ACTIVE">ACTIVE</option>
      <option value="INACTIVE">INACTIVE</option>
    </select>
  </label><br />
  <button type="submit">Create Staff</button>
</form>
