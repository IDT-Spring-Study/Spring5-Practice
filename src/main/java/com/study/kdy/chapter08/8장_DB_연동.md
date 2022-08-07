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