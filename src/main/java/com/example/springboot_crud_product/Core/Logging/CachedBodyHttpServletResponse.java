package com.example.springboot_crud_product.Core.Logging;

import java.io.ByteArrayOutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

	private ByteArrayOutputStream cachedBytes = new ByteArrayOutputStream();

	public CachedBodyHttpServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return new ServletOutputStream() {

			@Override
			public void write(int b) {
				cachedBytes.write(b);
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setWriteListener(WriteListener listener) {
			}
		};
	}

	public byte[] getCachedBody() {
		return cachedBytes.toByteArray();
	}
}