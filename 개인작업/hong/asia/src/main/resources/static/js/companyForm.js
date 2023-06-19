$(document).ready(function() {
            var submitEmail = false;

            function showErrorMsg(obj, msg) {
                obj.attr("class", "error");
                obj.html(msg);
                obj.show();
            }

            function showSuccMsg(obj, msg) {
                obj.attr("class", "succ");
                obj.html(msg);
                obj.show();
            }

            $("#emailsend").click(function() {
                var email = $("#email").val();
                var eMsg = $("#emailMsg");
                var eMsg2 = $("#emailMsg2");
                $.ajax({
                    type: "get",
                    url: "/mail/sendmail",
                    data: {
                        "email": email
                    },
                    success: function(result) {
                        showSuccMsg(eMsg2, "인증번호를 입력해주세요.");
                    },
                    error: function() {
                        showErrorMsg(eMsg, "이메일 발송에 실패하였습니다. 이메일 정보를 다시 확인해주세요.");
                    }
                });
            });


            $("#emailcheck").click(function() {
                var emailcode = $("#emailcode").val();
                var eMsg = $("#emailCheckMsg");
                var eMsg2 = $("#emailCheckMsg2");
                $.ajax({
                    type: "get",
                    url: "/mail/checkcode",
                    data: {
                        "emailcode": emailcode
                    },
                    success: function(result) {
                        if (result.result == true) {
                            showSuccMsg(eMsg2, "인증번호가 일치합니다");
                            submitEmail = true;
                            eMsg.hide();
                        } else {
                            showErrorMsg(eMsg, "인증번호가 일치하지 않습니다.");
                        }
                    },
                    error: function() {
                        showErrorMsg(eMsg, "먼저 인증번호를 발송해주세요.");
                    }
                });
            });

            document.getElementById('btn').onclick = function() {
                var eMsg = $("#emailCheckMsg");
                if (submitEmail == false) {
                    showErrorMsg(eMsg, "이메일 인증이 되어야 합니다.");
                    return false;
                }

                var today = new Date();
                var currentYear = today.getFullYear()

                if (document.getElementById('birth')) {
                    var birth = document.getElementById('birth').value;

                    var age = currentYear - Number(birth.slice(0, 4));

                    document.getElementById('age').value = age + 1;
                }

                var addr2 = document.getElementById('addr2').value;

                var addr3 = document.getElementById('addr3').value;

                document.getElementById('addr').value = addr2 + " " + addr3;

                document.getElementById('frm').submit();
                return false;
            };
        });