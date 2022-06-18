package chapter03.service;

import chapter03.dto.RegisterRequestDto;
import chapter03.exception.DuplicateMemberException;
import chapter03.model.Member;
import chapter03.model.MemberDao;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MemberRegisterService {
	private final MemberDao memberDao;

	public Long regist(RegisterRequestDto reqDto) {
		Member member = memberDao.selectByEmail(reqDto.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + reqDto.getEmail());
		}
		Member newMember = new Member(
				reqDto.getEmail(), reqDto.getPassword(), reqDto.getName(),
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
