package com.binghe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Mockito를 목 테스트 - MemberRepository를 목으로 만들어 MemberService를 테스트한다.")
@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("멤버를 찾는다.")
    @Test
    void findById() {
        // given
        Member member = new Member(1L, "binghe");
        MemberService memberService = new MemberService(memberRepository);

        // when
        when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(member));

        // then
        Member findMember = memberService.findById(1L);
        verify(memberRepository, times(1)).findById(1L);
        assertAll(
            () -> assertThat(findMember.getId()).isEqualTo(member.getId()),
            () -> assertThat(findMember.getName()).isEqualTo(member.getName())
        );
    }

    @DisplayName("존재 하지 않는 멤버일 경우, 예외를 던진다.")
    @Test
    void findById_fail() {
        // given
        MemberService memberService = new MemberService(memberRepository);

        // when
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> {
            Member findMember = memberService.findById(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
