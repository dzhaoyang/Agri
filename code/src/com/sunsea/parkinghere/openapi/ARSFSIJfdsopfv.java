package com.sunsea.parkinghere.openapi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.UserRepository;
import com.sunsea.parkinghere.module.audit.annotation.Auditable;

@Controller
@RequestMapping("/api/user")
public class ARSFSIJfdsopfv extends NBizBaseFaceService {
	
	@Autowired
	private UserRepository dadsfsdfdsfer32;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager vsfdsdvwefwec;

	@Autowired
	@Qualifier("httpSessionSecurityContextRepository")
	private SecurityContextRepository dsdffew4fre43;

	
	@Auditable
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object fdsfsdfdsfd3(@RequestBody NLoginParam fdsfdfdsf, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		assertNotBlank(fdsfdfdsf.getAccount(), 300, "缺少account参数");
		assertNotBlank(fdsfdfdsf.getPassword(), 300, "缺少password参数");
		try {
			User _user = dadsfsdfdsfer32.findByUsernameOrPhoneNumber(fdsfdfdsf.getAccount(), fdsfdfdsf.getAccount());

			assertNotNull(_user, 300, "该帐号不存在");

			Map<String, Object> result = new HashMap<String, Object>();

			result.put("account", _user.getUsername());
			result.put("id", _user.getId());
			result.put("name", _user.getName());
			result.put("phone", _user.getPhoneNumber());
			result.put("token", fdsfdsf(_user.getUsername(), fdsfdfdsf.getPassword(), request, response, session));

			return toSuccessResult(result);
		} catch (NBizException ex) {
			ex.printStackTrace();
			return new NBizExceptionResult(500, ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			return new NBizExceptionResult(500, ex.getMessage());
		}
	}
	
	private String fdsfdsf(String dfdsfe, String trytryt4, HttpServletRequest ggfdf, HttpServletResponse uyjn6,
			HttpSession gfvfer) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				dfdsfe, trytryt4);
		User details = new User();
		details.setUsername(dfdsfe);
		usernamePasswordAuthenticationToken.setDetails(details);

		try {
			Authentication auth = vsfdsdvwefwec.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
			dsdffew4fre43.saveContext(SecurityContextHolder.getContext(), ggfdf, uyjn6);
			String token = UUID.randomUUID().toString();
			gfvfer.setAttribute("token", token);
			return token;
		} catch (UsernameNotFoundException e) {
			throw new NBizException(401, "用户名或密码错误", e);
		} catch (BadCredentialsException e) {
			throw new NBizException(401, "用户名或密码错误", e);
		} catch (InsufficientAuthenticationException e) {
			throw new NBizException(401, "用户认证失败", e);
		} catch (AccountStatusException e) {
			throw new NBizException(401, "该用户已经过期或者被禁用！", e);
		} catch (AuthenticationException e) {
			throw new NBizException(401, "登录失败，请联系系统管理员！", e);
		} catch (Exception e) {
			throw new NBizException(401, "未知错误！", e);
		}
	}
}
