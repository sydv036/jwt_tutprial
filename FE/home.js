import { callApiGet } from "./common.js";
$(document).ready(async () => {
  const arr = await callApiGet("http://localhost:8888/student-token");
  const tableBody = $("#table_body");
  arr.forEach((item) => {
    const row = `
         <tr>
            <th scope="row">${item.id}</th>
            <td>${item.email}</td>
            <td>${item.name}</td>
        </tr>
    `;
    tableBody.append(row);
  });
  function refreshToken() {
    $.ajax({
      type: "POST",
      url: API + "refresh-token",
      data: JSON.stringify(localStorage.getItem("refreshToken")),
      dataType: "json",
      contentType: "application/json",
      success: function (response) {
        console.log("AccessTokenNew", response);
        localStorage.setItem("token", response);
        console.log(response);
      },
      error: function (error) {
        console.log(error);
      },
    });
  }
});
