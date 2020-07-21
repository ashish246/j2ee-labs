package com.multipart.demo;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * MultipartConfig annotation has following attributes:
 * 
 * fileSizeThreshold: We can specify the size threshold after which the file
 * will be written to disk. The size value is in bytes, so 1024*1024*10 is 10
 * MB.
 * 
 * location: Directory where files will be stored by default, it’s default value
 * is “”.
 * 
 * maxFileSize: Maximum size allowed to upload a file, it’s value is provided in
 * bytes. It’s default value is -1L means unlimited.
 * 
 * maxRequestSize: Maximum size allowed for multipart/form-data request. Default
 * value is -1L that means unlimited.
 * 
 * Part interface represents a part or form item that was received within a
 * multipart/form-data POST request. Some important methods are
 * getInputStream(), write(String fileName) that we can use to read and write
 * file.
 * 
 * @author Administrator
 *
 */
@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
// 100 MB
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 205242440643911308L;

	/**
	 * Directory where uploaded files will be saved, its relative to the web
	 * application directory.
	 */
	private static final String UPLOAD_DIR = "uploads";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		System.out.println("Upload File Directory="
				+ fileSaveDir.getAbsolutePath());

		String fileName = null;
		// Get all the parts from request and write it to the file on server
		for (Part part : request.getParts()) {
			fileName = getFileName(part);
			part.write(uploadFilePath + File.separator + fileName);
		}

		request.setAttribute("message", fileName
				+ " File uploaded successfully!");

		getServletContext().getRequestDispatcher("/response.jsp").forward(
				request, response);
	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {

		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2,
						token.length() - 1);
			}
		}
		return "";
	}
}