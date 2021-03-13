import re
import json
import scrapy
from scrapy import signals
from collections import defaultdict
from scrapy.selector import Selector
from scrapy.exceptions import CloseSpider
from sqlite_appDB import GoogleStoreAppDB


## 21 categories * 101 apps per = 2121 row DB

class appStoreSpider(scrapy.Spider):
	name = "appStore"

	def __init__(self):
		self.MAX_VAL = 100
		self.all_apps = set() 
		self.category_dict = defaultdict(list)
		self.app_pattern = 'https://play\.google\.com/store/apps/details\?id=(\w|.)+'
		self.valid_categories = {'ART_AND_DESIGN', 'BEAUTY', 'DATING',
					 'EDUCATION', 'ENTERTAINMENT', 'FINANCE',
					 'FOOD_AND_DRINK', 'GAME', 'HEALTH_AND_FITNESS',
					 'HOUSE_AND_HOME', 'LIFESTYLE', 'MEDICAL',
					 'MUSIC_AND_AUDIO', 'NEWS_AND_MAGAZINES', 'PARENTING',
					 'PHOTOGRAPHY', 'PRODUCTIVITY', 'SHOPPING',
					 'SOCIAL', 'SPORTS', 'TRAVEL_AND_LOCAL'}
		self.log_file   = open("spider_logger.txt", 'w')
		self.categories = open("categories.json", 'w')
		
	
	## start paths include all 21 app categories. Spider is then instructed to run through them
	def start_requests(self):
		start_paths = ["https://play.google.com/store/apps/category/ART_AND_DESIGN",
			       "https://play.google.com/store/apps/category/BEAUTY",
			       "https://play.google.com/store/apps/category/DATING",
			       "https://play.google.com/store/apps/category/EDUCATION",
			       "https://play.google.com/store/apps/category/ENTERTAINMENT",
			       "https://play.google.com/store/apps/category/FINANCE",
			       "https://play.google.com/store/apps/category/FOOD_AND_DRINK",
			       "https://play.google.com/store/apps/category/GAME",
			       "https://play.google.com/store/apps/category/HEALTH_AND_FITNESS",
			       "https://play.google.com/store/apps/category/HOUSE_AND_HOME",
			       "https://play.google.com/store/apps/category/LIFESTYLE",
			       "https://play.google.com/store/apps/category/MEDICAL",
			       "https://play.google.com/store/apps/category/MUSIC_AND_AUDIO",
			       "https://play.google.com/store/apps/category/NEWS_AND_MAGAZINES",
			       "https://play.google.com/store/apps/category/PARENTING",
			       "https://play.google.com/store/apps/category/PHOTOGRAPHY",
			       "https://play.google.com/store/apps/category/PRODUCTIVITY",
			       "https://play.google.com/store/apps/category/SHOPPING",
			       "https://play.google.com/store/apps/category/SOCIAL",
			       "https://play.google.com/store/apps/category/SPORTS",
			       "https://play.google.com/store/apps/category/TRAVEL_AND_LOCAL"]
		for path in start_paths:
			yield scrapy.Request(path, self.parse)

			
	## Used for debugging purposes in case of a variety of failures to add an app to the database
	def _write_to_log(self, statement, app_name, app_url):
		try:
			self.log_file.write(statement + app_name + "\n" + app_url + "\n\n")
		except:
			self.log_file.write(statement + "  [UNABLE TO DECODE APP INFO -> UNDEFINED]\n\n")

			
	
	def parse(self, response):
		resp_sel = Selector(response)
		sites = resp_sel.xpath('//a/@href').extract() 

		for site in sites:
			## Finds the links and runs through them
			url = response.urljoin(site)
			if (re.match(self.app_pattern, url)):
				yield scrapy.Request(url, self.parse)

		## Gathers application info if is an app
		app_info = response.xpath('//script[@type="application/ld+json"]//text()').extract_first()
		
		
		if (app_info):
			app_json = json.loads(app_info)
			
			## assigns correct category to app
			if 'GAME' in app_json['applicationCategory']:
				genre = 'GAME'
			else:
				genre = app_json['applicationCategory']
				
			# Validates catgeory is still being searched and we are still searching for apps
			if (len(self.valid_categories) > 0):
				if (genre in self.valid_categories):
					try:
						## gathers all neeeded info from each category
						app_id = response.url.split("?id=")[-1].split("&")[0]

						if (app_id not in self.all_apps):
							review_count = int(app_json['aggregateRating']['ratingCount'])
							rating_score = float(app_json['aggregateRating']['ratingValue'])

							if (review_count >= 10 and rating_score >= 4.5):
								name      = app_json['name']
								url       = app_json['url']
								app_logo  = app_json['image']
								downloads = response.xpath("//div[contains(text(), 'Installs')]/..//text()").getall()[1]
								price     = app_json['offers'][0]['price']
								
								## Success! Adds app to db
								appDBhelper = GoogleStoreAppDB()
								succeeded = appDBhelper.insert(name, app_id, genre, url, app_logo, rating_score, review_count, downloads, price)

								if (succeeded):
									self.all_apps.add(app_id)
									self.category_dict[genre].append({'name':name, 'url':url, 'logo':app_logo, 'rating_score':rating_score, 
													  'review_count': review_count, 'downloads':downloads, 'price':price})

									if (len(self.category_dict[genre]) > self.MAX_VAL):
										self.valid_categories.remove(genre)
								else:
									self._write_to_log("ERROR ADDING TO DB ON: ", app_json['name'], app_json['url'])
							else:
								self._write_to_log("NOT GOOD ENOUGH ON: ", app_json['name'], app_json['url'])
						else:
							self._write_to_log("DUPLICATE ON: ", app_json['name'], app_json['url'])
					except:
						self._write_to_log("ERROR ON: ", app_json['name'], app_json['url'])
				else:
					self._write_to_log("INVALID GENRE: ("+genre+") on: ", app_json['name'], app_json['url'])
			else:
				raise CloseSpider('Received sufficent apps')


	@classmethod
	def from_crawler(cls, crawler, *args, **kwargs):
		spider = super(appStoreSpider, cls).from_crawler(crawler, *args, **kwargs)
		crawler.signals.connect(spider.spider_closed, signal=signals.spider_closed)
		return spider


	def spider_closed(self, spider):
		self.log_file.close()
		json.dump(self.category_dict, self.categories)
		self.categories.close()
