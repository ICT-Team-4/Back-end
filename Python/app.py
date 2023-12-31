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

#네이버 속보 뉴스 서비스
from api.naver_news import Naver
#OCR서비용
from api.ocr import OCR

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


'''
4.파일 업로드
    POST /uploads
'''
api.add_resource(Upload,'/fileupload')

'''
5.네이버 속보 뉴스 크롤링
GET /naver
'''
api.add_resource(Naver,'/naver')
'''
6.OCR
POST /ocr
'''
api.add_resource(OCR,'/ocr')

if __name__ == '__main__':
    app.run(debug=True)