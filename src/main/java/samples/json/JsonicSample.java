package samples.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.IActor;
import bean.HttpJsonBean;
import net.arnx.jsonic.JSON;

/**
 * HTTP リクエストの情報をJSONでマッピングできるかの検証 ※例に示す二重の入れ子まで対応 入れ子のオブジェクトはjava.util.LinkedHashMap型
 *
 * @author 113414A00850F
 *
 */
@SuppressWarnings("unused")
public class JsonicSample implements IActor {
	static Logger log = LoggerFactory.getLogger(JsonicSample.class);

	static String jsonfileDir = System.getProperty("user.dir") + "/src/main/resource/jsonfile";

	/**
	 * { httpquery:{ "username":"test", "email":"test@example.com" }, httpheader:{ "X-Application-Id":"12345", "X-Application-Key":"67890" }, httpbody:{ "query":{"channels":["chan1","chan2"]}, "message":"test message" } }
	 */
	public static final String JSON_SAMPLE01 = "{" + "httpquery:{" + "\"username\":\"test\"," + "\"email\":\"test@example.com\"" + "}," + "httpheader:{" + "\"X-Application-Id\":\"12345\"," + "\"X-Application-Key\":\"67890\"" + "}," + "httpbody:{" + "\"query\":{\"channels\":[\"chan1\",\"chan2\"]}," + "\"message\":\"test message\"" + "}" + "}";

	// ヘッダ追加とボディに入れ子の項目を追加
	public static final String JSON_SAMPLE02 = "{" + "\"httpquery\":{" + "\"username\":\"test\"," + "\"email\":\"test@example.com\"" + "}," + "\"httpheader\":{" + "\"X-Application-Id\":\"12345\"," + "\"X-Application-Key\":\"67890\"," + "\"Content-Type\": \"application/json\"" + "}," + "\"httpbody\":{" + "\"query\":{\"channels\":[\"chan1\",\"chan2\"]}," + "\"message\":\"test message\"," + "\"allowedReceivers\": [ \"g:admin\",\"g:localuser\" ]" + "}" + "}";

	public static final String JSON_SAMPLE03 = "{\"currentTime\":\"2016-03-07T11:12:13.000Z\",\"results\":[{\"_id\":\"56d67c508a286f3eb0554a43\",\"message\":\"Googleへ誘導します\",\"url\":\"http://www.google.co.jp/\",\"title\":\"お知らせ(テスト)\",\"badge\":0,\"sound\":\"\",\"content-available\":0,\"category\":\"\",\"ACL\":{\"r\":[\"g:authenticated\"],\"w\":[\"g:authenticated\"],\"c\":[],\"u\":[],\"d\":[],\"admin\":[]},\"createdAt\":\"2016-03-02T05:38:24.791Z\",\"updatedAt\":\"2016-03-02T05:38:24.791Z\",\"etag\":\"0a26ce9c-6f42-492d-afd2-07829dea832b\"}]}";
	public static final String JSON_SAMPLE04 = "{\"body\":" + JSON_SAMPLE03 + "}";

	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void action() throws Exception {
		testCall01();

	}

	@Override
	public void init() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void terminate() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void testCall01() {
		HttpJsonBean impl = new HttpJsonBean();
		impl = JSON.decode(JSON_SAMPLE04, HttpJsonBean.class);
		log.info(JSON_SAMPLE04);

		log.info("Keys :" + impl.getBody().keySet());
		log.info("class :" + impl.getBody().get("results").getClass().getName());
	}

	private void testCall02() {
		HttpJsonBean impl = new HttpJsonBean();
		Map<String, String> head = new LinkedHashMap<String, String>();
		Map<String, Object> body = new LinkedHashMap<String, Object>();

		head.put("X-Application-Id", "56c41a588a286f37037fa967");
		head.put("X-Application-Key", "N3wPV3tEoqkm4UITT2p61v8HPom59te6RfuahPU6");
		head.put("Content-Type", "application/json");

		String user01 = "56c437298a286f37037fa980";
		String[] users =
			{ user01, "56c5a05c8a286f53758753c6" };
		body.put("username", users);
		String[] messages =
			{ "メッセージ１", "メッセージ２" };
		body.put("message", messages);
		ACL acl = new ACL();
		acl.setOwner(user01);
		acl.setR(user01);
		body.put("ACL", acl);

		impl.setHeader(head);
		impl.setBody(body);
		log.info(JSON.encode(impl));

		log.info("Keys :" + impl.getBody().keySet());
	}

