package com.controller.serviceprovider;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.AppointmentBean;
import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.ServiceProviderBean;
import com.repository.AppointmentRepository;
import com.repository.RoleRepository;
import com.repository.ServiceProviderRepository;
import com.service.FileUploadService;

import lombok.extern.log4j.Log4j2;

@CrossOrigin
@RestController
@RequestMapping("/serviceprovider")
@Log4j2
public class ProviderSessionController {
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
//
//	@Autowired
//	TokenService tokenService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	AppointmentRepository appointmentRepository;

	@PostMapping("/signup")
	public ResponseEntity<ResponseBean<ServiceProviderBean>> signUp(@RequestBody ServiceProviderBean serviceProviderBean){
		ServiceProviderBean dbUser = serviceProviderRepository.findByEmail(serviceProviderBean.getEmail());
		ResponseBean<ServiceProviderBean> res = new ResponseBean<>();
		log.info("signup...");
			if(dbUser == null) {
//				fileUploadService(serviceProviderBean.getPhoto());
				RoleBean role = roleRepository.findByRoleName("service provider");
				serviceProviderBean.setRole(role);
//				serviceProviderBean.setPhoto("resources\\images\\"+file.getOriginalFilename());
				String encPassword = bcrypt.encode(serviceProviderBean.getPassword());
				serviceProviderBean.setPassword(encPassword);
				serviceProviderRepository.save(serviceProviderBean);
				res.setData(serviceProviderBean);
				res.setMsg("Signup done...");
				
				return ResponseEntity.ok(res);
			}else {
				res.setData(serviceProviderBean);
				res.setMsg("Email Already Taken");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginBean loginBean){
		ServiceProviderBean dbUser = serviceProviderRepository.findByEmail(loginBean.getEmail());
		
		if(dbUser == null || bcrypt.matches(loginBean.getPassword(), dbUser.getPassword()) == false) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(loginBean);
			res.setMsg("Invalid Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		} else {
//			String authToken = tokenService.generateToken(16);
//			dbUser.setAuthToken(authToken);
			serviceProviderRepository.save(dbUser);
			log.info("AuthToken generated");
			
			ResponseBean<Map<String,Object>> res = new ResponseBean<>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("serviceprovider", dbUser);
			res.setData(data);
			res.setMsg("Login Done...");
			return ResponseEntity.ok(res);
				
		}
	}
	@GetMapping("/getallSp")
	public ResponseEntity<?> getallSp(ServiceProviderBean provider) {
		return ResponseEntity.ok(serviceProviderRepository.findAll());
	}
	@DeleteMapping("/delete/{providerId}")
	public ResponseEntity<ResponseBean<ServiceProviderBean>> removeProvider(@PathVariable("providerId") Integer providerId) {
		ServiceProviderBean provider = serviceProviderRepository.findByProviderId(providerId);
		ResponseBean<ServiceProviderBean> res = new ResponseBean<>();

		if (provider == null) {
			res.setMsg("invalid");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			serviceProviderRepository.deleteById(providerId);
			res.setData(provider);
			System.out.println("provider delete "+provider.getProviderId());
			res.setMsg("provider removed");
			return ResponseEntity.ok(res);
		}
	}
//	@PostMapping("/upload/image")
//    public ResponseEntity<ImageUploadResponse> uplaodImage(@RequestParam("image") MultipartFile file)
//            throws IOException {
//
//        imageRepository.save(Image.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .image(ImageUtility.compressImage(file.getBytes())).build());
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ImageUploadResponse("Image uploaded successfully: " +
//                        file.getOriginalFilename()));
//    }
	
	@PostMapping("/bookappointment")
	public ResponseEntity<ResponseBean<AppointmentBean>> bookAppointmnet(@RequestBody AppointmentBean appointmentBean){
		ResponseBean<AppointmentBean> res = new ResponseBean<>();
		appointmentRepository.save(appointmentBean);
		res.setData(appointmentBean);
		res.setMsg("appointmnet done...");
		return ResponseEntity.ok(res);
		
	}
}
