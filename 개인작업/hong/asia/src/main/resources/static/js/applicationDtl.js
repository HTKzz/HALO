$(document).ready(function() {
			document.getElementById('btn').onclick = function() {
				var date = $("#test option:selected").val();
        	    
          		if (date == "") {
            	    alert("날짜를 선택해주세요.");
                	return false;
          		}
			}
			
			$("#btn_2").on("click", function(){
				if(confirm('글을 삭제하시겠습니까?')){
					alert("글을 삭제하였습니다.");	
				} else {
					return false;
				}
			});
        });
		