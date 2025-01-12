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
      dataType: "json",
      contentType: "application/json",
      success: function (response) {
        console.log(response.responseText);
        sessionStorage.setItem("access_token", response.responseText);
        console.log(response);
      },
      error: function (error) {
        console.log(error);
      },
    });
  });
});
