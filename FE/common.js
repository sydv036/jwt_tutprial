const API = "http://localhost:8888/";
function callApiGet(url) {
  return new Promise((resolve, reject) => {
    $.ajax({
      url: url,
      type: "GET",
      dataType: "json",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
      success: function (data) {
        resolve(data);
      },
      error: function (error) {
        console.log("err", error);
        refreshToken();
        reject(error);
      },
    });
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
export { callApiGet };
