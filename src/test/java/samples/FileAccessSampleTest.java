package samples;

import java.io.IOException;
import java.lang.reflect.Field;
import org.junit.Test;
import base.IActor;
import mockit.Deencapsulation;

public class FileAccessSampleTest implements IActor {

	FileAccessSample fileac = new FileAccessSample();


	@Override
	public void close() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void action() throws Exception {
		finalFieldAccess();

	}

	@Override
	public void init() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void terminate() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Test
	private void finalFieldAccess() {

		// Before
		System.out.println("--- Before");
		fileac.dispFinalField();

		try {
			Field field = FileAccessSample.class.getDeclaredField("DUMMYPATH");
			field.setAccessible(true);
			// field.set(null, "ABCDEFGHIJK");
			Deencapsulation.setField(FileAccessSample.class, "DUMMYPATH", "ABCDEFGHIJK");

		} catch (SecurityException | NoSuchFieldException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// // TODO 自動生成された catch ブロック
			// e.printStackTrace();
		}

		// After
		System.out.println("--- After");
		fileac.dispFinalField();
	}

}
