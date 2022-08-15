# 8.1 JDBC 프로그래밍의 단점을 보완하는 스프링
- JDBC 프로그래밍은 비즈니스 로직 외 DB 통신을 위한 사전 작업이 필수로 들어가야 한다.
```java
    // 사전작업 - 커넥션, 파라미터를 받는 PreparedStatement, 결과를 받는 ResultSet 미리 선언
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
    // 비즈니스 로직 -> 생략
    } catch (SQLException e) {
    e.printStackTrace();
    throw e;
    } finally {
    // 존재할 경우 닫아서 자원을 해제해야 한다. -> 메모리 낭비 방지
    if (rs != null) rs.close();
    
    if (pstmt != null) pstmt.close();
    
    if (conn != null) conn.close();
    }
```
- 스프링의 JdbcTemplate 클래스를 통해 이 과정을 생략하고 스프링에게 맡길 수 있다.
```java
List<Member> results = jdbcTemplate.query(
        "SELECT * FROM MEMBER M1 WHERE M1.EMAIL = ?",
        new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
                var member = new Member(rs.getString("EMAIL"),
                    // 중략
                );
                member.setId(rs.getLong("ID"));
                return member;
            }
        });
return results.isEmpty() ? null : result.get(0);
```
- 또한 JDBC 프로그래밍은 Autocommit 이 기본값이기 때문에 수동으로 커밋하려는 경우, 옵션을 비활성화하고 커밋해야 한다.
```java
try {
    conn.setAutoCommit(false);
    // 중략
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
}
```
- 스프링의 경우, @Transactional 을 메소드에 적용하여 메소드 단위로 트랜잭션을 적용시킬 수 있다.(기본값 Autocommit)

# 8.2 프로젝트 준비
- 커넥션풀
   * 커넥션 생성시간을 줄이기 위해 미리 생성해둔 커넥션을 저장하는 장소
   * 상용 서비스에서는 데이터베이스 부하를 일정하게 유지하기 위해 커넥션 풀을 사용하고 커넥션 갯수를 조절한다.
   * 대표적인 예르 HikariCP 를 사용한다.

