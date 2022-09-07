package com.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmailDetailsBean;
import com.bean.ForgotPasswordBean;
import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.OtpService;

import lombok.extern.log4j.Log4j2;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Log4j2
public class UserSessionController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;

//	@Autowired
//	TokenService tokenService;
//	
	@Autowired
	EmailController emailController;
	
	@Autowired
	OtpService otpService;

	@PostMapping("/signup")
	public ResponseEntity<ResponseBean<UserBean>> signUp(@RequestBody UserBean userBean){
		UserBean dbUser = userRepository.findByEmail(userBean.getEmail());
		ResponseBean<UserBean> res = new ResponseBean<>();
		
			if(dbUser == null) {
			
				
				RoleBean role = roleRepository.findByRoleName("service user");
				System.out.println(role);
				userBean.setRole(role);
				String encPassword = bcrypt.encode(userBean.getPassword());
				userBean.setPassword(encPassword);
				userRepository.save(userBean);
				res.setData(userBean);
				res.setMsg("Signup done...");
				return ResponseEntity.ok(res);
			}else {
				
				res.setData(userBean);
				res.setMsg("Email Already Taken");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginBean loginBean){
		UserBean dbUser = userRepository.findByEmail(loginBean.getEmail());
		
		if(dbUser == null ||bcrypt.matches(loginBean.getPassword(), dbUser.getPassword()) == false) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(loginBean);
			res.setMsg("Invalid Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}else {
//			String authToken = tokenService.generateToken(16);
//			dbUser.setAuthToken(authToken);
			userRepository.save(dbUser);
			log.info("AuthToken generated");
			
			ResponseBean<Map<String, Object>> res = new ResponseBean<>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("user", dbUser);
			res.setData(data);
			res.setMsg("Login done...");
			return ResponseEntity.ok(res);
		}
	}
	
	@PostMapping("/otpsend")
	public ResponseEntity<?> sendotp(@RequestBody LoginBean login){
		System.out.println("HEllo");
		EmailDetailsBean emailBean = new EmailDetailsBean();
		String email =  login.getEmail();
		System.out.println(email);
		UserBean userBean = userRepository.findByEmail(email);
		Integer otp = otpService.genrateOtp();
		emailBean.setRecipient(email);
		
		emailBean.setSubject("forget password otp");
		emailBean.setMsgBody("forgot password OTP is-"+otp);
		emailController.sendMail(emailBean);
		userBean.setOtp(otp);
		userRepository.save(userBean);
		return ResponseEntity.ok(emailBean);
	}
	

	@PostMapping("/otp")
	public ResponseEntity<?> forgot(@RequestBody ForgotPasswordBean forgotPasswordBean){
		ResponseBean<Object> res = new ResponseBean<>();
		String email = forgotPasswordBean.getEmail();
		UserBean userBean = userRepository.findByEmail(email);
		Integer otp = userBean.getOtp();
		if(otp == null ) {
			res.setData(email);
			res.setMsg("otp not found");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}else if(otp.equals(forgotPasswordBean.getOtp())) {
			res.setData(email);
			res.setMsg("successfully...");
			userBean.setOtp(null);
			userRepository.save(userBean);
			return ResponseEntity.ok(res);
		}else {
			res.setData(email);
			res.setMsg("incorrect otp");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<?> updatepassword(@RequestBody LoginBean login){
		ResponseBean<Object> res = new ResponseBean<>();
		UserBean userBean = userRepository.findByEmail(login.getEmail());
		userBean.setPassword(bcrypt.encode(login.getPassword()));
		userRepository.save(userBean);
		res.setData(userBean);
		res.setMsg("password successfully forgot...");
		return ResponseEntity.ok(res);	
		
	}
	
}
