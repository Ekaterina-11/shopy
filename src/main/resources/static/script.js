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
            post('/admDelProduct',"id="+encodeURIComponent(parseInt(event.target.name)));
        }
    });
}

let confirmOrderButton = Array.from(document.getElementsByClassName("confirmOrderButton"));
if(confirmOrderButton != null){
    confirmOrderButton.forEach(e => {
        e.onclick = function(event){
            post('/confirmOrder',"id="+encodeURIComponent(parseInt(event.target.name)));
        }
    });
}

let blockUserButton = Array.from(document.getElementsByClassName("blockUserButton"));
if(blockUserButton != null){
    blockUserButton.forEach(e => {
        e.onclick = function(event){
            post('/admCustomer',"id="+encodeURIComponent(parseInt(event.target.name)));
        }
    });
}

let cardButton = document.getElementById("cardButton");
let cartClose = document.getElementById("cartClose");
let cartClear = document.getElementById("cartClear");
let cartConfirm = document.getElementById("cartConfirm");
let goodsCart = document.getElementById("goodsCart");
if(cardButton != null){
    cartClose.onclick = function(event){
        let cartDiv = document.getElementById("cartDiv");
        cartDiv.style.display = "none";
    }

    cartConfirm.onclick = function(event){
        post('/cartConfirm',"cart="+encodeURIComponent(JSON.parse(localStorage.getItem("cartStore")))+"&date="+new Date().toISOString());
        localStorage.setItem("cartStore", JSON.stringify([]));
        localStorage.setItem("cartTitle", JSON.stringify([]));
        localStorage.setItem("cartCost", JSON.stringify([]));
        let cartDiv = document.getElementById("cartDiv");
        cartDiv.style.display = "none";
    }

    cartClear.onclick = function(event){
        localStorage.setItem("cartStore", JSON.stringify([]));
        localStorage.setItem("cartTitle", JSON.stringify([]));
        localStorage.setItem("cartCost", JSON.stringify([]));
        let cartDiv = document.getElementById("cartDiv");
        cartDiv.style.display = "none";
    }

    cardButton.onclick = function(event){
        let cartDiv = document.getElementById("cartDiv");
        cartDiv.style.display = "flex";
        let cartTitle = Array.from(JSON.parse(localStorage.getItem("cartTitle")));
        let cartCost = Array.from(JSON.parse(localStorage.getItem("cartCost")));
        let products = "";
        for(let i=0;i<cartTitle.length;i++){
            products += cartTitle[i]+" "+cartCost[i]+"<br>";
        }
        goodsCart.innerHTML = products;
    }
}

let addToCart = Array.from(document.getElementsByClassName("addToCart"));
if(addToCart != null){
    addToCart.forEach(e => {
        e.onclick = function(event){
            let cartStore = JSON.parse(localStorage.getItem("cartStore"));
            let cartTitle = JSON.parse(localStorage.getItem("cartTitle"));
            let cartCost = JSON.parse(localStorage.getItem("cartCost"));
            cartStore.push(event.target.value);
            cartTitle.push(document.getElementById("title"+event.target.value).innerHTML);
            cartCost.push(document.getElementById("cost"+event.target.value).innerHTML);
            localStorage.setItem("cartStore", JSON.stringify(cartStore));
            localStorage.setItem("cartTitle", JSON.stringify(cartTitle));
            localStorage.setItem("cartCost", JSON.stringify(cartCost));
        }
    });
}

let initialStore = JSON.parse(localStorage.getItem("cartStore"));
if(initialStore == null){
    localStorage.setItem("cartStore", JSON.stringify([]));
    localStorage.setItem("cartTitle", JSON.stringify([]));
    localStorage.setItem("cartCost", JSON.stringify([]));
}

let searching = document.getElementById("searching");
let minCost = document.getElementById("minCost");
let maxCost = document.getElementById("maxCost");
let blocks = Array.from(document.getElementsByClassName("simpleProduct"));
let allCosts = Array.from(document.getElementsByClassName("allCosts"));

if(searching != null){
    searching.onkeyup = function searchFunc(){
        if(searching.value != "" && searching.value != " "){
            for(let i=0;i<blocks.length;i++){
                if(blocks[i].innerHTML.indexOf(searching.value) !== -1){
                    blocks[i].style.display = "flex";
                }else{
                    blocks[i].style.display = "none";
                }
            }
            allCosts.forEach(e => {
                if(parseInt(e.innerHTML) < parseInt(minCost.value) || parseInt(e.innerHTML) > parseInt(maxCost.value)){
                    e.parentElement.style.display = "none";
                }
            });
        }else{
            for(let i=0;i<blocks.length;i++){
                blocks[i].style.display = "flex";
            }
        }
    }
    minCost.onchange = function searchFunc1(){
        for(let i=0;i<blocks.length;i++){
            if(blocks[i].innerHTML.indexOf(searching.value) !== -1){

            }else{
                blocks[i].style.display = "none";
            }
        }
        allCosts.forEach(e => {
            if(parseInt(e.innerHTML) < parseInt(minCost.value) || parseInt(e.innerHTML) > parseInt(maxCost.value)){
                e.parentElement.style.display = "none";
            }else{
                e.parentElement.style.display = "flex";
            }
        });
    }

    maxCost.onchange = function searchFunc2(){
        for(let i=0;i<blocks.length;i++){
            if(blocks[i].innerHTML.indexOf(searching.value) !== -1){

            }else{
                blocks[i].style.display = "none";
            }
        }
        allCosts.forEach(e => {
            if(parseInt(e.innerHTML) < parseInt(minCost.value) || parseInt(e.innerHTML) > parseInt(maxCost.value)){
                e.parentElement.style.display = "none";
            }else{
                e.parentElement.style.display = "flex";
            }
        });
    }
}