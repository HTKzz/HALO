$(document).ready(function() {
			$("#searchBtn").on("click", function(e) {
				e.preventDefault();
				
				var searchBy = $("#searchBy").val();
				var searchQuery = encodeURI($("#searchQuery").val());
				
				if (((searchBy == "stat") || (searchBy == "role")) && searchQuery.length == 1) {
                    alert("2글자이상 입력해주세요.");
                    return false;
                }
				
				page(0);
			});
			
			$(".myReservationForm-btn").on("click", function(){
				if(confirm('회원 상태를 변경하시겠습니까?')){
					alert("변경되었습니다.");	
				} else {
					return false;
				}
			});
		});

		function page(page) {
			
			var searchBy = $("#searchBy").val();
			var searchQuery = encodeURI($("#searchQuery").val());
			
			location.href = "/admin/memberMngList/" + page + "?searchBy=" + searchBy
					+ "&searchQuery=" + searchQuery;
		}