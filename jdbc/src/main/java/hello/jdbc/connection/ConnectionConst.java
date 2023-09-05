package hello.jdbc.connection;

public abstract class ConnectionConst { // 상수이기 때문에 abstract 로 객체 생성을 막아 놓았다.
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
