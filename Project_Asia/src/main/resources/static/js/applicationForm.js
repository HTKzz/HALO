$(document).ready(function() {
			
			if ($("#app-css").val() == 'hi') {
				document.getElementById('select-seat').style.pointerEvents = 'none';
				document.getElementById('select-programCategory').style.pointerEvents = 'none';
				document.getElementById('select-place').style.pointerEvents = 'none';
			}

            if ($("#select-programCategory").val() == '공연') {
                document.getElementById('inputseat').style.display = 'block';
            } else {
                document.getElementById('inputseat').style.display = 'none';
                $("#select-seat").find("option:eq(0)").prop("selected", true);
            }
            
            $("#select-programCategory").on("change", function(){
                if ($("#select-programCategory").val() == '공연') {
                    document.getElementById('inputseat').style.display = 'block';
                } else {
                    document.getElementById('inputseat').style.display = 'none';
                    $("#select-seat").find("option:eq(0)").prop("selected", true);
                }
            });
			
            bindDomEvent();

        });

        function bindDomEvent() {
            $(".custom-file-input").on("change", function() {
                var fileName = $(this).val().split("\\").pop(); // 이미지 파일명
                var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출
                fileExt = fileExt.toLowerCase(); // 소문자 변환(이미지 확장자를 소문자로 통일하여 비교하기 위함)

                if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png" && fileExt != "bmp") {
                    alert("이미지 파일만 등록이 가능합니다."); <!--파일첨부 시 이미지 파일인지 검사한다.-->
                        $(this).val("");
                    return;
                }

                $(this).siblings(".upload-name").val(fileName); <!--라벨 태그 안의 내용을 jquery의.html() 을 이용하여 파일명을 입력해준다.-->
            });
        }