//package com.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthTokenFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//
//		HttpServletRequest request = ((HttpServletRequest) (req));
//
//		String url = request.getRequestURL().toString();
//
//		System.out.println("incoming url ---> " + url);
//		System.out.println("method ---> " + request.getMethod());
//
//		if (url.contains("/public/")|| request.getMethod().toLowerCase().equals("options")) {
//			System.out.println("options by pass....");
//			chain.doFilter(req, res);// goahed
//		} else {
//			// token - authentication
//			String authToken = request.getHeader("authToken");
//			System.out.println(authToken);
//			if (authToken == null || authToken.trim().length() != 16) {
//				
//				System.out.println("token verification failed.......");
//				HttpServletResponse response = ((HttpServletResponse) res);
//				response.setContentType("application/json");
//				response.setStatus(401);
//				response.getWriter().write("{'msg':'Please Login before access service'}");
//			} else {
//				//token -> db user ? 
//				System.out.println("user verfied....");
//				chain.doFilter(req, res);// go ahead
//			}
//
//		}
//
//	}
//}