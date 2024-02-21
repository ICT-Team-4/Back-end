import base64, json

from flask_restful import Resource,reqparse
from flask import jsonify, request
import model.workout.workout_model as oracle

class Workout(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # 아래는 공통 파라미터 설정(key=value로 받기)
        self.parser.add_argument('DESCRIPTION',location='form')
        self.parser.add_argument('MEMO', location='form')
        self.parser.add_argument('CATEGORY', location='form')
        self.parser.add_argument('ACCURACY', location='form')
        self.parser.add_argument('COUNTS', location='form')
        self.parser.add_argument('WEIGHT', location='form')
        self.parser.add_argument('END_DATE', location='form')
    def get(self,user_id):
        #args = self.parser.parse_args()
        try:
            dof = request.args.get('date')
            #workout_all=[]
            cal = request.args.get('calId')

            if(cal != None):
                conn = oracle.workout_connectDatabase()
                str1 = oracle.workout_selectOne(conn,cal)
                oracle.workout_close(conn)
                return str1

            conn = oracle.workout_connectDatabase()
            workout_all = oracle.workout_selectAll(conn, user_id, dof)
            oracle.workout_close(conn)
            workDiary =[]
            for i in range(len(workout_all)):
                str1 = 'C:\\Users\\user\\Upload\\' + workout_all[i][3] + '.png'
                with open(str1, "rb") as f:
                    image = base64.b64encode(f.read())
                    workDiary.append(list(workout_all[i])+list(["data:image/png;base64," + str(image)[2:-2]]))

            list_ = ['chart1','workout','chart2','chart3']
            lis = ['Red', 'Good', 'Orange', 'Yellow', 'Green', 'Blue']
            num = [10, 15, 3, 5, 7, 2]

            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
            return jsonify(dict(zip(list_,(j,workDiary,j,j))))
        except:
            print("error")

    def post(self,user_id):
        args = self.parser.parse_args()

        conn1 = oracle.workout_connectDatabase()
        data = oracle.workout_insert(conn1, user_id, args)
        return data  # 테이블 2개여서 성공이면 2이다

    def put(self,user_id):
        args = self.parser.parse_args()
        conn = oracle.workout_connectDatabase()
        data = oracle.workout_update(conn, user_id, args)
        oracle.workout_close(conn)
        return data

    def delete(self,user_id):
        conn = oracle.workout_connectDatabase()
        data = oracle.workout_delete(conn,user_id)
        return data