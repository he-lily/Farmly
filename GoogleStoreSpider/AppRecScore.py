import sqlite3


def assignPriceScore(price):
    if (0.0 == price):
        return 10
    elif (0.0 < price and price < 1.00):
        return 9
    elif (1.0 <= price and price < 2.00):
        return 8
    elif (2.00 <= price and price < 3.00):
        return 7
    elif (3.00 <= price and price < 4.00):
        return 6
    elif (4.00 <= price and price < 5.00):
        return 5
    elif (5.00 <= price and price < 7.50):
        return 4
    elif (7.50 <= price and price < 10.00):
        return 3
    elif (10.00 <= price and price < 20.00):
        return 2
    else:
        return 1


def assignDownloadsScore(downloads):
    if (downloads in ["10+", "50+", "100+", "500+", "1,000+"]):
        return 1
    elif (downloads in ["5,000+", "10,000+"]):
        return 2
    elif (downloads in ["50,000+", "100,000+"]):
        return 3
    elif (downloads == "500,000+"):
        return 4
    elif (downloads == "1,000,000+"):
        return 5
    elif (downloads == "5,000,000+"):
        return 6
    elif (downloads == "10,000,000+"):
        return 7
    elif (downloads in ["50,000,000+", "100,000,000+"]):
        return 8
    elif (downloads == "500,000,000+"):
        return 9
    elif (downloads in ["5,000,000,000+", "1,000,000,000+"]):
        return 10

    
def assignRatingScore(score):
    if (4.0 <= score and score < 4.1):
        return 1
    elif (4.1 <= score and score < 4.2):
        return 2
    elif (4.2 <= score and score < 4.3):
        return 3
    elif (4.3 <= score and score < 4.4):
        return 4
    elif (4.4 <= score and score < 4.5):
        return 5
    elif (4.5 <= score and score < 4.6):
        return 6
    elif (4.6 <= score and score < 4.7):
        return 7
    elif (4.7 <= score and score < 4.8):
        return 8
    elif (4.8 <= score and score < 4.9):
        return 9
    else:
        return 10


def assignReviewScore(score):
    if (1000 > score):
        return 1
    elif (1000 <= score and score < 10000):
        return 2
    elif (10000 <= score and score < 50000):
        return 3
    elif (50000 <= score and score < 100000):
        return 4
    elif (100000 <= score and score < 500000):
        return 5
    elif (500000 <= score and score < 750000):
        return 6
    elif (750000 <= score and score < 100000):
        return 7
    elif (100000 <= score and score < 250000):
        return 8
    elif (250000 <= score and score < 500000):
        return 9
    else:
        return 10
    

def main():    
    connection = sqlite3.connect("GoogleStoreApps.db")
    cursor     = connection.cursor()

    select_query = """SELECT * from App"""

    cursor.execute(select_query)    
    records = cursor.fetchall()

    appID = 1
    for row in records:
        appRating    = assignRatingScore(row[5])
        appReviews   = assignReviewScore(row[6])
        appDownloads = assignDownloadsScore(row[7])
        appPrice     = assignPriceScore(row[8])

        appBaseScore = appRating + appReviews + appDownloads + appPrice

        sql = ''' UPDATE App
                  SET BASESCORE = ?
                  WHERE NAME = ?'''
        
        cursor.execute(sql, (appBaseScore, row[0]))
        connection.commit()
        
        appID += 1


    cursor.close()
    connection.close()
    
    
##          
##if __name__ == '__main__':
##    main()
