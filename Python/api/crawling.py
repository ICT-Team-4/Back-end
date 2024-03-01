from flask_restful import Resource
from flask import make_response
from model.crawling.google_crawling import google_crawling
from model.crawling.naver_blog_crawling import naver_crawling
from model.crawling.crawling_recipe import recipe
import json

class Crawling(Resource):
    def get(self):
        # print(recipe('중식'))
        return make_response(json.dumps({'중식':recipe('중식'),'양식':recipe('양식'),'일식':recipe('일식')},ensure_ascii=False))
        # pass
#         articles=naver_news_it()
#         #print(articles)#[((제목,링크주소),이미지URL,요약,신문사),(),(),....]
#         news_dict=[]
#         for titles,imageUrl,summary,_ in articles:#구조분해해서 변수에 저장
#             title,link = titles#구조 분해
#             news_dict.append({'title':title,'link':link,'imageUrl':imageUrl,'summary':summary})
#
#         #print(news_dict)
#         return make_response(json.dumps(news_dict,ensure_ascii=False))