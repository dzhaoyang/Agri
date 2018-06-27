package com.sunsea.parkinghere.biz.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.ValidationException;
import com.sunsea.parkinghere.framework.annotation.Protected;
import com.sunsea.parkinghere.framework.web.WebUtils;

@Controller
@RequestMapping("/api/v1/dashboard/user/profile")
public class UPMRS {
    
    @Autowired
    private US csdfsdfsd;
    
    @Protected("user:changePassword")
    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    @ResponseBody
    public Object fdsfdsfdfdfd(String oldPassword,
					            String newPassword,
					            String newPassword1,
					            ModelMap map,
					            HttpServletRequest httpServletRequest,
					            HttpSession session) {
        try {
            
            User user = (User) SecurityContextHolder.getContext()
                                                    .getAuthentication()
                                                    .getPrincipal();
            
            User backendUser = csdfsdfsd.findById(user.getId());
            if (!csdfsdfsd.vpp(backendUser, oldPassword)) {
                throw new RuntimeException("旧密码输入错误！");
            }
            
            if (!newPassword.equals(newPassword1)) {
                return WebUtils.failedMap("2次输入的新密码不一致！");
            }
            
            csdfsdfsd.csdp(backendUser, newPassword);
            return WebUtils.succeedMap();
        }
        catch (Exception e) {
            return WebUtils.failedMap(e.getMessage());
        }
    }
    
    @Protected("user:changeProfile")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object fdsfsdfdsfds(@PathVariable("id") String fdsf, User fdge) throws Exception {
        
    	User target = csdfsdfsd.findById(fdsf);
        if (target == null) {
            throw new ValidationException(String.format("用户 [id=%s] 未找到!", fdsf));
        }
        
        target.setUsername(fdge.getUsername());
        target.setName(fdge.getName());
        target.setDisplayName(fdge.getDisplayName());
        target.setTitle(fdge.getTitle());
        target.setEmail(fdge.getEmail());
        target.setPhoneNumber(fdge.getPhoneNumber());
        
        csdfsdfsd.persist(target);
        return WebUtils.succeedMap(target);
    }
}
