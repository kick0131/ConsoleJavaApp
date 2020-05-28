package samples.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 色々なJSONパターン
 * マッピング先はJsonMappingDummyを想定
 */
class JsonCommonTestPattern {
  private List<String> testPatternList = new ArrayList<String>();

  public JsonCommonTestPattern() {
    // マッピングパラメータ無し
    testPatternList.add("{\"key1\":\"value1\",\"key2\":123}");
    // マッピングパラメータ有り
    testPatternList.add("{\"name\":\"value1\",\"age\":123}");
    // ネストされたマッピングパラメータ
    testPatternList.add("{\"body\":{\"name\":\"value1\",\"age\":123}}");
    // API Gateway形式(ネストされたValue全体が文字列)のマッピングパラメータ
    testPatternList.add("{\"body\": \"{\\\"name\\\":\\\"value1\\\",\\\"age\\\":123}\"}");
  }

  /**
   * 指定したパターンを取り出す
   */ 
  public String pattern(int idx){
    return testPatternList.get(idx);
  }
  /**
   * 全パターンを返す
   * @return
   */
  public List<String> allPattern(){
    return testPatternList;
  }
}