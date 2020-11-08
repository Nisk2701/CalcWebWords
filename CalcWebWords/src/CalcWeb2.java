import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CalcWeb2 {
	public static void main(String args[]) throws IOException {

		String urlStr = "https://www.314e.com/";
		String link_text = new String();
		StringBuffer sbWHole = new StringBuffer();

		try {
			String sb = openCOnnection(urlStr);

			Document doc = Jsoup.parse(sb.toString());
			sbWHole.append(sb);
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				// get the value from the href attribute

				if (link.attr("href").contains(urlStr)) {

					link_text = openCOnnection(link.attr("href"));

					sbWHole.append(Jsoup.parse(link_text).body().text());

				}

			}
			frequestWords(sbWHole.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String openCOnnection(String urlStr) throws IOException {
		URL url;
		StringBuffer sb = new StringBuffer();
		String inputLine;
		try {
			url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while ((inputLine = in.readLine()) != null)
				sb.append(inputLine);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Page not found " + e.getMessage());
		}
		return sb.toString();

	}

	public static void frequestWords(String resultString) {

		List<String> list = new ArrayList<String>();
		list = Arrays.asList(resultString.split(" "));
		System.out.println(list);
		Map<String, Long> map = list.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

		List<Map.Entry<String, Long>> result = map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toList());

		System.out.println("frequent words of all the scrawled web pages are: " + result);

	}

}