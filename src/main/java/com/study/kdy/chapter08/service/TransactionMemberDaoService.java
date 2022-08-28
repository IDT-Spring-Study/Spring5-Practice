package com.study.kdy.chapter08.service;

import com.study.kdy.chapter08.dto.MemberInsertReqDto;
import com.study.kdy.chapter08.model.Member;
import com.study.kdy.chapter08.model.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class TransactionMemberDaoService {

    private final MemberDao memberDao;

    @Transactional
    public Member insertOrThrow(MemberInsertReqDto reqDto, boolean isThrow) {
        var insertedMember = memberDao.insert(reqDto.toEntity());

        if (isThrow) {
            throw new RuntimeException();
        }

        return insertedMember;
    }

    public Member nonTransactionalInsertWithThrow(MemberInsertReqDto reqDto) throws RuntimeException {
        memberDao.insert(reqDto.toEntity());

        throw new RuntimeException();
    }

    public Member selectByEmail(String email) {
        return memberDao.selectByEmail(email);
    }

    @Transactional
    public void delete(Long id) {
        memberDao.delete(id);
    }

    @Transactional
    public Member insertException(MemberInsertReqDto reqDto) {
        try {
            insertOrThrow(reqDto, true);
        } catch (RuntimeException e) {
            System.out.printf("failed! %s", e.getMessage());
        }

        return memberDao.insert(reqDto.toEntity());
    }

}
