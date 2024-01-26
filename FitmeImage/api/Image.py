import json

from flask import make_response
from flask_restful import Resource,reqparse
import model.oracledb.image_model as oracle
from flask import jsonify
import werkzeug

import base64

class Image(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # 아래는 공통 파라미터 설정(key=value로 받기)
        # self.upload_path= os.path.join(os.getcwd(),'uploads')
        self.parser.add_argument('uploads')
    def get(self,id):
        # print(id)

        # conn = oracle.connectDatabase()
        # data = oracle.select(conn, id)
        # print('get',data[0])

        str1 = 'C:\\Users\\user\\Upload\\' + str(id) + '.png'
        with open(str1, "rb") as f:
            base64_string1 = base64.b64encode(f.read())
        # print(base64_string1)
        return {'image':base64_string1.decode('utf8')}
        # return jsonify(data)  # 테이블 2개여서 성공이면 2이다
    def post(self,id):
        args = self.parser.parse_args()
        # print(id,':',args['uploads'])
        conn = oracle.connectDatabase()
        data = oracle.insert(conn)
        # print(data[0])
        strDe = args['uploads']

        str1 = 'C:\\Users\\user\\Upload\\'+str(data[0])+'.png'
        with open(str1, "bw") as f:
            f.write(base64.b64decode(strDe.encode()))
            # print(str1)
        return data[0]
    def delete(self,id):
        print(id)