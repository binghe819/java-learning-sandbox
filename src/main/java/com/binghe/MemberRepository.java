package com.binghe;

import java.util.List;
import java.util.Optional;

public class MemberRepository {

    private final List<Member> members;

    public MemberRepository(List<Member> members) {
        this.members = members;
    }

    public Optional<Member> findById(Long id) {
        return members.stream()
            .filter(member -> member.getId().equals(id))
            .findAny();
    }

    public void save(Member member) {
        members.add(member);
    }
}
