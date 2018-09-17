# web-crawler-app
It's command line app which builds website map for given URL (URL "https://wiprodigital.com" hardcored).

To run this app you should simply clone git repository by using e.g. Eclipse IDE and run Application.main(). 

You can also use web-crawler-app.jar file included in /target directory. Just run on Java configured environment (from folder when jar is placed) through cmd: "java -jar web-crawler-app.jar".

The main problems in achieving desired solution was to
- find simple way to parse htmls responses - I used jsoup library,
- deal with storing URLs in the structure which would enable user to display found URLs in structured way. I used map <String, Elements>,
- avoid loops in visitting and storing URLs processess (e.g. https://wiprodigital.com includes -> https://wiprodigital.com/something includes -> https://wiprodigital.com includes -> https://wiprodigital.com/something includes), I controlled this by tracking which URLs was visited and used rucurrency


What is to do next with more time?

I decided to display links in flat structure (for every single visited page - given is list of URLs from it). It can be displayed in tree structure also.  

Second thing which can be done is of course building simple Spring based web app with simple web form for giving start URL input.



I hope that it's enough for initial/screening task. Sorry for possible mistakes.
