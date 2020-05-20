$.validator.addMethod("password", function(value, element) {   
    var password = /^[a-zA-Z0-9_\.]{6,10}$/;
    return this.optional(element) || (password.test(value));
}, "密码由[a-zA-Z0-9_.]组成的6到10位");