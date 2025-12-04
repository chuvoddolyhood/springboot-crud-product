package com.example.springboot_crud_product.Core.Logging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// wrap request & response
		CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
		CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse(response);

		long start = System.currentTimeMillis();

		log.info(">>> REQUEST {} {} | body={}", request.getMethod(), request.getRequestURI(), wrappedRequest.getBody());

		filterChain.doFilter(wrappedRequest, wrappedResponse);

		long duration = System.currentTimeMillis() - start;

		byte[] responseBody = wrappedResponse.getCachedBody();
		String responseBodyStr = new String(responseBody);

		log.info("<<< RESPONSE {} {} | status={} | time={}ms | body={}", request.getMethod(), request.getRequestURI(),
				response.getStatus(), duration, responseBodyStr);

		// write body back to original response
		ServletOutputStream out = response.getOutputStream();
		out.write(responseBody);
		out.flush();
	}
}