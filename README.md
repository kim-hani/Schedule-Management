# Schedule-Management
<br/>

### 스케줄 관리 API 설계

|기능|Method|URL|request|response|상태코드|
|------|-----|---|---|---|---|
|일정 생성|Post|http://localhost:8080/schedules|요청 body|등록 정보|201 : Created|
|일정 전체조회|Get|http://localhost:8080/schedules| 요청 param|전체 응답 정보|200 : OK|
|일정 조회|Get|http://localhost:8080/schedules/{id}|요청 param|단건 응답 정보|200 : OK|
|일정 수정|Put|http://localhost:8080/schedules/{id}|요청 body|수정 정보|200 : OK|
|일정 삭제|Delete|http://localhost:8080/schedules/{id}|요청 param|테스트3|200 : OK|

<br/>

### ERD 작성
![image](https://github.com/user-attachments/assets/fbf81b5c-c214-431a-9f48-7f7ba702e300)
