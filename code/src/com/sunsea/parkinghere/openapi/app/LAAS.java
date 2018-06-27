package com.sunsea.parkinghere.openapi.app;

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
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.openapi.NBizBaseFaceService;
import com.sunsea.parkinghere.openapi.NBizException;

@Controller
@RequestMapping(value = "/api/app/user")
public class LAAS extends NBizBaseFaceService {
	
	@Autowired
	private US fdsafdewfsdfsd;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("httpSessionSecurityContextRepository")
	private SecurityContextRepository securityRepository;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object fdsfdfds4gf(@RequestBody LPfdsuofo fdgfgfdg, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		assertNotBlank(fdgfgfdg.getAccount(), 300, "缺少account参数");
		assertNotBlank(fdgfgfdg.getPassword(), 300, "缺少password参数");
		try {
			User user = fdsafdewfsdfsd.fbuds(fdgfgfdg.getAccount());
			
			assertNotNull(user, 300, "该帐号不存在");

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("id", user.getId());
			result.put("name", user.getName());
			result.put("phone", user.getPhoneNumber());
			result.put("token", fdsafdfd98d77d(user.getUsername(), fdgfgfdg.getPassword(), request, response, session));

			return toSuccessResult(result);
		} catch (NBizException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NBizException(500, ex);
		}
	}
	
	private String fdsafdfd98d77d(String dfsdfd, String gfhhgfh, HttpServletRequest tretert4, HttpServletResponse cvcx5,
			HttpSession fgtrhtr) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				dfsdfd, gfhhgfh);
		User details = new User();
		details.setUsername(dfsdfd);
		usernamePasswordAuthenticationToken.setDetails(details);

		try {
			Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			
			SecurityContextHolder.getContext().setAuthentication(auth);
			securityRepository.saveContext(SecurityContextHolder.getContext(), tretert4, cvcx5);
			String token = UUID.randomUUID().toString();
			fgtrhtr.setAttribute("token", token);
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
