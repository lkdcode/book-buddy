# Book Buddy

## 기능 정의서

> 1) _사용자_<br/>
     - 회원가입
>
> 2) _도서관리_<br/>
     - 도서를 등록한다<br/>
     - 등록된 도서를 수정한다<br/>
     - 등록된 도서에 대한 대출이력을 확인한다<br/>
     - 아이디를 입력 후 도서에 대한 대출처리를 한다<br/>
     - 도서에 대하여 반납처리한다<br/>

### 사용자

> 회원가입

- [ ] 이메일(UK), 비밀번호, 이름으로 회원가입을 할 수 있다.<br/>
- [ ] 요청 시 이메일(UK), 비밀번호, 이름은 각각 누락될 수 없다.<br/>
- [ ] 회원 가입 시 이메일(UK)은 중복될 수 없다.<br/>

### 도서

> 도서를 등록한다

- [ ] ISBN(UK), 제목, 저자, 수량으로 도서를 등록 할 수 있다.<br/>
- [ ] 요청 시 ISBN(UK), 제목, 저자, 수량은 각각 누락될 수 없다.<br/>
- [ ] 등록 시 ISBN(UK) 은 중복될 수 없다.<br/>

> 등록된 도서를 수정한다

- [ ] 등록된 도서의 ISBN(UK), 제목, 저자, 수량은 각각 수정할 수 있다.<br/>
- [ ] 요청 시 ISBN(UK), 제목, 저자, 수량은 각각 누락될 수 없다.<br/>
- [ ] 수정 시 ISBN(UK) 은 중복될 수 없다.<br/>

> 등록된 도서에 대한 대출이력을 확인한다

- [ ] 도서의 번호(PK)로 해당 도서의 대출 이력을 조회할 수 있다.<br/>
- [ ] 요청 시 도서의 번호(PK)는 누락될 수 없다.<br/>
- [ ] 대출 이력은 가장 최근 대출 내역부터 내림차순으로 정렬한다.<br/>

> 아이디를 입력 후 도서에 대한 대출 처리를 한다

- [ ] 유저 이메일(UK)과 도서 번호(PK)로 도서 대출을 할 수 있다.<br/>
- [ ] 요청 시 유저 이메일과 도서 번호(PK)를 누락할 수 없다.<br/>
- [ ] 유저가 이미 빌린 책의 중복 대출은 허용하지 않는다.<br/>

> 도서에 대하여 반납처리한다

- [ ] 유저 이메일과 도서 번호(pk)로 도서 반납을 할 수 있다.<br/>
- [ ] 요청 시 유저 이메일과 도서 번호(PK)를 누락할 수 없다.<br/>

## ERD

```mermaid
---
title: Book Buddy ERD
---
erDiagram
    tb_user |o--o{ tb_loan: OneToMany
    tb_book |o--o{ tb_loan: OneToMany
    tb_book |o--o{ tb_loan: OneToMany

    tb_user {
        id BIGINT PK "Auto Increment"
        email VARCHAR(255) UK
        password VARCHAR(255) "NOT NULL"
        name VARCHAR(255) "NOT NULL"
        careted_at DATETIME(6)
        updated_at DATETIME(6)
    }

    tb_book {
        id BIGINT PK "Auto Increment"
        ISBN VARCHAR(20) UK
        title VARCHAR(255) "NOT NULL"
        author VARCHAR(255) "NOT NULL"
        quantity INT "NOT NULL"
    }

    tb_loan {
        id BIGINT PK "Auto Increment"
        book_id BIGINT FK
        user_email VARCHAR(255) FK
        borrowed_at DATETIME(6)
        returned_at DATETIME(6)
    }
```

## API

##

##