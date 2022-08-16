package com.study.kdy.chapter08.service;

import com.study.kdy.chapter08.dto.MemberInsertReqDto;
import com.study.kdy.chapter08.model.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionWrappingService {

    private final TransactionMemberDaoService transactionMemberDaoService;

    public Member insertAndThrow(MemberInsertReqDto reqDto) throws RuntimeException {
        transactionMemberDaoService.insertOrThrow(reqDto, false);
        throw new RuntimeException();
    }

}
