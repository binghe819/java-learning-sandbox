# 자바 놀이터 - Mockito

<br>

# @InjectMocks
> 1.8.3부터 새로 생긴 기능이다.

`@InjectMocks`: Mock 혹은 Spy 필드들을 자동으로 주입해준다.

Mockito가 특정 Mock 객체 안에 Mock객체를 주입해준다는 것을 의미한다.

**주의할 점은 해당 객체는 Mocking하지 않고, 내부 상태만 Mock객체를 주입해준다.**

```java
// 직접 Mock 주입 코드

@Mock
private MemberRepository memberRepository;

private MemberService memberService = new MemberService(memberRepository);

// @InjectMocks 사용 코드
@Mock 
private MemberRepository memberRepository;

@InjectsMock
private MemberService memberService;
```

* [@InjectsMock을 사용하지 않고 똑같은 효과를 낸 코드 - 직접 Mock 객체 주입](./src/test/java/com/binghe/NonInjectMocksTest.java)
* [@InjectsMock을 사용하여 대신 Mock 객체 주입](./src/test/java/com/binghe/InjectMocksTest.java)

<br>

> 참고: [Mockito docs](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#21)

<br>
