// 마이페이지

$(document).ready(function){
	
	$("a").click(function() {
    	toggleClass(".myPageForm-header-inner");
	});
	
	
	function change_pw() {
		document.getElementById("password").disabled = false;
		document.getElementById("pw_button").value = "확정";
		document.getElementById("pw_button").style.color = "hotpink";
		document.getElementById("pw_button").setAttribute("onclick", "decide_pw()");
	}
	function decide_pw() {
		document.getElementById("submit").disabled = false;
		document.getElementById("pw2").value = document.getElementById("pw").value;
		document.getElementById("pw").disabled = true;
		document.getElementById("pw_button").disabled = true;
		document.getElementById("pw_button").value = "확정됨";
		document.getElementById("pw_button").style.color = "#ccc";
	}
}