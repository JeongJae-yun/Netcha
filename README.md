# Netcha

[개발환경]
Eclipse EE, Android Studio, Mysql

[개발순서]
1) Android Studio 이용 어플 화면 및 구현 기능 개발
2) DB 연결을 위해 Server를 tomcat으로 쓰고 Mysql과 연결하여 json형식으로 android 전송

[사용API]
- TheMovieDB 영화정보 API (순위별 영화에서 포스터 보여주기 위한 용도)
- youtube 트레일러 API (영화 상세 페이지에서 예고편 보여주기 위한 용도)
- google maps/ places API (위치기반 검색 및 영화관 탐색 용도)

[주요기능]
- 로그인/ 회원가입(Web Server - DB연동)
- 영화 정보에 대한 줄거리 및 예고편 영상 제공
- 사용자의 영화로 추가 기능 (Web Server - DB연동)
- 타인의 영화평을 볼 수 있으며 사용자 역시 영화에 대한 평을 남길 수 있는 커뮤니티 기능(Web Server - DB연동)
- 자신의 위치를 지준으로 주변 영화관(megabox, lotte cinema, cgv) 분류해서 보여준 이후 현재위치로부터 직선거리 보여주는 기능

