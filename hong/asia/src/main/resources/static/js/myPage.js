$(document).ready(function() {
        	document.getElementById('btn').onclick = function() {
        		var pw = $("#myPage-text-pw").val();
        		
        		if (pw == "") {
            	    alert("내용을 입력해주세요.");
                	return false;
          		}
        	}
		});