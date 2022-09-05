package com.study.main.chapter16.config;

import com.study.main.chapter16.spring.AuthService;
import com.study.main.chapter16.spring.ChangePasswordService;
import com.study.main.chapter16.spring.MemberDao;
import com.study.main.chapter16.spring.MemberRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.main.chapter16.controller.ApiExceptionAdvice;
import com.study.main.chapter16.controller.ChangePwdController;
import com.study.main.chapter16.controller.LoginController;
import com.study.main.chapter16.controller.LogoutController;
import com.study.main.chapter16.controller.MemberDetailController;
import com.study.main.chapter16.controller.MemberListController;
import com.study.main.chapter16.controller.RegisterController;
import com.study.main.chapter16.controller.RestMemberController;

@Configuration
public class ControllerConfig {

	@Autowired
	private MemberRegisterService memberRegSvc;
	@Autowired
	private AuthService authService;
	@Autowired
	private ChangePasswordService changePasswordService;
	@Autowired
	private MemberDao memberDao;

	@Bean
	public RegisterController registerController() {
		RegisterController controller = new RegisterController();
		controller.setMemberRegisterService(memberRegSvc);
		return controller;
	}

	@Bean
	public LoginController loginController() {
		LoginController controller = new LoginController();
		controller.setAuthService(authService);
		return controller;
	}
	
	@Bean
	public LogoutController logoutController() {
		return new LogoutController();
	}
	
	@Bean
	public ChangePwdController changePwdController() {
		ChangePwdController controller = new ChangePwdController();
		controller.setChangePasswordService(changePasswordService);
		return controller;
	}
	
	@Bean
	public MemberListController memberListController() {
		MemberListController controller = new MemberListController();
		controller.setMemberDao(memberDao);
		return controller;
	}
	
	@Bean
	public MemberDetailController memberDetailController() {
		MemberDetailController controller = new MemberDetailController();
		controller.setMemberDao(memberDao);
		return controller;
	}
	
	@Bean
	public RestMemberController restApi() {
		RestMemberController cont = new RestMemberController();
		cont.setMemberDao(memberDao);
		cont.setRegisterService(memberRegSvc);
		return cont;
	}
	
	@Bean
	public ApiExceptionAdvice apiExceptionAdvice() {
		return new ApiExceptionAdvice();
	}
}
