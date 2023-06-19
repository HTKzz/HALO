$(document).ready(function() {

			$("#refundMsg").on("click", function() {
				if (confirm('환불 완료 상태로 변경 하시겠습니까?')) {

				} else {
					return false;
				}
			});
			
			$("#reservation-cancelMsg").on("click", function() {
	            if (confirm('취소 상태로 변경 하시겠습니까?')) {

	            } else {
	               return false;
	            }
	         });

			$("#searchBtn").on("click", function(e) {
			    e.preventDefault();
			    page(0);
			});
		});
		
		function page(page) {
		    var searchBy = $("#searchBy").val();
			var searchQuery = encodeURI($("#searchQuery").val()); 
		    location.href = "/admin/reservationMng/" + page + "?searchBy=" + searchBy
					+ "&searchQuery=" + searchQuery;
		}