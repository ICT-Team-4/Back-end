from flask_restful import Resource,reqparse
from flask import jsonify, request
from flask import make_response
import model.workout.workout_model as oracle
import model.workout.publicData_model as pub

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
        args = self.parser.parse_args()
        print(args)
        try:
            dof = request.args.get('date')
            cal = request.args.get('calId')
            if(cal != None):
                conn = oracle.workout_connectDatabase()
                str1 = oracle.workout_selectOne(conn,cal)
                oracle.workout_close(conn)
                print(str1)
                return  str1

            conn = oracle.workout_connectDatabase()
            workout_all = oracle.workout_selectAll(conn,user_id,dof)
            oracle.workout_close(conn)
            list_ = ['chart1','workoutDiary','chart2','chart3']
            lis = ['Red', 'Good', 'Orange', 'Yellow', 'Green', 'Blue']
            num = [10, 15, 3, 5, 7, 2]

            pub_data = []
            if len(workout_all) > 0:
                for i in range(len(workout_all)):
                    data = workout_all[i][2]
                    pub_data1 = pub.line(data)
                    pub_data.append(pub_data1[0] if len(pub_data1) > 0 else [])

            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
            print(workout_all)
            return jsonify(dict(zip(list_,(j,workout_all,j,j))))
        except:
            print("error")

    def post(self,user_id):
        # print(user_id)
        args = self.parser.parse_args()
        # print(type(args))
        conn = oracle.workout_connectDatabase()
        data = oracle.workout_insert(conn, user_id, args)
        return data #테이블 2개여서 성공이면 2이다

    def put(self,cal_id):
        print(cal_id)

    def delete(self,user_id):
        conn = oracle.workout_connectDatabase()
        data = oracle.workout_delete(conn,user_id)
        return data

    # def get(self,user_id): #사용자가 날짜를 클릭하는데 date값도 같이 받아야하는거 아닌가...?ㅠㅠ
    #     try:
    #         conn = oracle.excercise_connectDatabase()
    #         return make_response(json.dumps(oracle.excercise_selectOne(conn,user_id),ensure_ascii=False))
    #     except:
    #         print("error")