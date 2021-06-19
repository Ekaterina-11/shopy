function post(address, body) {
	let xhr = new XMLHttpRequest();
	xhr.open("POST", address, true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
          setTimeout(function(){
              document.location.reload();
          },500);
		}
	};
	xhr.send(body);
}

let uploadAvatarButton = document.getElementById("uploadAvatarButton");
uploadAvatarButton.onclick = function(){
    let avatarInput = document.getElementById("avatarInput");

    const img = new Image();
    img.src = avatarInput.value;
    img.onload = function () {
      post('/uploadAvatarPhoto',"avatarPhoto="+encodeURIComponent(avatarInput.value));
      document.getElementById("avatarInput").style.border = "2px solid green";
    }
    img.onerror = function () {
      document.getElementById("avatarInput").style.border = "2px solid red";
    }
}