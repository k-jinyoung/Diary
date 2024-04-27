//등록 기능
const createButton = document.getElementById("create-btn");

if (createButton){
    //클릭 이벤트가 감지되면 등록 API 요청
    createButton.addEventListener("click", (event) => {
            body = JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value,
                start: params.get('date')
            });
        function success() {
            alert("하루를 기록하였습니다!")
        }
        function fail() {
            alert("하루의 기록을 실패했습니다ㅠㅠ");
            location.replace("/calendar");
        }

        httpRequest("POST", "/api/diaries", body, success, fail)
    });
}

//쿠키 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(";");
    cookie.some(function (item) {
        item = item.replace(" ", "");

        var dic = item.split("=");

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

//HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {
            //로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization : "Bearer " + localStorage.getItem("access_token"),
            "Content-Type" : "application/json",
        },
        body:body,
    }).then((response) => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie("refresh_token");
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                }),
            })
                .then((res) => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then((result) =>{
                    //재발급이 성공하면 로컬 스토리지 값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem("access_token", result.accessToken);
                    httpRequest(mehtod, url, body, success, fail);
                })
                .catch((error) => fail());
        } else {
            return fail;
        }
    });
}


//수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton){
    //클릭 이벤트가 감지되면 수정 API요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title: document.getElementById("title").value,
            content: document.getElementById("content").value,
        });

        function success() {
            alert("하루를 수정하였습니다!");
            location.replace("/calendar");
        }

        function fail() {
            alert("하루를 수정하는 데 실패했습니다.");
            location.replace("/calendar");
        }

        httpRequest("PUT", "/api/diaries/" + id, body, success, fail);
    });
}

//삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton){
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('diary-id').value;
        function success() {
            alert("하루의 기록을 삭제하였습니다.");
            location.replace("/calendar");
        }

        function fail() {
            alert("하루의 기록을 삭제하는 데 실팼했습니다.");
            location.replace("/calendar");
        }

        httpRequest("DELETE", "/api/diaries/" + id, null, success, fail);
    });
}