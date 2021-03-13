import sqlite3
from sqlite3 import Error

## Called from appstore_spider.py. Opens and inserts the app and its info the the Application Database

class GoogleStoreAppDB:
    def __init__(self):
        self.name       = "GoogleStoreApps.db"
        self.connection = sqlite3.connect(self.name)
        self.cursor     = self.connection.cursor()

        try:
            _connection = sqlite3.connect(self.name)
            _c = _connection.cursor()
            _c.execute("CREATE TABLE App(NAME,ID,CATEGORY,URL,LOGO,RATINGSCORE,REVIEWCOUNT,DOWNLOADS,PRICE)")
            _c.close()
        except sqlite3.OperationalError:
            pass
        

    def insert(self, name, app_id, category, url, logo, score, reviews, downloads, price):
        try:
            query = "INSERT INTO App(NAME,ID,CATEGORY,URL,LOGO,RATINGSCORE,REVIEWCOUNT,DOWNLOADS,PRICE) VALUES ('" + name + "','" + app_id + "','" + category + "','" + url +  "','" + logo + "'," + str(score) + "," + str(reviews) + ",'" + downloads + "'," + str(price) + ")"
            self.cursor.execute(query)
            self.connection.commit()
            self.connection.close()
            return True
            
        except:
            return False