	private void JsonRead() throws IOException {
		// ファイルの読み込み
		FileReader in = null;
		String buffer = "";
		String line = "";
		String jsonfile = "";

		jsonfile = "pushMessageHistory.json";
		// jsonfile = "pushNotice.json";
		try {
			String path = jsonfileDir + "/" + jsonfile;
			log.info("path :" + path);
			in = new FileReader(path);
			BufferedReader br = new BufferedReader(in);
			while ((line = br.readLine()) != null) {
				buffer += line;
			}
			br.close();
			log.info("encode :" + System.getProperty("file.encoding"));
			log.info("buffer :" + buffer);

			// Json読み込み
			String json = new String(buffer);
			HttpJsonBean impl = new HttpJsonBean();
			impl = JSON.decode(json, HttpJsonBean.class);

			log.info("Keys :" + impl.getBody().keySet());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}

	}

	/** オブジェクト複数登録(バッチ処理) */
	class Requests {
		public Datas[] requests;
	}

	class Datas {
		public String op = "";
		public Map<String, Object> data = new LinkedHashMap<String, Object>();
	}

	private void testCall03() {
		Datas a1 = new Datas();
		a1.op = "insert";
		a1.data.put("_id", "0000");
		a1.data.put("name", "name");
		a1.data.put("score", "100");
		a1.data.put("ACL", "rwcd");
		Datas a2 = new Datas();
		Datas[] requests =
			{ a1, a2 };

		Requests req = new Requests();
		req.requests = requests;
		log.info("JSON:" + JSON.encode(req));
	}

	private void testCall04() {
		ArrayList<String> a1 = new ArrayList<String>();
		a1.add("val1");
		a1.add("val2");
		a1.add("val3");
		log.info("JSON:" + JSON.encode(a1));
	}

	@SuppressWarnings("unchecked")
	private void testCall05() {
		Map<String, Object> impl2 = new LinkedHashMap<String, Object>();
		impl2 = JSON.decode(JSON_SAMPLE03);
		log.info(JSON_SAMPLE03);
		log.info(JSON.encode(impl2));

		log.info("Keys :" + impl2.keySet());
		log.info("class :" + ((List<Map<String, Object>>) impl2.get("results")).get(0).get("message"));
	}

	class ACL {
		private String owner;
		private String r;
		private String w;
		private String c;
		private String d;

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getR() {
			return r;
		}

		public void setR(String r) {
			this.r = r;
		}

		public String getW() {
			return w;
		}

		public void setW(String w) {
			this.w = w;
		}

		public String getC() {
			return c;
		}

		public void setC(String c) {
			this.c = c;
		}

		public String getD() {
			return d;
		}

		public void setD(String d) {
			this.d = d;
		}
	}

	/**
	 * Ethernetフレーム
	 *
	 * @author hiramatsu136
	 *
	 */
	class EthernetBean {
		String dstmac = "";
		String srcmac = "";
		String type = "";
		Arp arp = new Arp();
		Ip ip = new Ip();

		public String getDstmac() {
			return dstmac;
		}

		public void setDstmac(String dstmac) {
			this.dstmac = dstmac;
		}

		public String getSrcmac() {
			return srcmac;
		}

		public void setSrcmac(String srcmac) {
			this.srcmac = srcmac;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Arp getArp() {
			return arp;
		}

		public void setArp(Arp arp) {
			this.arp = arp;
		}

		public Ip getIp() {
			return ip;
		}

		public void setIp(Ip ip) {
			this.ip = ip;
		}

		/** 文字列を返す */
		String getStr() {
			return (dstmac + srcmac + type + arp.getStr() + ip.getStr());
		}

	}

