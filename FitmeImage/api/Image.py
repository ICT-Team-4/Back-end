import json
from io import BytesIO

from flask import make_response
from flask_restful import Resource,reqparse
import model.oracledb.image_model as oracle
from flask import jsonify
import werkzeug

import base64
from PIL import Image

class Image(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('uploads', location='form')

    def get(self, id):
        str1 = 'C:\\Users\\user\\Upload\\' + str(id) + '.png'
        with open(str1, "rb") as f:
            # 이미지를 열어서 PIL 이미지 객체로 변환
            image = Image.open(f)

            # 이미지 크기가 760x760보다 작으면 원본 이미지를 반환
            if image.size[0] <= 760 and image.size[1] <= 760:
                with open(str1, "rb") as f:
                    base64_string = base64.b64encode(f.read())
                return {'image': base64_string.decode('utf-8')}

            # 이미지 크기가 760x760보다 크면 크기를 조정하여 반환
            else:
                # 이미지 크기를 조정하여 새로운 PIL 이미지 객체 생성
                resized_image = image.resize((760, 760))
                # 조정된 이미지를 바이트 스트림으로 변환하여 base64 인코딩
                buffered = BytesIO()
                resized_image.save(buffered, format="PNG")
                base64_string = base64.b64encode(buffered.getvalue())
                return {'image': base64_string.decode('utf-8')}
    def put(self,id):
        print('>>>',id)
        args = self.parser.parse_args()
        image = args['uploads']
        print('adadsas',image)
        str1 = 'C:\\Users\\user\\Upload\\' + str(id) + '.png'
        with open(str1, "wb") as f:
            f.write(base64.b64decode(image.encode()))
        return '성공'