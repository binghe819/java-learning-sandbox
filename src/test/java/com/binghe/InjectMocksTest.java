package com.binghe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InjectMocksTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void dependency() {
        assertThat(memberRepository).isNotNull();
        assertThat(memberService).isNotNull();
    }

    @DisplayName("@InjectsMocks를 사용하면 MemberService안에 MemberRepository를 자동으로 주입해준다.")
    @Test
    void injectMocks() {
        // given
        Member member = new Member(1L, "binghe");

        // mock
        given(memberRepository.findById(1L)).willReturn(Optional.ofNullable(member));

        // when
        Member findMember = memberService.findById(1L);

        // then
        assertThat(member.getName()).isEqualTo(findMember.getName());
        verify(memberRepository, times(1)).findById(1L);
    }
}
