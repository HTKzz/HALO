$("header > nav").on({
				"mouseenter" : function() {
					$(this).find("ul").stop().fadeIn(200);
					$(".menu-item-title:hover").css({
						"font-weight": "bolder"
					});
				},
				"mouseleave" : function() {
					$(this).find("ul").stop().fadeOut(200);
					$(".menu-item-title").css({
						"font-weight": "normal",
						"font-size": ""
					});
				}
			}, ".acc-header-gnb > li");