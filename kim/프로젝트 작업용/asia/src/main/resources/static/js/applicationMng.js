$(document).ready(function() {
			$("#searchBtn").on("click", function(e) {
				e.preventDefault();
				page(0);
			});
		});

		function page(page) {
			var searchBy = $("#searchBy").val();
			var searchQuery = encodeURI($("#searchQuery").val());
			location.href = "/admin/applications/" + page + "?searchBy="
					+ searchBy + "&searchQuery=" + searchQuery;
		}