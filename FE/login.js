$(() => {
  const API = "http://localhost:8888/";
  $("#login").click(() => {
    const userName = $("#username").val();
    const pass = $("#password").val();
    const data = {
      username: userName,
      password: pass,
    };

    $.ajax({
      type: "POST",
      url: API + "login",
      data: JSON.stringify(data),
      dataType: "text",
      contentType: "application/json",
      xhrFields: {
        withCredentials: true, // Cho phép gửi và nhận cookie
      },
      success: function (data) {
        const response = JSON.parse(data);
        console.log("Đăng nhập thành công:", response.token);
        localStorage.setItem("token", response.token);
        localStorage.setItem("refreshToken", response.refreshToken);
        callApiWithToken();
      },
      error: function (error) {
        console.log("Đăng nhập thất bại:", error);
        console.log("Chi tiết lỗi:", error.responseText);
      },
    });
  });
  function callApiGet() {
    $.ajax({
      type: "GET",
      url: API + "student",
      xhrFields: {
        withCredentials: true, // Đúng tên trường
      },
      success: function (response) {
        console.log(response);
      },
      error: function (error) {
        console.log(error);
      },
    });
  }
  function callApiWithToken() {
    $.ajax({
      type: "GET",
      url: API + "student-token",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
      success: function (response) {
        console.log(response);
      },
      error: function (error) {
        refreshToken();
        console.log(error);
      },
    });
  }
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
