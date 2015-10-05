package br.com.ims.tool.nextform.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import javax.naming.InitialContext;
import javax.net.ssl.HttpsURLConnection;
import org.apache.log4j.Logger;
import br.com.ims.tool.nextform.model.MethodInvocationVO;

public class MethodsCatalogExternalService {

	private static Logger logger = Logger.getLogger(MethodsCatalogExternalService.class);
	private static InitialContext ctx = null;
	private final String USER_AGENT = "Mozilla/5.0";


	public MethodInvocationVO getExternalMethod(String jsonContext,Map<String, String> parameters) throws Exception {

		MethodInvocationVO methodInvocationVO = null;

		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

		return methodInvocationVO;
	}

}
