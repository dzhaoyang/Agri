package com.sunsea.parkinghere.biz.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sunsea.parkinghere.biz.model.PasswordChangeRequest;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.US;

@Controller
@RequestMapping("/guest/account")
public class FARPC {
    
    @Autowired
    private US fdssddf;
    
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
    public String fddsfdsfdds(ModelMap map, HttpServletRequest request) throws Exception {
        return "guest/account/forgetPassword";
    }
    
    /**
     * Working under current tenancy's namespace
     * 
     * @param username
     * @param map
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/passwordResetRequest", method = RequestMethod.POST)
    public String gfggsdgsg3(String username,
                                     ModelMap map,
                                     HttpServletRequest request) throws Exception {
    	User account = fdssddf.ga(username);
        if (account == null) {
            map.put("message", "用户名无效!");
            return "guest/account/forgetPassword";
        }
        
        if (StringUtils.isBlank(account.getEmail())) {
            map.put("message", "未找到邮箱!");
            return "guest/account/forgetPassword";
        }
        
        PasswordChangeRequest passwordChangeRequest = fdssddf.cpcrdsfds(username);
        // by default, the username is equaled with email address, but there
        // a special user which is administrator
        // so we have to retrieve the email of administrator while
        // administrator try to reset its password.
        map.put("username", username);
        map.put("email", passwordChangeRequest.getUser().getEmail());
        // To reset the password to your GaeSecure account, please follow
        // the instructions we've sent to your <b>${email}</b> email
        // address.
        return "guest/account/passwordResetNotify";
    }
    
    /**
     * http://<xxx>.gaesecure.com/guest/account/changePassword.do?uuid={uuid}
     * 
     * @param uuid
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping(value = "/changePassword/{uuid}", method = RequestMethod.GET)
    public String fdfsdfsdfds(@PathVariable String uuid,
                                     ModelMap modelMap,
                                     HttpServletRequest request) {
        modelMap.put("uuid", uuid);
        if (fdssddf.vpcr(uuid)) {
            // enter user-name, new password & re-enter new password
            return "guest/account/changePassword";
        }
        else {
            // show message:The URL you used for changing your password is not
            // valid or expired. Please submit another request for resetting
            // your password.
            return "guest/account/changePasswordReject";
        }
    }
    
    @RequestMapping(value = "/saveChangePassword", method = RequestMethod.POST)
    public String cpdsfds(String uuid,
                                 String username,
                                 String newPassword1,
                                 String newPassword2,
                                 ModelMap modelMap,
                                 HttpServletRequest request) {
        modelMap.put("uuid", uuid);
        
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(username)
            || StringUtils.isEmpty(newPassword1)
            || StringUtils.isEmpty(newPassword2)) {
            modelMap.put("message",
                         "用户名或密码为空!");
            return "guest/account/changePassword";
        }
        
        if (!fdssddf.vpcr(uuid)) {
            return "guest/account/changePasswordReject";
        }
        
        if (!newPassword1.equals(newPassword2)) {
            modelMap.put("message",
                         "两次输入的新密码不匹配!");
            return "guest/account/changePassword";
        }
        
        if (!fdssddf.vpcrd(uuid, username)) {
            modelMap.put("message",
                         "重置密码请求无效!");
            return "guest/account/changePassword";
        }
        
        fdssddf.apcr(uuid, newPassword1);
        return "redirect:/guest/account/changePasswordSuccess";
    }
    
    @RequestMapping(value = "/changePasswordSuccess", method = RequestMethod.GET)
    public String fdsfdfdfds(HttpServletRequest request) {
        // show message:
        // Your password has been reset. You may now <a
        // href="<spring:url value="/"/>">login</a> with the new credentials
        return "guest/account/changePasswordSuccess";
    }
    
}
