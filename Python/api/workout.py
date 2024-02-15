import base64, json

from io import BytesIO
from flask_restful import Resource,reqparse
from flask import jsonify, request
from flask import make_response
import model.workout.workout_model as oracle
import model.image_oracledb.image_model as imagedb
# import model.workout.publicData_model as pub

class Workout(Resource):
    def __init__(self):
        # print(self,'self')
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
        # print(args,'args')
        try:
            dof = request.args.get('date')
            # print(dof,'dof')
            workout_all=[]
            cal = request.args.get('calId')
            # print(cal,'cal')
            if(cal != None):
                conn = oracle.workout_connectDatabase()
                str1 = oracle.workout_selectOne(conn,cal)
                oracle.workout_close(conn)
                # print('str1:',str1)
                return str1

            conn = oracle.workout_connectDatabase()
            workout_all = oracle.workout_selectAll(conn, user_id, dof)
            oracle.workout_close(conn)
            # 공공데이터 저장된 카테고리 이미지 연결
            # image = args['CATEGORY']
            # if image != '':
            #     conn = imagedb.connectDatabase()
            #     data = imagedb.select(conn)
            #     str1 = 'C:\\Users\\user\\Upload\\' + str(data[0]) + '.png'
            #     args['CATEGORY'] = str(data[0])
            #     with open(str1, "rb") as f:
            #         f.read(base64.b64decode(image.encode()))

            workDiary =[]
            # print('workout_all---',workout_all)
            # print('workout_all', workout_all)
            for i in range(len(workout_all)):
                # print('>>>', workout_all[i][3])
                str1 = 'C:\\Users\\user\\Upload\\' + workout_all[i][3] + '.png'
                # print(str1)
                with open(str1, "rb") as f:
                    image = base64.b64encode(f.read())
                    # print('image_encoded',image)
                    workDiary.append(list(workout_all[i])+list(["data:image/png;base64," + str(image)[2:-2]]))



            # 공공데이터 연결
            # workoutDiary = []
            # for i in range(len(workout_all)):
            #     id = 41 if workout_all[i][4] == None else workout_all[i][4]
            #     print(id,'id')
            #     str1 = 'C:\\Users\\user\\Upload\\' + str(id) + '.png'
            #
            #     with open(str1, "rb") as f:
            #         f.seek(0)
            #         image = base64.b64encode(f.read())
            #         workoutDiary.append(list(workout_all[i][4])+list(["data:image/png;base64,"+str(image)[2:-2]])+list(workout_all[i][5:]))
            # print(len(workoutDiary))

            list_ = ['chart1','workout','chart2','chart3']
            lis = ['Red', 'Good', 'Orange', 'Yellow', 'Green', 'Blue']
            num = [10, 15, 3, 5, 7, 2]

            # pub_data = []
            # if len(workout_all) > 0:
            #     for i in range(len(workout_all)):
            #         data = workout_all[i][2]
            #         pub_data1 = pub.line(data)
            #         pub_data.append(pub_data1[0] if len(pub_data1) > 0 else [])

            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
            return jsonify(dict(zip(list_,(j,workDiary,j,j))))
        except:
            print("error")

    def post(self,user_id):
        # conn = oracle.workout_connectDatabase()
        # data = oracle.workout_insert(conn, user_id, args)
        # return data #테이블 2개여서 성공이면 2이다
        # print(user_id)
        args = self.parser.parse_args()
        # print('args(CATEGORY) : ',args['CATEGORY'])
        # image = args['CATEGORY']
        # if image != '':
        #     conn = imagedb.connectDatabase()
        #     data = imagedb.insert(conn)
        #     str1 = 'C:\\Users\\user\\Upload\\' + str(data[0]) + '.png'
        #     args['CATEGORY'] = str(data[11])
        #     with open(str1, "bw") as f:
        #         f.write(base64.b64decode(image.encode()))
        #
        # for t in args:
        #     print(t, ':', args[t])

        conn1 = oracle.workout_connectDatabase()
        data = oracle.workout_insert(conn1, user_id, args)
        # print('post', data)
        return data  # 테이블 2개여서 성공이면 2이다

    def put(self,user_id):
        # dof = request.args.get('date')
        args = self.parser.parse_args()
        # print(args)
        conn = oracle.workout_connectDatabase()
        data = oracle.workout_update(conn, user_id, args)
        oracle.workout_close(conn)
        return data



    def delete(self,user_id):
        conn = oracle.workout_connectDatabase()
        data = oracle.workout_delete(conn,user_id)
        return data