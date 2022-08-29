# Spring Framework5 스터디 계획서
### 목차
1. [목표](#1-목표)
2. [진행 방식](#2-진행-방식)
3. [발표 양식](#3-발표-양식)
   * [발표자 준비](#발표자-준비)
   * [발표자료 관리](#발표자료-관리)
   * [예제코드 관리](#예제코드-관리)
   * [발표 준비절차](#발표-준비절차)
4. [진행 일정](#4-진행-일정)
5. [참고](#참고)
   * [커밋 컨벤션](#커밋-컨벤션)

---

### 1. 목표
- 스프링 기초 지식 습득
- 매주 발표를 통한 면접 대비

### 2. 진행 방식
- [Spring 5 프로그래밍 입문](http://www.yes24.com/Product/Goods/62268795) 책을 기준으로 스터디   
- 책의 챕터(목차) 별 1~2주로 나누어 매주 월요일 발표
- 발표자 랜덤 추첨 -> 모든 사람이 발표 준비

### 3. 발표 양식
#### 발표자 준비
  + 담당 챕터 발표 자료 준비
  + 노트북, 책 준비
  ```
  발표자 및 참여자는 책에 없는 챕터 관련 질문을 준비할 경우 모두가 모를 수 있기 때문에 
  공부 해오거나 사전 공유해서 대화가 될 수 있도록 조치할 것.
  ```
  
#### 발표자료 관리
1) 깃허브 이슈
2) 파워포인트
3) 노션
4) etc

#### 예제코드 관리
* 조직 레포지토리 main 브랜치에 개인 패키지 별로 예제 푸시

#### 발표 준비절차
  1. 발표 자료 작성 -> 예제 설명 및 라이브 코딩 위주 준비
  2. main 브랜치 개인 패키지에 푸시
  3. 발표 진행
  4. 발표 후 발표자 및 발표자료 링크 업데이트

### 4. 진행 일정
2022.06.20(월) ~ 2022.10.17 (18주)   
`챕터 당 평균 25페이지 내외로 되어있어 25페이지를 1주 발표 기준으로 설정함.`

|챕터|제목|발표일|발표자|발표 자료|
|-|-|-|-|-|
|3|스프링 DI|2022.06.20|김동영|[김동영_깃허브_발표자료](https://github.com/IDT-Spring-Study/Spring5-Practice/issues/1)|
|3|스프링 DI|2022.06.27|박회재|[박회재_노션_발표자료](https://cuboid-expert-37b.notion.site/Spring5-d4c1683f9c8f4cf0b68792ab1f608b64)|
|4|의존 자동 주입|2022.07.04|홍승훈|[홍승훈_노션_발표자료](https://www.notion.so/Chapter-4-631fa59be76a4c6cb918e40da7f9d77e)|
|5|컴포넌트 스캔|2022.07.11|김태수|[김태수_티스토리_발표자료](https://lollolzkk.tistory.com/113?category=0)|
|6|빈 라이프사이클과 범위|2022.07.18|홍승훈|[홍승훈_노션_발표자료](https://www.notion.so/Chapter-6-638f395120a24d7d9e7f1bc85d235579)|
|7|AOP 프로그래밍|2022.08.01|김동영|[김동영_깃허브_발표자료](https://github.com/IDT-Spring-Study/Spring5-Practice/blob/5035cf90b1f264b14ebac228e40e66cb2a82f661/src/main/java/com/study/kdy/chapter07/7%EC%9E%A5_AOP_%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D.md)|
|8|DB 연동|2022.08.08|김태수|[김태수_티스토리_발표자료](https://lollolzkk.tistory.com/117)|
|8|DB 연동|2022.08.16|박회재|[박회재_노션_발표자료](https://cuboid-expert-37b.notion.site/Transaction-61ee1ad067004cca90ea04c41fea7a7f),&nbsp;[김동영_깃허브_발표자료](https://github.com/IDT-Spring-Study/Spring5-Practice/blob/775ec96a95df4db83acf4d58e8ee412cfd268c73/src/main/java/com/study/kdy/chapter08/8%EC%9E%A5_DB_%EC%97%B0%EB%8F%99.md)|
|9|스프링 MVC 시작하기|2022.08.22|홍승훈|[홍승훈_노션_발표자료](https://www.notion.so/Chapter-9-MVC-49e600d40998401b83b1376f83f3da89)|
|10|스프링 MVC 프레임워크 동작 방식|2022.08.29|김동영|[김동영_깃허브_발표자료](https://github.com/IDT-Spring-Study/Spring5-Practice/blob/9250f7e121d337f330c547d14f085ca79f44bd96/src/main/java/com/study/kdy/chapter10/10%EC%9E%A5_%EC%8A%A4%ED%94%84%EB%A7%81_MVC_%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC_%EB%8F%99%EC%9E%91_%EB%B0%A9%EC%8B%9D.md)|
|11|MVC 1: 요청 매핑, 커멘드 객체,<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;리다이렉트, 폼 태그, 모델|2022.09.06|TBD|1~10/14장 발표|
|11|MVC 1: 요청 매핑, 커멘드 객체,<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;리다이렉트, 폼 태그, 모델|2022.09.12|TBD|11~14/14장 발표, 추석|
|12|MVC 2: 메시지, 커맨드 객체 검증|2022.09.19|TBD||
|13|MVC 3: 세션, 인터셉터, 쿠키|2022.09.26|TBD||
|14|MVC 4: 날짜 값 변환, @PathVariable, 익셉션 처리|2022.10.03|TBD|개천절|
|15|간단한 웹 어플리케이션의 구조|2022.10.10|TBD|7장이라 내용 보충 필요. 대체공휴일(한글날)|
|16|JSON 응답과 요청 처리|2022.10.17|TBD||
|17|프로필과 프로퍼티 파일|2022.10.24|TBD|발표자 선정 후 발표여부 확인 필요.|

### 참고
#### 커밋 컨벤션
  1. 커밋 종류   
     |type|desc|
     |--|--|
     |feat|새로운 기능 추가|
     |fix|버그 수정|
     |docs|문서 수정|
  2. 제목(필수)
     - `커밋 종류: 커밋 내용` -> 50자 미만, 마침표 없이 작성
     - 부족할 경우, 본문에 상세한 내용 작성
  3. 본문(선택)
     - 제목에서 두 줄 띄고 작성 -> 깃에서 제목과 본문을 구분하는 방식
     - 한 줄당 72자 내로 작성
     - 최대한 상세하게 작성할 것
  4. 예시
     ```
     feat: 챕터3 스프링 DI 1~5장 예제 코드 추가
  
     1. 스프링 DI 기준 예제 코드 작성
     2. 5장 조립코드(Assembler) 는 6장 예제 코드로 대체되기 때문에 생략함.
     ```
