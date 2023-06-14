document.addEventListener('DOMContentLoaded', () => {

            const seatContainer = document.querySelector('.seatContainer');

            //좌석 클릭했을때
            seatContainer.addEventListener('click', (e) => {
                var a = $('#hdprice').val();

                if (e.target.className === 'seat') {

                    e.target.className = 'selectedSeat';
                    $('input[id=' + e.target.id + ']').attr('value', 'seat occupied');
                    var selectedSeatCount = document.querySelectorAll('.selectedSeat').length;
                    $('input[id=cnt]').attr('value', selectedSeatCount);
                    $('input[id=price]').attr('value', selectedSeatCount * a);
                    var b = document.querySelectorAll('.selectedSeat');
                    var List = [];
                    for (let i = 0; i < b.length; i++) {
                    	if(i == 0) {
                    		List.push(b[i].innerText);
                    	} else {
                            List.push(' '+b[i].innerText);
                    	}
                    }
                    $('input[id=seat]').attr('value', List);

                } else if (e.target.className === 'selectedSeat') {

                    e.target.className = 'seat';
                    $('input[id=' + e.target.id + ']').attr('value', 'seat');
                    var selectedSeatCount = document.querySelectorAll('.selectedSeat').length;
                    $('input[id=cnt]').attr('value', selectedSeatCount)
                    $('input[id=price]').attr('value', selectedSeatCount * a);
                    var b = document.querySelectorAll('.selectedSeat');
                    var List = [];
                    for (let i = 0; i < b.length; i++) {
                    	if(i == 0) {
                    		List.push(b[i].innerText);
                    	} else {
                            List.push(' '+b[i].innerText);
                    	}
                    }
                    $('input[id=seat]').attr('value', List);
                }
            })
            
            document.getElementById('btn').onclick = function() {
            	
            	var seat = $("#seat").val();
            	
                if (seat == "") {
                    alert("좌석을 선택해주세요.");
                    return false;
                }
                
            	document.getElementById('frm').submit();
            }
        })

        function fnReplace(val) {
            var ret = 0;
            if (typeof val != "undefined" && val != null && val != "") {
                ret = Number(val.replace(/,/gi, ''));
            }
            return ret;
        }