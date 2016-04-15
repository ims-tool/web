package br.com.ims.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class UploadFileService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadFile(@FormDataParam("fileName") final String fileName, @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		if(StringUtils.isNotBlank(fileDetail.getFileName())){
			String uploadedFileLocation = "C://Cesar/"+ fileName+".wav";
			writeToFile(uploadedInputStream, uploadedFileLocation);
			// save it
			try {
				uploadedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Nao tem arquivo para salvar");
		}
		


	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			uploadedInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}