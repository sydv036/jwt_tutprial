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
      success: function (response) {
        console.log("Đăng nhập thành công:", response);
        sessionStorage.setItem("token", response);
        // callApiGet(); // Tiếp tục xử lý logic sau khi đăng nhập thành công
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
    console.log("token", sessionStorage.getItem("token"));

    $.ajax({
      type: "GET",
      url: API + "student-token",
      headers: {
        Authorization: "Bearer " + sessionStorage.getItem("token"),
      },
      success: function (response) {
        console.log(response);
      },
      error: function (error) {
        console.log(error);
      },
    });
  }
});
