스프링 부트3와 redis를 활용한 Redis Aside Pattern 연습
1. 유저 정보를 저장
2. 조회할때 레디스에 없으면 db에서 조회 후 redis에 저장
3. 설정한 ttl안에 조회, redis에 있으면 redis에 있는 값 반환
4. 없으면 db에서 쿼리로 조회

jedis를 사용해 redis 값 조회 및 저장
- redisConfig를 직접 작성해 JedisPool을 사용하려 했으나 jmx 중복 오류가 발생
- redisConfig없이 jedis라이브러리만 추가해서 사용

docker를 사용해 redis와 mysql을 실행한 후 진행

결과
- 첫 요청시에만 db에서 조회
- 이후 요청시에는 ttl시간 안에 redis에서 값을 빠르게 조회 후 반환하는 점을 파악할 수 있었음
