import requests
from bs4 import BeautifulSoup

url = 'https://land.naver.com/news/headline.naver'
#
test = requests.get(url)
print(test.text)
soup = BeautifulSoup(test.text,'html.parser')
print(soup.select('#land_news_list > li > a > div > p'))

