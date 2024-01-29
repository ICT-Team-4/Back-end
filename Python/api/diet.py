from flask_restful import Resource,reqparse
from flask import jsonify , request
import model.diet.diet_model as oracle
import model.diet.publicData_model as pub


class Diet(Resource):
    def __init__(self):
        # print(self)
        self.parser = reqparse.RequestParser()
        # 아래는 공통 파라미터 설정(key=value로 받기)
        self.parser.add_argument('DESCRIPTION',location='form')
        self.parser.add_argument('MEMO', location='form')
        self.parser.add_argument('DIET_IMAGE', location='form')
        self.parser.add_argument('FOOD', location='form')
        self.parser.add_argument('FOOD_WEIGHT', location='form')
        self.parser.add_argument('END_DATE', location='form')
    def get(self,user_id):
        args = self.parser.parse_args()
        # print(args,'args')
        try:
            #겟 파라미터 받아오는 법(http://localhost:5000/diet/3?param1=23)
            dof = request.args.get('date')
            # print(dof, 'dof')


            food_all=[]
            cal = request.args.get('calId')
            # print(cal, 'cal')
            if(cal != None):
                #
                # print(cal)
                # # # print("test",conn)
                conn = oracle.diet_connectDatabase()
                str1 = oracle.diet_selectOne(conn, cal)
                oracle.diet_close(conn)
                print(str1,'str1')

                return str1


            # print(dof)
            conn = oracle.diet_connectDatabase()
            # # # print("test",conn)
            food_all = oracle.diet_selectAll(conn, user_id, dof)
            oracle.diet_close(conn)
            list_ = ['chart1','foodDiary','chart2','chart3'] #리액트로 보내줄 헤더


            lis = ['asdasdasd', '나이스', 'Yellow', 'Green', 'Purple', 'Orange1']
            num = [12, 19, 3, 5, 2, 3]

            # print(food_all[0]) # (21, 'fdads', '3ㅇㄴㅁㅇ', '순대', '2024-01-25 00:03:23', '2024-01-25 00:03:23', None, 300, 4)
            pub_data = []
            if len(food_all) > 0:
                for i in range(len(meal_all)):
                    data = food_all[i][2]
                    print(data,'data....하.')
                    pub_data1 = pub.line(data)
                    print(pub_data1,'pub_data1여긴가..')
                    # print(pub_data1)
                    pub_data.append(pub_data1[0] if len(pub_data1) > 0 else [])
            # print(pub_data[0][1:-1])

            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
                print(lis[index])
            print(food_all)
            return jsonify(dict(zip(list_,(j,food_all,j,j))))
        except:
            print("error")

    def post(self,user_id):
        # print(user_id)
        args = self.parser.parse_args()
        # print(type(args))
        conn = oracle.diet_connectDatabase()
        data = oracle.diet_insert(conn, user_id, args)
        # print('post',data)
        return data #테이블 2개여서 성공이면 2이다
    def put(self,cal_id):
        pass
    def delete(self,cal_id):
        pass
    # def get(self,user_id): #사용자가 날짜를 클릭하는데 date값도 같이 받아야하는거 아닌가...?ㅠㅠ
    #     try:
    #         conn = oracle.diet_connectDatabase()
    #         return make_response(json.dumps(oracle.diet_selectOne(conn,user_id),ensure_ascii=False))
    #     except:
    #         print("error")