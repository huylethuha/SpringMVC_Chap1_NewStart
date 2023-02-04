function submitForm() {
    var name = $("#name").val();
    var email = $("#email").val();
    $.ajax({
        url: "/user/create",
        type: "post",
        data: {
            name: name,
            email: email
        },
        success: function (data) {
            alert("User created successfully!");
        }
    });
}