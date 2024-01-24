from flask_restful import Resource,reqparse
from flask import jsonify , request
from flask import make_response
import model.oracledb.diet_model as oracle
import json

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
        args = self.parser.parse_args()
        # print(args)
        try:
            #겟 파라미터 받아오는 법(http://localhost:5000/diet/3?param1=23)
            dof = request.args.get('date')
            print(dof)

            list_ = ['chart1','mealTime'] #리액트로 보내줄 헤더
            
            conn = oracle.diet_connectDatabase()
            # # # print("test",conn)
            meal_all = oracle.diet_selectAll(conn,user_id,dof)
            oracle.diet_close(conn)
            lis = ['asdasdasd', '나이스', 'Yellow', 'Green', 'Purple', 'Orange1']
            num = [12, 19, 3, 5, 2, 3]

            # headers = []
            # body = []
            # for index in range(len(meal_all)):
            #     headers.append(meal_all[index][0])
            #     body.append(meal_all[index][1:])
            # test = dict(zip(headers,body))

        #     #맘에 안듬
            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
                # print(lis[index])

            return jsonify(dict(zip(list_,(j,meal_all))))
        except:
            print("error")

    def post(self,user_id):
        # print(user_id)
        args = self.parser.parse_args()
        # print(type(args))
        conn = oracle.diet_connectDatabase()
        return oracle.diet_insert(conn,user_id,args) #테이블 2개여서 성공이면 2이다

    # def get(self,user_id): #사용자가 날짜를 클릭하는데 date값도 같이 받아야하는거 아닌가...?ㅠㅠ
    #     try:
    #         conn = oracle.diet_connectDatabase()
    #         return make_response(json.dumps(oracle.diet_selectOne(conn,user_id),ensure_ascii=False))
    #     except:
    #         print("error")