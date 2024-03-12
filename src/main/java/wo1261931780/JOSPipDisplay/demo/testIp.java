package wo1261931780.JOSPipDisplay.demo;

import org.lionsoul.ip2region.xdb.Searcher;

import java.util.concurrent.TimeUnit;

/**
 * Created by Intellij IDEA.
 * Project:JOSP-ipDisplay
 * Package:wo1261931780.JOSPipDisplay.demo
 *
 * @author liujiajun_junw
 * @Date 2024-03-10-37  星期二
 * @Description
 */
public class testIp {

	public static void main(String[] args) throws Exception {
		// 1、创建 searcher 对象 (修改为离线库路径)
		String dbPath = "src/main/java/wo1261931780/JOSPipDisplay/demo/ip2region.xdb";
		// 上面这个文件必须存在，用来解析，也是作为数据库的来源
		Searcher searcher = null;
		try {
			searcher = Searcher.newWithFileOnly(dbPath);
		} catch (Exception e) {
			System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
			return;
		}

		// 2、查询
		// String ip = "110.242.68.66";
		String ip = "74.48.4.5"; //{region: 加拿大|0|安大略|多伦多|Telus, ioCount: 4, took: 183 μs}
		try {
			long sTime = System.nanoTime(); // Happy java
			String region = searcher.search(ip);
			long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
			System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
		} catch (Exception e) {
			System.out.printf("failed to search(%s): %s\n", ip, e);
		}

		// 3、关闭资源
		searcher.close();

		// 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
	}
}
