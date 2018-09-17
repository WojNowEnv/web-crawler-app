package crawler;

public class Application {

	public static void main(String[] args) {
		String linkToCrawlInto = "https://wiprodigital.com";
		
		System.out.println("Hello. I'm starting fetching into"+linkToCrawlInto+"... Please hang tight!");
		Crawler crawler = new Crawler(linkToCrawlInto);
		
		
		Thread waiting = new Thread(() -> {
			for (int i = 0; i<=7;i++) {
				try {
					Thread.sleep(1000);
					System.out.print(".");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.print("Please just wait ;)");
		});
		waiting.start();
		
		
		try {
			// web map is builed here
			crawler.buildWebSiteMaps();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// displaying "flat structure map"
		for (String key : crawler.getWebMapForDomain().keySet()) {
			System.out.println("PARRENT URL: "+key+"\n");
			
			System.out.println("Domain sites:");
			crawler.getWebMapForDomain().get(key).stream().map(x -> x.attr("abs:href")).distinct().forEach(System.out::println);
			System.out.println("\n");
			
			System.out.println("Media(images):");
			crawler.getWebMapForMedia().get(key).stream().map(x -> x.attr("abs:src")).distinct().forEach(System.out::println);
			System.out.println("\n");
			
			System.out.println("External links:");
			crawler.getWebMapForExternalDomains().get(key).stream().map(x -> x.attr("abs:href")).distinct().forEach(System.out::println);
			System.out.println("\n");
		}
	}
	

}
