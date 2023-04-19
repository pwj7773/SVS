
function checkForm(){
  let id = document.getElementById("id").value;
  let pw = document.getElementById("pw").value;

  // trim() : 문자열의 앞 또는 뒤에 붙어있는 공백 삭제
  if(id.trim().length == 0 || pw.trim().length == 0){
    alert("아이디 또는 비밀번호가 비어있습니다.");
    return false;
  }
  
  return true;
}



