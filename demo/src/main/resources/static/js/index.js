const HIDDEN_CLASSNAME = "hidden";
let form = document.querySelector("#form");
let title = document.querySelector("#title");
let content = document.querySelector("#content");
let username = document.querySelector("#username");
let titlealert = document.querySelector(".titlealert");
let contentalert = document.querySelector(".contentalert");
let usernamealert = document.querySelector(".usernamealert");
let url = location.href.split(location.host);

function href(){
    if(url[1] != "/"){
        location.href="/";
    }
}

//네비게이션 스크롤
document.querySelector('.a1').addEventListener('click',e=>{
    href();
    document.querySelector('.section1').scrollIntoView({behavior:'smooth'});
});
document.querySelector('.a2').addEventListener('click',e=>{
    href();
    document.querySelector('.section2').scrollIntoView({behavior:'smooth'});
});
document.querySelector('.a3').addEventListener('click',e=>{
    href();
    document.querySelector('.section3').scrollIntoView({behavior:'smooth'});
});


//validation
function validate(e) {
    titlealert.classList.add(HIDDEN_CLASSNAME);
    contentalert.classList.add(HIDDEN_CLASSNAME);
    usernamealert.classList.add(HIDDEN_CLASSNAME);
    if (title.value == "" || title.value.length <= 2) {
        e.preventDefault();
        console.log("title validate");
        titlealert.classList.remove(HIDDEN_CLASSNAME);
    } else if (username.value == "") {
        e.preventDefault()
        console.log("username validate");
        usernamealert.classList.remove(HIDDEN_CLASSNAME);
    } else if (content.value == "" || content.value.length <= 10) {
        e.preventDefault()
        console.log("content validate");
        contentalert.classList.remove(HIDDEN_CLASSNAME);
    }
}

form.addEventListener("submit",validate);

