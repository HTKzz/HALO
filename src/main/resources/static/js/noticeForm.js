$(document).ready(function() {

        bindDomEvent();

    });

    function bindDomEvent() {
        $(".custom-file-input").on("change", function() {
            var fileName = $(this).val().split("\\").pop(); // 이미지 파일명
            var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출
            fileExt = fileExt.toLowerCase(); // 소문자 변환(이미지 확장자를 소문자로 통일하여 비교하기 위함)

            
            $(this).siblings(".upload-name").val(fileName); <!--라벨 태그 안의 내용을 jquery의.html() 을 이용하여 파일명을 입력해준다.-->
        });
    }