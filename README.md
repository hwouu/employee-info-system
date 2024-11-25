# 직원 정보 관리 시스템

Java와 MySQL 기반으로 개발된 직원 정보 관리 시스템입니다. 직관적인 사용자 인터페이스와 강력한 데이터베이스 연동을 통해 직원 데이터를 효율적으로 관리할 수 있도록 설계되었습니다.

## 프로젝트 개요

본 시스템은 Java를 활용한 클라이언트 애플리케이션과 MySQL 데이터베이스를 연동하여 직원 정보를 관리합니다. 검색, 추가, 수정, 삭제와 같은 기본적인 기능뿐만 아니라, 데이터 통계 및 조건 검색과 같은 고급 기능도 제공합니다.

### 주요 기능

- **직원 정보 관리**
  - 직원 정보 조회
  - 직원 데이터 추가, 수정, 삭제
  - 조건에 따른 데이터 검색

- **그룹 및 통계**
  - 부서별 평균 급여 조회
  - 성별 및 상사 관계별 직원 수 통계
  - 특정 조건에 따른 데이터 필터링 및 삭제

- **UI 및 사용성**
  - 체크박스를 통한 데이터 컬럼 선택
  - 컬럼 정렬 및 조건 기반 검색 기능

## 기술 스택

- **프로그래밍 언어**
  - Java (Swing을 활용한 UI 구현)

- **데이터베이스**
  - MySQL

- **라이브러리**
  - MySQL Connector/J
  - JDBC (Java Database Connectivity)

## 시작하기

### 필수 조건
- JDK 17 이상
- MySQL Server (8.0 이상)
- MySQL Connector/J 설치

### 설치 및 실행

1. 저장소를 클론합니다.
   ```bash
   git clone https://github.com/hwouu/employee-info-system.git
   cd employee-info-system
   ```

2. MySQL 데이터베이스 설정:
   - `COMPANY` 데이터베이스를 생성하고 `EMPLOYEE` 및 `DEPARTMENT` 테이블을 설정합니다.
   - 제공된 `schema.sql` 파일을 사용하여 테이블을 생성하세요.

3. 프로젝트 실행:
   - IntelliJ IDEA 또는 Eclipse와 같은 IDE에서 프로젝트를 열고 `Main` 클래스를 실행합니다.

### 데이터베이스 연결 설정

`DatabaseConnection.java` 파일의 URL, 사용자 이름, 비밀번호를 수정하여 데이터베이스와 연결합니다.

```java
private static final String URL = "jdbc:mysql://localhost:3306/COMPANY";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

## 프로젝트 구조

```
src/
├── com.company.db/       # 데이터베이스 연동 로직
├── com.company.model/    # 데이터 모델 정의
├── com.company.ui/       # 사용자 인터페이스
└── com.company.Main      # 프로그램 진입점
```

## 기여하기

1. 프로젝트를 Fork합니다.
2. 새 기능 브랜치를 생성합니다. (`git checkout -b feature/AmazingFeature`)
3. 변경 사항을 Commit합니다. (`git commit -m 'feat: Add some AmazingFeature'`)
4. 브랜치에 Push합니다. (`git push origin feature/AmazingFeature`)
5. Pull Request를 생성합니다.

## 라이선스

이 프로젝트는 MIT 라이선스에 따라 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
```

이 내용을 기반으로 저장소와 README.md 파일을 업데이트하세요. 추가적인 요청사항이 있다면 알려주세요!
