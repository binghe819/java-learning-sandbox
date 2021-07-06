package com.binghe;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 유저입니다."));
    }

    public void save(Member member) {
        memberRepository.save(member);
    }
}
