from flask_restful import Resource,reqparse
from flask import jsonify
from flask import make_response
import model.oracledb.model as oracle
# import json

class Diet(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # 아래는 공통 파라미터 설정(key=value로 받기)
        self.parser.add_argument('DESCRIPTION',location='form')
        self.parser.add_argument('MEMO', location='form')
        self.parser.add_argument('DIET_IMAGE', location='form')
        self.parser.add_argument('FOOD', location='form')
        self.parser.add_argument('FOOD_WEIGHT', location='form')
    def get(self,user_id):
        try:
            print(user_id)
            conn = oracle.connectDatabase()
            # print("test",conn)
            print(oracle.selectAll(conn))
            oracle.close(conn)
            lis = ['asdasdasd', '나이스', 'Yellow', 'Green', 'Purple', 'Orange']
            num = [12, 19, 3, 5, 2, 3]
        #
        #     #맘에 안듬
            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
                # print(lis[index])
            return jsonify(j)
        except:
            print("error")

    def post(self,user_id):
        # print(user_id)
        args = self.parser.parse_args()
        # print(type(args))
        conn = oracle.connectDatabase()
        return oracle.insert(conn,user_id,args) #테이블 2개여서 성공이면 2이다