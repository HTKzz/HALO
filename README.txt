- 가입하기(책내용참조)
1. https://github.com에 접속하여 [Sign Up] 클릭한다.
2. [Enter your email]에 이메일 입력하고 [Continue]버튼을 클릭한다.
3. [Create a password]에 패스워드 입력하고 [Continue]버튼을 클릭한다.
4. [Enter a username]에 이름 입력하고 [Continue]버튼을 클릭한다.
5. 공지 이메일을 받을지 물어보면 'y' 입력하고 [Continue]버튼을 클릭한다. *전 n으로함
6. 사람임을 인증하는 단계에서 나선 은하 두번 정도 선택하면 v로 체크되고 인증이 완료된다. 인증 후 [Create account] 버튼을 클릭.
7. 입력했던 이메일을 열어보면 GitHub에서 보낸 코드를 확인할 수 있다. [Enter code]에 해당 코드를 입력한다.
8. 추가로 설문을 위한 화면에서는 하단의 [Skip personalization]을 클릭한다.

- 설치하기
1. 깃허브 안에 깃허브 설치파일 다운로드한다
2. 다운받은 Git 설치 파일을 실행하면 설치창이 뜬다. 기본 설정을 유지한 채 계속 [Next]를 누르고 마지막 [Install] 클릭한다.
3. 마지막 화면에서 [View Release Notes]를 체크하지 않고 [Finish]버튼을 클릭한다.

- 사용하기전
1. 사용할 폴더 만들기
2. 폴더 우클릭후 git bash here 클릭
$ git config --global user.email "자신의 것" 엔터
$ git config --global user.name "자신의 것" 엔터

- 깃허브 프로그램 명령어 
컨트롤+insert = 복사
쉬프트+insert = 붙여넣기

- 본격 사용하기
1. 깃허브 폴더 허가? 얻기
git init

폴더안에 .git 파일 생겼나 확인하기 *숨김파일로 생기니 숨김파일보기설정

2. PM 깃허브 연결하기
git remote add origin https://github.com/HTKzz/HALO.git

3. 깃허브 폴더전체 다운로드 받기
git pull origin master

4. 파일/폴더 올리기
git add "파일이름.확장자명"      ex) README.txt / 폴더는 git add 폴더명

git add . << 폴더안에 파일전체가 올라가서 절대사용금지!!!!!!!!!

5. commit 하면서 *코멘트 달기*
git commit -m "메시지"

6. 올리기(절대 push 관련 다른명령어 사용금지!!!!!!!!!!!!)
git push origin master



-- 오류정보
** remote origin already exists. 뜨는건 remote add가 이미 되어있음
** 'origin' does not appear to be a git repository     2번 실행


** failed to push some refs to      3번 실행



