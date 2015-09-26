package br.com.ims.tool.nextform.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class MethodInvocationUtils {

	private static Logger logger = Logger.getLogger(MethodInvocationUtils.class);

	private static DateFormat dtFormatAnoMesDia;
	private static DateFormat dtFormatAnoMesDiaHrMin;
	private static DateFormat dtFormatAnoMesDiaHora;
	private static DateFormat dtFormatDiaMesAno;
	private static DateFormat dtFormatDiaMesAnoHora;
	private static DateFormat dtFormatNomeMes;

	private static DecimalFormat decFormatDinheiro;

	public static DateFormat getDtFormatAnoMesDia() {
		if (dtFormatAnoMesDia == null) {
			dtFormatAnoMesDia = new SimpleDateFormat("yyyy-MM-dd");
		}
		return dtFormatAnoMesDia;
	}

	public static DateFormat getDtFormatNomeMes() {
		if (dtFormatNomeMes == null) {
			dtFormatNomeMes = new SimpleDateFormat("MMMMM", new Locale("pt", "BR"));
		}
		return dtFormatNomeMes;
	}

	public static DateFormat getDtFormatAnoMesDiaHrMin() {
		if (dtFormatAnoMesDiaHrMin == null) {
			dtFormatAnoMesDiaHrMin = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		}
		return dtFormatAnoMesDiaHrMin;
	}

	public static DateFormat getDtFormatAnoMesDiaHora() {
		if (dtFormatAnoMesDiaHora == null) {
			dtFormatAnoMesDiaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return dtFormatAnoMesDiaHora;
	}

	public static DateFormat getDtFormatDiaMesAno() {
		if (dtFormatDiaMesAno == null) {
			dtFormatDiaMesAno = new SimpleDateFormat("dd/MM/yyyy");
		}
		return dtFormatDiaMesAno;
	}

	public static DateFormat getDtFormatDiaMesAnoHora() {
		if (dtFormatDiaMesAnoHora == null) {
			dtFormatDiaMesAnoHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		}
		return dtFormatDiaMesAnoHora;
	}

	public static DecimalFormat getDecFormatDinheiro() {
		if (decFormatDinheiro == null) {
			decFormatDinheiro = new DecimalFormat("0.00");
		}
		return decFormatDinheiro;
	}

	public static JSONObject getJsonObject(String json) {

		if (json == null || json.isEmpty()) {
			return new JSONObject();
		}

		JSONObject object = null;
		try {
			object = new JSONObject(json);
		} catch (JSONException e) {
			logger.error("Erro ao recuperar recuperar objeto Json [json] " + json, e);
		}

		return object;
	}

	public static String getFieldValue(String fieldName) {
		String value = null;
		try {
			Field field = MapValues.class.getDeclaredField(fieldName);
			value = (String) field.get(field.getName());
		} catch (Exception e) {
			logger.error("Não foi possível recuperar o valor do campo [fieldName] " + fieldName, e);
		}
		return value;
	}

	public static String getContextValue(String jsonContext, String key) {

		JSONObject jsonObject = getJsonObject(jsonContext);
		StringTokenizer tokenizer = new StringTokenizer(key, MapValues.DELIMITER);
		while (tokenizer.hasMoreTokens()) {
			try {
				String s = tokenizer.nextToken();
				JSONObject jObj = jsonObject.optJSONObject(s);
				if (jObj == null) {
					if (jsonObject.has(s)) {
						return jsonObject.optString(s);
					} else {
						return null;
					}
				} else {
					if (tokenizer.hasMoreTokens()) {
						String s2 = tokenizer.nextToken();
						if (jObj.has(s2)) {
							return jObj.getString(s2);
						}
					}
				}
			} catch (JSONException e) {
				logger.error("Erro ao recuperar valor do contexto usando [key] " + key, e);
			}
		}

		return null;
	}

	/**
	 * @param jsonContext
	 * @param key
	 * @param value
	 * @param updateValue
	 * @return Contexto atualizado
	 */
	public static String setContextValue(String jsonContext, String key, String value, boolean updateValue) {

		JSONObject jsonObject = getJsonObject(jsonContext);
		StringTokenizer tokenizer = new StringTokenizer(key, MapValues.DELIMITER);
		while (tokenizer.hasMoreTokens()) {
			try {
				// recupera o nome do node raíz
				String node = tokenizer.nextToken();

				// recupera o nome do atributo abaixo do node raíz, caso exista
				String attr = null;
				if (tokenizer.hasMoreTokens()) {
					attr = tokenizer.nextToken();
				}

				if (attr == null) {
					if (jsonObject.has(node)) {
						if (updateValue) {
							jsonObject.put(node, value);
						}
					} else {
						jsonObject.put(node, value);
					}
				} else {
					// recupera a referência do objeto com o nome do node raíz. Se não existe, então cria
					JSONObject jObjPai = jsonObject.optJSONObject(node);
					if (jObjPai == null) {
						jObjPai = new JSONObject();
						jsonObject.put(node, jObjPai);
					}

					// verifica se já tem o atributo da raíz e atualiza o valor do mesmo ou não
					if (jObjPai.has(attr)) {
						if (updateValue) {
							jObjPai.put(attr, value);
						}
					} else {
						jObjPai.put(attr, value);
					}
				}

				return jsonObject.toString();
			} catch (JSONException e) {
				logger.error("Erro ao setar [key] " + key + " [value] " + value + " no contexto", e);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Erro ao setar [key] " + key + " [value] " + value + " no contexto", e);
			}
		}

		return jsonContext;
	}

	@SuppressWarnings("unchecked")
	public static String setContextValues(String jsonContext, String pattern, String newValue) {

		try {
			StringTokenizer tokenizer = new StringTokenizer(pattern, MapValues.DELIMITER);
			String obj = tokenizer.nextToken();
			String attr_pattern = tokenizer.nextToken();

			JSONObject jsonObject = MethodInvocationUtils.getJsonObject(jsonContext);
			JSONObject newobj = jsonObject.getJSONObject(obj);
			Iterator<String> it = newobj.keys();
			while (it.hasNext()) {
				String attr = it.next();
				if (attr.startsWith(attr_pattern)) {
					jsonContext = MethodInvocationUtils.setContextValue(jsonContext, obj + "." + attr, newValue, true);
				}
			}
		} catch (Exception e) {
			logger.error("Erro ao atualizar valores [pattern] " + pattern + " [value] " + newValue + " no contexto", e);
		}

		return jsonContext;
	}
	
	public static String setContextParamValue(String jsonContext, Map<String, String> parameters) {
		String key = null;
		String value = null;
		try{
			List<String> values = new ArrayList<String>();
			String contextValue = null;
			for (Map.Entry entry : parameters.entrySet()) {
				values.add(entry.getValue().toString());
			}
			
			if(values.size() >= 2){
				value = values.get(0);
				key = values.get(1);
			}

			contextValue = MethodInvocationUtils.getContextValue(jsonContext, value);
			
			if(contextValue != null){
				jsonContext = MethodInvocationUtils.setContextValue(jsonContext, key, contextValue, true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonContext;
	}

}
