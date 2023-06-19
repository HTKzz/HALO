$(document).ready(function() {
			$("#my-refund").on("click", function(){
				if(confirm('환불 신청을 하시겠습니까?')){
					
				} else {
					return false;
				}
			});
			$("#my-cancelBtn").on("click", function(){
				if(confirm('취소완료를 하시겠습니까?')){
					
				} else {
					return false;
				}
			});
			$("#my-printBtn").on("click", function(){
				if(confirm('티켓을 인쇄 하시겠습니까?')){
					
				} else {
					return false;
				}
			});
			
			var IMP = window.IMP; 
		    IMP.init('imp32626541');
		});
		
		function requestPay(num, name, price) {
			
			if(confirm('결제를 하시겠습니까?') ? true : false){
				IMP.request_pay({
			    	pg: "kcp.store-24677c12-63",
			        pay_method: "card",
			        merchant_uid: 1223423, // 임의의 번호 사용 후 num 으로 바꿔야 함
			        name: name,
			        amount: price
			    }, function (rsp) { // callback
			    	if ( rsp.success ) {
			   	      // 결제 성공 시 로직
			   	      alert('결제가 완료되었습니다.');
			   	      // DB에 결과 저장 로직 구현
			   	      
					  document.getElementById('frm').submit();
			   	      
			   	    } else {
			   	      // 결제 실패 시 로직
			   	      var msg = '결제에 실패하였습니다.';
			   	      msg += '에러내용 : ' + rsp.error_msg;
			   	      alert(msg);
			   	    }
			    });
		    
		    }else{
				return false; 
			}
		}