# 8.3 DataSource 설정
- 스프링에서는 DataSource 를 통해 커넥션을 관리할 수 있다.
```java
Connection conn = null;
try {
    // dataSource 는 bean 으로 관리된다.
    conn = dataSource.getConnection(); // Tomcat JDBC 는 javax.sql.DataSource 를 구현한 클래스를 제공한다.
}
```
```java
@Configuration
public class DbConfig {

	@Bean(destroyMethod = "close") // 빈 해제 시, 데이터소스 자원을 닫아 메모리 낭비를 방지한다.
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver"); // 드라이버 설정
		ds.setUrl("jdbc:mysql://localhost/spring5-practice?characterEncoding=utf8"); // DB URL 설정
		ds.setUsername("spring5"); // ID
		ds.setPassword("spring5"); // PWD
		ds.setInitialSize(2); // 커넥션풀 초기 커넥션 갯수
		ds.setMaxActive(10); // 커넥션풀 최대 커넥션 갯수
		ds.setTestWhileIdle(true); // 유휴상태 커넥션 테스트
		ds.setMinEvictableIdleTimeMillis(60000 * 3); // 유휴상태 커넥션 유지시간 -> 이후 최소 갯수를 제외하고 해제됨.
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000); // 유휴 커넥션 검사 주기 -> 유휴 커넥션이 DB와 실제로 연결되어 있는지 검사한다.
		return ds;
	}
}
```
- 커넥션 풀은 활성/유휴 상태가 있다.
- 커넥션 풀에 요청하여 커넥션을 가져다 쓰는 경우 해당 커넥션은 `활성(active)` 상태가 된다.
- 커넥션 풀에 다시 반납되면 해당 커넥션은 `유휴(idle)` 상태가 된다.
```java
public void countTest() {
    var dataSourceQuery = new DataSourceQuery(dbAppCtx.getBean(DataSource.class));
    System.out.printf("dataSourceQuery class: %s%n", dataSourceQuery.getClass().getName());

    System.out.printf("maxIdle: %d", dataSourceQuery.getMaxIdle());

    var result = dataSourceQuery.count();
    System.out.println(result);
}
```
![DataSource 예제](https://user-images.githubusercontent.com/43669379/183298472-6a84c08c-5c03-4f9f-b62d-108c9b60eb22.png)

# 8.4 JdbcTemplate 을 이용한 쿼리 실행
- 스프링 JdbcTemplate 를 사용하면 DataSource나 Connection 등 직접적인 설정을 간소화할 수 있다.
```java
private JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource); // JdbcTemplate 에 dataSource 의존성 주입
	}

	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?",
				new RowMapper<Member>() {
					@Override
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
						Member member = new Member(
								rs.getString("EMAIL"),
								rs.getString("PASSWORD"),
								rs.getString("NAME"),
								rs.getTimestamp("REGDATE").toLocalDateTime());
						member.setId(rs.getLong("ID"));
						return member;
					}
				}, email);

		return results.isEmpty() ? null : results.get(0);
	}
}
```
```java
@Configuration
public class DbConfig {

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver"); // 드라이버 설정
		ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8"); // DB URL 설정
		ds.setUsername("spring5"); // ID
		ds.setPassword("spring5"); // PWD
		ds.setInitialSize(2); // 커넥션풀 초기 커넥션 갯수
		ds.setMaxActive(10); // 커넥션풀 최대 커넥션 갯수
		ds.setTestWhileIdle(true); // 유휴상태 커넥션 테스트
		ds.setMinEvictableIdleTimeMillis(60000 * 3); // 유휴상태 커넥션 유지시간 -> 이후 최소 갯수를 제외하고 해제됨.
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000); // 유휴 커넥션 검사 주기
		return ds;
	}
	
    // 의존성 주입을 위해 Bean 등록
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}
	
}
```
```java
@Test
public void selectTest() {
    // given
    var errorEmail = "eeeee";

    // when
    var result = memberDao.selectByEmail(errorEmail);

    // then
    assertThat(result).isNull();
}
```
![selectByEmail 예제](https://user-images.githubusercontent.com/43669379/183299089-9165cb88-cc1f-4f41-935d-7be68c2670ce.png)

- KeyHolder 를 이용하여 AutoIncrement 로 자동생성된 키 값을 확인할 수 있다.
```java
public Member insert(Member member) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
        // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
        PreparedStatement pstmt = con.prepareStatement(
                "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
                        "values (?, ?, ?, ?)",
                new String[] { "ID" });
        // 인덱스 파라미터 값 설정
        pstmt.setString(1, member.getEmail());
        pstmt.setString(2, member.getPassword());
        pstmt.setString(3, member.getName());
        pstmt.setTimestamp(4,
                Timestamp.valueOf(member.getRegisterDateTime()));
        // 생성한 PreparedStatement 객체 리턴
        return pstmt;
    }, keyHolder);
    Number keyValue = keyHolder.getKey();
    member.setId(keyValue.longValue());

    return member;
}
```
```java
@Test
public void insertTest() {
    // given
    var member = new Member("email3", "pwd", "name", LocalDateTime.now());

    // when
    var result = memberDao.insert(member);

    // then
    assertThat(result.getClass()).isEqualTo(Member.class);
    assertThat(result.getEmail()).isEqualTo(member.getEmail());
    System.out.printf("inserted memberId: %d\n", result.getId());
}
```
![KeyHolder 예제](https://user-images.githubusercontent.com/43669379/183299723-59ec519d-b1b0-4a89-ab26-6a78203dbf93.png)

# 8.7 트랜잭션 처리
- 하나의 비즈니스 로직에서 여러 쿼리 실행 시, 일부 쿼리가 실패했을 때 성공한 쿼리는 롤백되어야 한다.
- 이렇듯 관계된 여러 쿼리를 하나의 작업으로 묶어주는 것을 `트랜잭션` 이라고 한다.
- 트랜잭션 내 일부 쿼리 실패 시, 성공 쿼리를 이전 상태로 되돌릴 수 있으며 이를 `롤백` 이라고 한다.
- 트랜잭션 내 모든 쿼리 성공 시, 실제 DB에 반영하는 작업을 `커밋` 이라고 한다.
- 트랜잭션 커밋/롤백 처리는 설정하기 나름이기 때문에 적용 범위 및 롤백 여부를 잘 설정해야 한다.

### 8.7.1 @Transactional 을 이용한 트랜잭션 처리
- 스프링에서는 @Transactional 을 통해 메소드 단위로 트랜잭션 처리를 할 수 있다.
```java
@Transactional
public void changePassword(String email, String oldPwd, String newPwd) {
    Member member = memberDao.selectByEmail(email);
    if (member == null)
        throw new MemberNotFoundException();

    member.changePassword(oldPwd, newPwd);

    memberDao.update(member);
}
```
- 또한 위 어노테이션이 동작할 수 있도록 @EnableTransactionManagement 설정이 필요하다.
```java
@Configuration
@EnableTransactionManagement // 설정 후 아래 트랜잭션 매니저 빈 등록
public class AppCtx {

    /* 중략 */

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
```
![트랜잭션-성공-커밋](https://user-images.githubusercontent.com/43669379/184634013-4950a907-061c-4608-952e-b13b3238de5d.png)

### 8.7.2 @Transactional과 프록시
- 비즈니스 메소드에 어노테이션만으로 트랜잭션 처리를 하기 위해 스프링은 AOP 방식으로 트랜잭션을 구현한다.
- 스프링은 AOP 를 프록시 객체를 통해 구현하기 때문에 @Transactional 또한 `프록시 방식으로 구현`된다.
- @EnableTransactionManager 어노테이션 사용 시, 스프링은 @Transactional 선언된 빈 객체를 가져와 알맞은 프록시 객체를 생성한다.

### 8.7.3 @Transactional 적용 메서드의 롤백 처리
- 프록시를 통해 트랜잭션이 관리되기 때문에 롤백 역시 프록시 객체가 처리한다.
```java
public class WrongPasswordException extends RuntimeException {
}
```
![트랜잭션-롤백](https://user-images.githubusercontent.com/43669379/184634629-327c428d-45db-4fcd-9d0b-347b4a8b4b2b.png)

- 트랜잭션은 기본적으로 RuntimeException 발생 시에만 롤백한다.
```java
public class WrongPasswordException extends SQLException {
}
```
![트랜잭션-커밋-RuntimeException아닐때](https://user-images.githubusercontent.com/43669379/184636336-a60042ea-1f7d-474d-9787-d64e233d1fec.png)

- 롤백에 포함시키기 위해선 @Transactional 에 rollbackFor 설정을 추가하면 된다.
  ![트랜잭션-롤백-클래스추가](https://user-images.githubusercontent.com/43669379/184637169-e9ed25f3-cb4c-46ba-a7fd-3c2ac3bd69e8.png)

- 롤백 클래스를 포함시켜도, RuntimeException 은 기본값으로 항상 롤백 대상에 포함된다.
```java
public class WrongPasswordException extends RuntimeException {
}
```
![트랜잭션-롤백-Runtime은항상포함](https://user-images.githubusercontent.com/43669379/184637506-fa47ca52-6684-40b9-a7a8-c89899889161.png)

### 8.7.4 @Transactional의 주요 속성
- 어노테이션의 주요 속성 중 하나는 Propagation(전파레벨) 이다.

| 값            | 설명                                                                                               |
|--------------|--------------------------------------------------------------------------------------------------|
| REQUIRED     | 트랜잭션이 필요하다는 의미. <br/>트랜잭션이 존재하는 경우 해당 트랜잭션을 사용하며, 없는 경우 새로 생성한다.                                 |
| MANDATORY    | 트랜잭션이 필요하다는 의미. <br/>기존 트랜잭션이 없는 경우, 예외를 발생시킨다.                                                  |
| REQUIRES_NEW | 항상 새로운 트랜잭션 생성.<br/>기존 트랜잭션이 있는 경우, 일시중지시킨 뒤 새로운 트랜잭션이 종료되면 계속 진행된다.                             |
| SUPPORTS     | 트랜잭션 없이 메서드 진행<br/>기존 트랜잭션이 있는 경우, 기존 트랜잭션을 같이 사용한다.                                             |
| NOT_SUPPORTED | 트랜잭션없이 메서드 진행.<br/>기존 트랜잭션이 있는 경우, 중지시킨뒤 메서드 진행 후 트랜잭션을 다시 진행한다.                                 |
| NEVER        | 트랜잭션 없이 메서드 진행.<br/>기존 트랜잭션이 있는 경우, 예외를 발생시킨다.                                                   |
| NESTED       | 트랜잭션을 항상 생성한다.<br/>기존 트랜잭션이 있는 경우, 내부 트랜잭션을 만든 뒤 메서드를 실행한다.<br/>JDBC3.0 드라이버 등 버전에 따라 지원여부가 상이함. |

- isolation(격리수준)

| 값                | 설명                                                                  |
|------------------|---------------------------------------------------------------------|
| DEFAULT          | 기본 설정을 사용                                                           |
| READ_UNCOMMITTED | 다른 트랜잭션이 커밋하지 않은 데이터 조회 가능                                          |
| READ_COMMITTED   | 다른 트랜잭션이 커밋한 데이터 조회 가능                                              |
| REPEATABLE_READ  | 트랜잭션 내 여러 차례 조회한 데이터는 동일한 값 조회 가능                                   |
| SERIALIZABLE     | 동일한 데이터에 대해 하나의 트랜잭션만 설정 가능<br/>기존 트랜잭션 완료 전까지 해당 데이터에 모든 작업 진행 불가. |

### 8.7.6 트랜잭션 전파
- @Transactional 의 기본 전파레벨(Propagation) 은 REQUIRED
```java
	/**
	 * The transaction propagation type.
	 * <p>Defaults to {@link Propagation#REQUIRED}.
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#getPropagationBehavior()
	 */
	Propagation propagation() default Propagation.REQUIRED;
```
- 따라서 트랜잭션 내 트랜잭션이 설정된 경우, 전파레벨에 따라 트랜잭션은 다르게 생성/관리 된다.
- JdbcTemplate 은 상위 트랜잭션이 존재할 경우, 해당 트랜잭션 범위 내에서 쿼리를 수행한다.
