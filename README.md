# ureca-mini-jdbc

LG U+ 유레카 2기 미니 프로젝트 휴대폰 개통관리 프로그램(CRUD)

<img width="376" alt="Image" src="https://github.com/user-attachments/assets/bc8653be-3369-455f-9281-ae0fe6ce21dc" />

# 프로젝트 개요

이 프로젝트는 **휴대폰 개통 관리 시스템**으로, 사용자와 휴대폰 정보를 관리하며 개통 내역을 기록하고 조회할 수 있는 기능을 제공합니다. Java 기반으로 개발되었으며, 데이터베이스 연동을 통해 CRUD 작업을
수행합니다.

## ERD

<img width="931" alt="Image" src="https://github.com/user-attachments/assets/b440f44c-6351-40c1-b07c-837318c4b00a" />

## 패키지별 주요 역할

### 1. `common`

- 공통적으로 사용되는 유틸리티와 설정 파일들을 포함합니다.
    - **`DBManager`**: 데이터베이스 연결 관리 클래스. JDBC를 통해 DB 연결을 설정하고 관리합니다.

### 2. `dto`

- 데이터를 캡슐화하여 DAO와 UI 간에 전달하는 역할을 합니다.
- 각 DTO 클래스는 특정 엔티티의 속성을 정의합니다.
    - **`ActivationDTO`**: 개통 내역의 데이터 구조를 정의.
    - **`PhoneDTO`**: 휴대폰 정보의 데이터 구조를 정의.
    - **`UserDTO`**: 사용자 정보의 데이터 구조를 정의.

### 3. `dao`

- 데이터베이스와의 상호작용을 담당하는 계층입니다.
- 각 DAO 클래스는 특정 엔티티(예: 사용자, 휴대폰, 개통 내역)에 대한 CRUD 작업을 수행합니다.
    - **`ActivationDAO`**: 개통 내역과 관련된 데이터베이스 작업 처리 (등록, 조회, 취소).
    - **`PhoneDAO`**: 휴대폰 데이터 관리 (목록 조회, 재고 업데이트 등).
    - **`UserDAO`**: 사용자 정보 관리 (등록, 조회 등).

### 4. `ui`

- 사용자와의 상호작용을 처리하는 계층으로, 화면 구성과 이벤트 처리를 담당합니다.
    - **`UserRegistrationUI`**: 사용자 등록 화면.
    - **`PhoneSelectionUI`**: 휴대폰 선택 및 개통 화면.
    - **`ActivationHistoryUI`**: 개통 내역 조회 및 취소 화면.
    - **공용 컴포넌트**:
        - **`PhoneListPanel`**: 휴대폰 목록 표시 및 선택 기능 제공.
        - **`UIComponents`**: UI 스타일링 및 공용 컴포넌트 생성.

## 주요 기능 요약

1. **사용자 관리**
    - 신규 사용자를 등록하고, 사용자 정보를 검색할 수 있습니다.

2. **휴대폰 관리**
    - 특정 통신사의 휴대폰 목록을 조회하거나 번호 이동 가능한 기기를 확인할 수 있습니다.
    - 재고 관리를 통해 개통 시 재고 감소 및 취소 시 복구를 처리합니다.

3. **개통 관리**
    - 사용자가 선택한 휴대폰을 개통하고, 기존 통신사에서 새로운 통신사로 번호 이동을 기록합니다.
    - 개통 내역을 조회하고 필요 시 개통 취소 기능을 제공합니다.

4. **데이터베이스 연동**
    - MySQL 또는 기타 RDBMS와 연동하여 사용자, 휴대폰, 개통 내역 데이터를 저장하고 관리합니다.

## 실행 화면

### 사용자 등록 화면

<img width="512" alt="Image" src="https://github.com/user-attachments/assets/6a70ba15-db04-4adc-a93a-653d9dfae1f2" />

- 사용자 정보 입력 받아서 등록합니다.
- 등록된 사용자만 휴대폰 목록을 볼 수 있습니다.

### 휴대폰 재고 화면

<img width="612" alt="Image" src="https://github.com/user-attachments/assets/ae4a531d-3b12-4802-9971-03fec71839c4" />

- 사용자가 희망하는 휴대폰 개통 선택인 기기변경과 번호이동에 대한 UI로 해당되는 통신사 목록을 볼 수 있습니다.

<img width="498" alt="Image" src="https://github.com/user-attachments/assets/ed8b0eae-16ae-4966-bae3-0ce5dab7a362" />

- 개통할 시 재고가 감소합니다.

### 개통 내역

<img width="812" alt="Image" src="https://github.com/user-attachments/assets/9bc0ab34-a67e-450b-b513-bf19e1cb1300" />

- 원하는 기기와 통신사 변경까지 마친 정보 조회테이블입니다.

<img width="699" alt="Image" src="https://github.com/user-attachments/assets/7bb730f0-3967-49e7-8737-0f6adafd8a11" />

- 개통 취소할 경우 재고가 다시 증가하고 개통된 내역과 사용자는 삭제됩니다.

<img width="612" alt="Image" src="https://github.com/user-attachments/assets/8036681b-5e65-4a5a-bff3-2ea887c6189e" />