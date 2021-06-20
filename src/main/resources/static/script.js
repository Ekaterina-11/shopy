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
if(uploadAvatarButton != null){
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
}

if(document.getElementById("submitAdd") != null){
    document.getElementById("submitAdd").style.display = "none";
}
let uploadPhotoButton = document.getElementById("image");
let productImg = document.getElementById("productImg");
if(uploadPhotoButton != null){
    uploadPhotoButton.onkeyup = function(){
        const img = new Image();
        img.src = uploadPhotoButton.value;
        img.onload = function () {
          productImg.src = uploadPhotoButton.value;
          document.getElementById("submitAdd").style.display = "block";
          document.getElementById("image").style.border = "2px solid green";
        }
        img.onerror = function () {
          productImg.src = "";
          document.getElementById("submitAdd").style.display = "none";
          document.getElementById("image").style.border = "2px solid red";
        }
    }
}

let deleteProduct = Array.from(document.getElementsByClassName("deleteProduct"));
if(deleteProduct != null){
    deleteProduct.forEach(e => {
        e.onclick = function(event){
            post('/admDelProduct',"id="+encodeURIComponent(parseInt(event.target.name))); //event.target
        }
    });
}

let blockUserButton = Array.from(document.getElementsByClassName("blockUserButton"));
if(blockUserButton != null){
    blockUserButton.forEach(e => {
        e.onclick = function(event){ //admCustomer
            post('/admCustomer',"id="+encodeURIComponent(parseInt(event.target.name)));
        }
    });
}