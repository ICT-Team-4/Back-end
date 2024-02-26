'''
https://flask-restful.readthedocs.io/en/latest/
1. pip install flask
2. pip install flask-restful
3. pip install flask_cors
'''
from flask import Flask
from flask_restful import Api
from flask_cors import CORS
import os

#내가 만든 RESTFul API서비스용 클래스 모듈들
from api.upload import Upload

#OCR
from api.ocr import Ocr
#크롤링
from api.crawling import Crawling
#식단 데이타 보내기
from api.diet import Diet
from api.calendarLike import CalendarLike
#운동 데이터 보내기
from api.workout import Workout

#public
from api.account import Account

from api.publicData import Public

#챗봇
from api.chatbot import Chatbot
#챗이미지 생성
from api.chatImage import ChatImage
#AI 운동 추천
from api.workout_recommend import WorkoutsRecommend
#AI 음식 추천
from api.foods_recommend_api import FoodsRecommend

from api.food_detection import FoodDetection

from api.ServiceWorker import ServiceWorker

#플라스크 앱 생성
app = Flask(__name__)
#CORS에러 처리
CORS(app)

#업로드용 폴더 설정
UPLOAD_ROOT=os.getcwd()
app.config['UPLOAD_FOLDER']=os.path.join(UPLOAD_ROOT, 'uploads')
#최대 파일 업로드 용량 1M로 설정
app.config['MAX_CONTENT_LENGTH']= 1* 1024 * 1024
#플라스크 앱(app)을 인자로 하여 Api객체 생성:URI 주소 즉 자원의 주소와 클래스를 매핑해서 요청을
#라우팅 즉 @app.route('/todos/<todo_id>')와 같다
api = Api(app)

api.add_resource(Upload,'/fileupload')

#OCR
api.add_resource(Ocr,'/ocr')
#크롤링 네이버 구글
api.add_resource(Crawling,'/crawling')
#식단 데이타 보내기
api.add_resource(Diet,'/diet/<user_id>')
api.add_resource(CalendarLike,'/calendarLike/<cal_id>')
# api.add_resource(Diet,'/diet')
#운동 데이터 보내기
api.add_resource(Workout,'/workout/<user_id>')
#어카운트 정보 받아오기
api.add_resource(Account,'/account/<user_id>')
#공공데이타
api.add_resource(Public,'/public')
#챗봇
api.add_resource(Chatbot,'/chatbot')
#챗이미지 생성
api.add_resource(ChatImage,'/chatImage')
#AI운동 추천
api.add_resource(WorkoutsRecommend,'/workoutsRecommend/<username>')

api.add_resource(FoodDetection,'/food')

api.add_resource(ServiceWorker, '/serviceWorker')

api.add_resource(FoodsRecommend,'/recommend/<username>')

if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True)