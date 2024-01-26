from flask_restful import Resource,reqparse
from flask import jsonify, request
from flask import make_response
import model.exercise.exercise_model as oracle


class Exercise(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # 아래는 공통 파라미터 설정(key=value로 받기)
        self.parser.add_argument('DESCRIPTION',location='form')
        self.parser.add_argument('MEMO', location='form')
        self.parser.add_argument('CATEGORY', location='form')
        self.parser.add_argument('ACCURACY', location='form')
        self.parser.add_argument('COUNTS', location='form')
        self.parser.add_argument('WEIGHT', location='form')
    def get(self,user_id):
        args = self.parser.parse_args()
        print(args)
        try:
            dof = request.args.get('date')
            lis = ['asdasdasd', '나이스', 'Yellow', 'Green', 'Purple', 'Orange1']
            num = [12, 19, 3, 5, 2, 3]
            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
            return jsonify(j)
        except:
            print("error")

    def post(self,user_id):
        # print(user_id)
        args = self.parser.parse_args()
        # print(type(args))
        conn = oracle.excercise_connectDatabase()
        data = oracle.excercise_insert(conn, user_id, args)
        return data #테이블 2개여서 성공이면 2이다

    # def get(self,user_id): #사용자가 날짜를 클릭하는데 date값도 같이 받아야하는거 아닌가...?ㅠㅠ
    #     try:
    #         conn = oracle.excercise_connectDatabase()
    #         return make_response(json.dumps(oracle.excercise_selectOne(conn,user_id),ensure_ascii=False))
    #     except:
    #         print("error")