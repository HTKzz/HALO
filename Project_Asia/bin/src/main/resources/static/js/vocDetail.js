$(document).ready(function(){
			$("#succSubmit").on("click", function(){
				if(confirm('글을 삭제하시겠습니까?')){
					alert("글을 삭제하였습니다.");	
				} else {
					return false;
				}
			});
		});