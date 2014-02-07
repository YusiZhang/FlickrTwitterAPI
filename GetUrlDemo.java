import java.io.*;
import java.net.*;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Reference : http://java4ever.blogspot.com/2008/08/play-with-flickr-api-in-java.html
 */

/**
 * @author yusizhang
 * 
 */
public class GetUrlDemo {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws Exception {
		String apiKey = "a55657f58894d528626736d4b521a550";
		String secret = "41dd338f9704a940";
		String method = "flickr.photos.search";
		String text = "football";
		String per_page = "10";
		int count = Integer.parseInt(per_page);
		
		
		
		
		URLConnection uc = new URL(
				"http://api.flickr.com/services/rest/?method=" + method
						+ "&api_key=" + apiKey + "&per_page=" + per_page
						+ "&text=" + text).openConnection();
//		System.out.println(uc.toString());
//		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//		FileWriter fw = new FileWriter(new File("src/test.xml"));
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/test.xml")));
		
		String nextline;
		String[] servers = new String[count];
		String[] ids = new String[count];
		String[] secrets = new String[count];
		while ((nextline = br.readLine()) != null) {
//			fw.append(nextline);
//			bw.append(nextline);
			
			bw.write(nextline);//fastest the way to read and write
		}
		
		br.close();
//		fw.close();
		bw.close();
		
		String filename = "src/test.xml";
		XMLInputFactory factory = XMLInputFactory.newInstance();
//		System.out.println("FACTORY: " + factory);
		
		XMLEventReader r = factory.createXMLEventReader(filename, new FileInputStream(filename));
		int i = -1;
		while(r.hasNext()){
			XMLEvent event = r.nextEvent();
			if(event.isStartElement()){
				StartElement element = (StartElement) event;
				String elementName = element.getName().toString();
				
				if (elementName.equals("photo")) {//xml element starts with photo
					i++;
					Iterator iterator = element.getAttributes();

					while (iterator.hasNext()) {

						Attribute attribute = (Attribute) iterator.next();
						/*
						 * attribute has two methods : 
						 * getName(server,id,secret,owner,farm,title etc) 
						 * getValue
						 */
						QName name = attribute.getName();
						String value = attribute.getValue();
//						System.out.println("Attribute name/value: " + name + "/" + value);
						if ((name.toString()).equals("server")) {
							servers[i] = value;
//							System.out.println("Server Value" + servers[0]);
						}
						if ((name.toString()).equals("id")) {
							ids[i] = value;
						}
						if ((name.toString()).equals("secret")) {
							secrets[i] = value;
						}
					}
				}
			}
		}
		System.out.println(i);
		
		for(int j=0;j<i;j++){
			String flickrurl = "http://static.flickr.com/" + servers[j] + "/" + ids[j] + "_" + secrets[j] + ".jpg";
			System.out.println(flickrurl);
		}
	}

}
