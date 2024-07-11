## 본문 

### JDBC 이해

    ● 설정
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-jdbc'
        compileOnly 'org.projectlombok:lombok'
        runtimeOnly 'com.h2database:h2'
        annotationProcessor 'org.projectlombok:lombok'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'

        //테스트에서 lombok 사용
        testCompileOnly 'org.projectlombok:lombok'
        testAnnotationProcessor 'org.projectlombok:lombok'
    }

    ● TABLE 생성
    drop table member if exists cascade;
    create table member {
        member_id varchar(10),
        money integer not null default 0,
        primary key (member_id)
    };

    insert into member(member_id, money) values ('hi1', 10000);
    insert into member(member_id, money) values ('hi2', 20000);

![application_db](./DB_img/application_db.png)
    - 클라이언트가 애플리케이션 서버를 통해 데이터를 저장하거나 조회하면, 애플리케이션 서버는 다음 과정을 통해서 데이터베이스를 사용한다.

![application_db2](./DB_img/application_db2.png)
    1.커넥션 연결 : 주로 TCP/IP를 사용해서 커넥션을 연결한다.
    2.SQL 전달 : 애플리케이션 서버는 DB가 이해할 수 있는 SQL을 연결된 커넥션을 통해 DB에 전달한다.
    3.결과 응답 : DB는 전달된 SQL을 수행하고 그 결과를 응답한다. 애플리케이션 서버는 응답 결과를 활용한다.

![application_db3](./DB_img/application_db3.png)
    - 문제는 각각의 데이터베이스마다 커넥션을 연결하는 방법, SQL을 전달하는 방법, 그리고 결과를 응답 받는 방법이 모두 다르다는 점이다.  

    1.데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버에 개발된 데이터베이스 사용 코드도 함께 변경해야 한다.
    2.개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로 학습해야 한다.  

    이런 문제를 해결하기 위해 JDBC라는 자바 표준이 등장한다.
    
### JDBC 표준 인터페이스
    JDBC는 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API다.
    JDBC는 데이터베이스에서 자료를 쿼리하거나 업데이트하는 방법을 제공한다.   

![JDBC_interface](./DB_img/application_inface.png)
    - java.sql.Connection - 연결
    - java.sql.Statement - SQL을 담은 내용
    - java.sql.ResultSet - SQL 요청 응답

    - 자바는 이렇게 표준 인터페이스를 정의해두었다. 이제부터 개발자는 이 표준 인터페이스만 사용해서 개발하면 된다.
    그런데 인터페이스만 있다고해서 기능이 동작하지는 않는다. 이 JDBC 인터페이스를 각각의 DB 벤더(회사)에서 자신의 DB에 맞도록 구현해서 라이브러리로 제공하는데, 이것을 JDBC 드라이버라 한다. 예를 들어서
    MySQL DB에 접근할 수 있는 것은 MySQL JDBC 드라이버라 하고, Oracle DB에 접근할 수 있는 것은 Oracle JDBC 드라이버라 한다.

![MySQL_Driver](./DB_img/MySQL_Driver.png) 

    ● 정리
    JDBC의 등장으로 다음 2가지 문제가 해결되었다.
    1.데이터베이스를 다른 종류의 DB로 변경하면 애플리케이션 서버의 DB 사용 코드도 함께 변경해야하는 문제
        - 애플리키이션 로직은 이제 JDBC 표준 인터페이스에만 의존한다.
        따라서 DB를 다른 종류의 DB로 변경하고 싶으면 JDBC 구현 라이브러리만 변경하면 된다. 따라서 다른 종류의 DB로 변경해도 애플리케이션 서버의 사용 코드를 그대로 유지할 수 있다.

    2.개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로 학습 해야하는 문제.
        - 개발자는 JDBC 표준 인터페이스 사용법만 학습하면 된다. 한번 배워두면 수십개의 데이터베이스에 모두 동일하게 적용할 수 있다.

    ● 참고 - 표준화의 한계
    JDBC의 등장으로 많은 것이 편리해졌지만, 각각의 데이터베이스마다 SQL, 데이터타입 등의 일부 사용법은 다르다.
    결국 데이터베이스를 변경하면 JDBC 코드는 변경하지 않아도 되지만 SQL은 해당 데이터베이스에 맞도록 변경해야한다.
    참고로 JPA를 사용하면 이렇게 각각의 데이터베이스 마다 다른 SQL을 정의해야 하는 문제도 많은 부분 해결할 수 있다.

### JDBC와 최신 데이터 접근 기술
    JDBC를 편리하게 사용하는 다양한 기술이 존재한다. 대표적으로 SQL Mapper와 ORM 기술로 나눌 수 있다.        

![JDBC](./DB_img/JDBC.png)    

    ● SQL Mapper
        - 장점 : JDBC를 편리하게 사용하도록 도와준다.
          - SQL 응답 결과를 객체로 편리하게 변환해준다.
          - JDBC의 반복 코드를 제거해준다.
        - 단점 : 개발자가 SQL을 직접 작성해야한다.
        - 대표기술 : 스프링 JdbcTemplate, MyBatis

![ORM](./DB_img/ORM.png)
    
    ● ORM
        - ORM은 객체를 관계형 데이터베이스 테이블과 매핑해주는 기술이다. 이 기술 덕분에 개발자는 반복적인 SQL을 직접 작성하지 않고, ORM 기술이 개발자 대신에 SQL을 동작을 만들어 실행해준다. 추가로 각각의 데이터베이스마다 다른 SQL을 사용하는 문제도 중간에서 해결해준다.
        - 대표기술 : JPA, 하이버네이트, 이클립스링크
        - JPA는 자바 진영의 ORM 표준 인터페이스이고, 이것을 구현한 것으로 하이버네이트와 이클리스 링크등의 구현 기술이 있다.

    ● SQL Mapper vs ORM 기술
    SQL Mapper와 ORM 기술 둘다 각각 장단점이 있다.
    쉽게 설명하자면 SQL Mapper는 SQL만 작성할 줄 알면 사용 가능하다. ORM기술은 SQL 자체를 작성하지 않아도 되어서 개발 생산성이 매우 높아진다.            