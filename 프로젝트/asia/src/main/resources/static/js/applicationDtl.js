$(document).ready(function() {
			document.getElementById('btn').onclick = function() {
				var date = $("#test option:selected").val();
        	    
          		if (date == "") {
            	    alert("날짜를 선택해주세요.");
                	return false;
          		}
			}
        });
		