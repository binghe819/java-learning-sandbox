package com.binghe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NonInjectMocksTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    void injectMocks() {
        // given
        Member member = new Member(1L, "binghe");

        // mock
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        // when
        Member findMember = memberService.findById(1L);

        // then
        assertThat(member.getName()).isEqualTo(findMember.getName());
        verify(memberRepository, times(1)).findById(1L);
    }
}
