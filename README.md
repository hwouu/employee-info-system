# 직원 정보 관리 시스템

**직원 정보 관리 시스템**은 Java와 MySQL을 기반으로 개발된 애플리케이션으로, 직관적인 사용자 인터페이스(UI)를 통해 직원 데이터를 효율적으로 관리할 수 있습니다. 생성, 검색, 수정, 그룹화, 삭제와 같은 다양한 기능을 제공합니다.

---

## 🎯 주요 기능
- **직원 관리**:
  - 직원 생성, 수정, 삭제.
  - 상사-부하 관계 및 부서 할당과 같은 복잡한 관계 처리.
- **검색 기능**:
  - 다양한 속성(예: 이름, 성별, 주민등록번호, 부서 이름)으로 검색.
  - 조건 기반의 고급 검색 지원.
- **그룹화 및 통계**:
  - 성별, 부서, 상사 등으로 그룹화하여 평균 급여 및 직원 수 확인.
- **동적 테이블 업데이트**:
  - 테이블 컬럼의 표시/숨기기 설정 가능.
  - 컬럼 헤더를 클릭하여 정렬 기능 제공.
- **강력한 오류 처리**:
  - 날짜, 급여, 외래 키 제약 조건 등에 대한 유효성 검사.
  - 잘못된 작업에 대한 상세 오류 메시지 팝업 제공.

---

## 📁 프로젝트 구조

### 백엔드
- **`DatabaseConnection`**:
  - MySQL 데이터베이스와의 연결을 처리.
- **`EmployeeSearch`**:
  - 데이터베이스의 CRUD 작업 수행.
  - 그룹화, 검색, 고급 필터링 포함.

### 프론트엔드
- **`EmployeeUI`**:
  - 사용자와 상호작용할 수 있는 직관적인 GUI 제공.
  - 버튼, 드롭다운, 동적 테이블 렌더링 포함.

---

## ⚙️ 사용 기술
- **프로그래밍 언어**: Java
- **데이터베이스**: MySQL
- **UI 프레임워크**: Swing
- **빌드 도구**: Gradle/Maven (선택사항)

---

## 🚀 시작하기

### 사전 준비
1. **Java JDK 8 이상**
2. **MySQL 데이터베이스**
3. **MySQL Connector/J**: JDBC 드라이버 설치 필요.
4. **IDE**: IntelliJ IDEA, Eclipse 또는 선호하는 Java IDE.

### 설치 방법
1. **레포지토리 클론**:
   ```bash
   git clone https://github.com/<username>/<repository>.git
   cd <repository>
   ```
2. **데이터베이스 설정**:
   - `COMPANY.sql` 스키마를 MySQL 데이터베이스에 가져오기.
   - `DatabaseConnection.java`에서 연결 정보를 올바르게 구성.
3. **프로젝트 실행**:
   - IDE에서 프로젝트 열기.
   - `Main.java` 파일을 컴파일하고 실행.

---

## 🛠️ 구성 방법

### 데이터베이스 연결 설정
`DatabaseConnection.java`에서 다음 내용을 수정:
```java
private static final String URL = "jdbc:mysql://localhost:3306/COMPANY";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

---

## 📊 세부 기능

### 직원 CRUD 작업
- 직원 추가:
  - 필드: 이름, 성, 주민등록번호, 급여, 상사, 부서 등.
- 특정 필드 동적 수정.
- 외래 키 제약 조건 확인 및 삭제.

### 고급 검색
- 다양한 속성으로 검색:
  - 예: `Department Name`, `Salary > 50000`, `Gender = M`.
- 그룹화된 검색을 통해 통계 제공.

### 동적 테이블 커스터마이징
- 체크박스를 통해 컬럼 표시/숨기기 설정.
- 사용자 선호에 따라 테이블 업데이트.

---

## 📄 라이선스
이 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

---

## 📬 연락처
질문이나 피드백이 있으시면 [nhw3990@gmail.kr](mailto:nhw3990@gmail.com)으로 연락주세요.
```

원하시는 대로 수정하실 수 있습니다! 😊
