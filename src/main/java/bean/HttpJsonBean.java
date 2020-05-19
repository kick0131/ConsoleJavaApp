package bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * HTTPリクエスト形式のJSONマッピングクラス
 */
public class HttpJsonBean implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Map<String,String> query = new LinkedHashMap<String,String>();
	private Map<String,String> header = new LinkedHashMap<String,String>();
	private Map<String,Object> body = new LinkedHashMap<String,Object>();

	/**
	 * 更新情報があれば更新し、無ければ追加する
	 * @param replaceData
	 */
	public void replaceQuery(Map<String,String> replaceData){
		if(replaceData == null){
			return;
		}
		Set<String> replaceKeys = replaceData.keySet();
		if(replaceKeys == null){
			return;
		}
		for(String key : replaceKeys){
			if(query.containsKey(key) == true){
				query.replace(key, replaceData.get(key));
			}else{
				query.put(key,replaceData.get(key));
			}
		}
	}
	public void replaceHeader(Map<String,String> replaceData){
		if(replaceData == null){
			return;
		}
		Set<String> replaceKeys = replaceData.keySet();
		if(replaceKeys == null){
			return;
		}
		for(String key : replaceKeys){
			if(header.containsKey(key) == true){
				header.replace(key, replaceData.get(key));
			}else{
				header.put(key,replaceData.get(key));
			}
		}
	}
	public void replaceBody(Map<String,Object> replaceData){
		if(replaceData == null){
			return;
		}
		Set<String> replaceKeys = replaceData.keySet();
		if(replaceKeys == null){
			return;
		}
		for(String key : replaceKeys){
			if(body.containsKey(key) == true){
				body.replace(key, replaceData.get(key));
			}else{
				body.put(key,replaceData.get(key));
			}
		}
	}
	public Map<String, String> getQuery() {
		return query;
	}
	public void setQuery(Map<String, String> query) {
		this.query = query;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	public Map<String, Object> getBody() {
		return body;
	}
	public void setBody(Map<String, Object> body) {
		this.body = body;
	}
}
