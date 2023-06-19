$(document).ready(function() {
	document.getElementById('btn').onclick = function() {
		var pw = $("#myPage-text-pw").val();
        		
		if (pw == "") {
			alert("내용을 입력해주세요.");
			return false;
          		
		}else if(pw.length < 8) {
			alert("비밀번호는 8자 이상이여야 합니다.");
			return false;
          	
		}else {
			if(confirm('회원 정보를 수정 하시겠습니까?')){
						
			} else {
				return false;
			}
		}
	}
	
	$("#my-memQuit").on("click", function(){
		if(confirm('회원 탈퇴를 하시겠습니까?')){
			alert('탈퇴 되셨습니다.');
		}else{
			return false;
		}
	});
	
	
});