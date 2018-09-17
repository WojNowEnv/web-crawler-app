package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	// variables which stores maps for certain types of content
	private Map<String, Elements> webMapForDomain = new LinkedHashMap<>();
	private Map<String, Elements> webMapForExternalDomains = new LinkedHashMap<>();
	private Map<String, Elements> webMapForMedia = new LinkedHashMap<>();
	
	// tells which sites was visited already
	private List<String> index = new ArrayList<>(); 
	private String webSiteDomainName = "";	
	
	public Crawler(String URL) {
		this.webSiteDomainName = URL;
	}
	
	public Map<String, Elements> getWebMapForDomain() {
		return this.webMapForDomain;
	}
	
	public Map<String, Elements> getWebMapForExternalDomains() {
		return this.webMapForExternalDomains;
	}
	
	public Map<String, Elements> getWebMapForMedia() {
		return this.webMapForMedia;
	}
	
	public void buildWebSiteMaps() throws Exception {
		if (!webSiteDomainName.isEmpty()) {
			crawlInto(webSiteDomainName);
		}else {
			throw new Exception();
		}
	}
	
	private void crawlInto(String URL){
		if (!webMapForDomain.keySet().contains(URL) && !this.webSiteDomainName.isEmpty()) {
            try {
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                Elements linksOnPageforMedia = document.select("img[src]");
                
                Elements linksOnPageForDomain = new Elements();
                Elements linksOnPageForExternalURLs = new Elements();
                
                // divide href Elements into external and wanted domain URLs
                for (Element item : linksOnPage) {
                	if(item.attr("abs:href").contains(webSiteDomainName)){
                		linksOnPageForDomain.add(item);
                	}else {
                		linksOnPageForExternalURLs.add(item);
                	}
                }
        		
                this.webMapForDomain.put(URL, linksOnPageForDomain);
                this.webMapForExternalDomains.put(URL, linksOnPageForExternalURLs);
                this.webMapForMedia.put(URL, linksOnPageforMedia);
                
                this.index.add(URL);
                
                // recursive mechanism to visit sites which we are interested in: under same domain and not visited yet
                for (Element item : linksOnPage) {
                	String tempURL = item.attr("abs:href");
                	if (tempURL.contains(this.webSiteDomainName) && !this.webMapForDomain.keySet().contains(tempURL) && !index.contains(tempURL) ) {
                		this.index.add(tempURL);
                		crawlInto(tempURL);		
                	}
                }

            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
		
	}
}