	class Arp {
		String hwtype = "";
		String prottype = "";
		String hwlength = "";
		String protlength = "";
		String operation = "";
		String srcmac = "";
		String srcaddr = "";
		String dstmac = "";
		String dstaddr = "";

		public String getHwtype() {
			return hwtype;
		}

		public void setHwtype(String hwtype) {
			this.hwtype = hwtype;
		}

		public String getProttype() {
			return prottype;
		}

		public void setProttype(String prottype) {
			this.prottype = prottype;
		}

		public String getHwlength() {
			return hwlength;
		}

		public void setHwlength(String hwlength) {
			this.hwlength = hwlength;
		}

		public String getProtlength() {
			return protlength;
		}

		public void setProtlength(String protlength) {
			this.protlength = protlength;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public String getSrcmac() {
			return srcmac;
		}

		public void setSrcmac(String srcmac) {
			this.srcmac = srcmac;
		}

		public String getSrcaddr() {
			return srcaddr;
		}

		public void setSrcaddr(String srcaddr) {
			this.srcaddr = srcaddr;
		}

		public String getDstmac() {
			return dstmac;
		}

		public void setDstmac(String dstmac) {
			this.dstmac = dstmac;
		}

		public String getDstaddr() {
			return dstaddr;
		}

		public void setDstaddr(String dstaddr) {
			this.dstaddr = dstaddr;
		}

		/** 文字列を返す */
		String getStr() {
			return hwtype + prottype + hwlength + protlength + operation + srcmac + srcaddr + dstmac + dstaddr;
		}

	}

	class Ip {
		String version = "";
		String headlen = "";
		String tos = "";
		String datagram = "";
		String id = "";
		String flag = "";
		String ttl = "";
		String protcolno = "";
		String checksum = "";
		String srcip = "";
		String dstip = "";
		String opt = "";
		String data = "";

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getHeadlen() {
			return headlen;
		}

		public void setHeadlen(String headlen) {
			this.headlen = headlen;
		}

		public String getTos() {
			return tos;
		}

		public void setTos(String tos) {
			this.tos = tos;
		}

		public String getDatagram() {
			return datagram;
		}

		public void setDatagram(String datagram) {
			this.datagram = datagram;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getTtl() {
			return ttl;
		}

		public void setTtl(String ttl) {
			this.ttl = ttl;
		}

		public String getProtcolno() {
			return protcolno;
		}

		public void setProtcolno(String protcolno) {
			this.protcolno = protcolno;
		}

		public String getChecksum() {
			return checksum;
		}

		public void setChecksum(String checksum) {
			this.checksum = checksum;
		}

		public String getSrcip() {
			return srcip;
		}

		public void setSrcip(String srcip) {
			this.srcip = srcip;
		}

		public String getDstip() {
			return dstip;
		}

		public void setDstip(String dstip) {
			this.dstip = dstip;
		}

		public String getOpt() {
			return opt;
		}

		public void setOpt(String opt) {
			this.opt = opt;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		/** 文字列を返す */
		String getStr() {
			return version + headlen + tos + datagram + id + flag + ttl + protcolno + checksum + srcip + dstip + opt + data;
		}
	}

	/**
	 * EthernetフレームのJSONファイルを読み込み、オブジェクトにマッピングする
	 *
	 * @throws IOException
	 */
	private void testCall06() throws IOException {
		String jsonfile = "Ethernet.json";
		FileReader in = null;
		try {
			// Json読み込み
			String path = jsonfileDir + "/" + jsonfile;
			log.info("path :" + path);
			in = new FileReader(path);
			BufferedReader br = new BufferedReader(in);
			String line;
			String buffer = "";
			while ((line = br.readLine()) != null) {
				buffer += line;
			}
			br.close();
			String json = new String(buffer);

			// Beanクラスにマッピング
			EthernetBean impl = new EthernetBean();
			impl = JSON.decode(json, EthernetBean.class);

			log.info("Ethernet data:" + impl.getStr());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
