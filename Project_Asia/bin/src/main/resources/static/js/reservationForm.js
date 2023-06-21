$(document).ready(function(){
			$("#cnt").on("propertychange change paste input", function() {
				var price = fnReplace($("#hdprice").val());
				var cnt = fnReplace($("#cnt").val())
				
				$("#price").val(price*cnt);
			});
			
			document.getElementById('btn').onclick = function() {
				document.getElementById('frm').submit();
			}
		});
		
		function fnReplace(val) {
		    var ret = 0;
		    if(typeof val != "undefined" && val != null && val != ""){
		        ret = Number(val.replace(/,/gi,''));
		    }
		    return ret;        
		}