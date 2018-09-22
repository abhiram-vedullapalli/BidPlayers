function onSignIn(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/tokensignin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
      console.log('Signed in as: ' + xhr.responseText);
    };
    xhr.send('id_token=' + id_token);
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {	
          window.location.href = this.responseText
        }
      }
  }