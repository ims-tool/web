package br.com.ims.flow.common;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DbConnection con = null;
		try {
			con = new DbConnection("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Util.getUID());
	}

}
