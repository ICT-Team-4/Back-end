from flask_restful import Resource,reqparse
from flask import jsonify , request
import model.diet.diet_model as oracle
import model.image_oracledb.image_model as imagedb
import model.diet.publicData_model as pub

import numpy as np

from datetime import datetime
import base64

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

            food_all=[]
            cal = request.args.get('calId')
            # print(cal, 'cal')
            if(cal != None):
                conn = oracle.diet_connectDatabase()
                str1 = oracle.diet_selectOne(conn, cal)
                oracle.diet_close(conn)
                st = pub.line(str1[3])
                strArr = list(str1)
                strArr.append(list(st[0]))
                print(strArr,'str1')
                return jsonify(strArr)


            # print(dof)
            conn = oracle.diet_connectDatabase()
            # # # print("test",conn)
            food_all = oracle.diet_selectAll(conn, user_id, dof)
            oracle.diet_close(conn)
            print('food_all',food_all)
            foodDiary = []
            for i in range(len(food_all)):
                id = 41 if food_all[i][4] == None or food_all[i][4] == 'None' else food_all[i][4]
                print('id',id)
                str1 = 'C:\\Users\\user\\Upload\\' + str(id) + '.png'
                # print('food : ', id,":",len(food_all))
                # print(str1)

                with open(str1, "rb") as f:
                    image = base64.b64encode(f.read())
                    # print(str(image)[2:-2])
                    # food_all[i][4] = str(image)[2:-2]
                    foodDiary.append(list(food_all[i][0:4])+list(["data:image/png;base64,"+str(image)[2:-2]])+list(food_all[i][5:]))
            print('foodDiary',foodDiary)


            list_ = ['chart1','foodDiary','chart2','chart3'] #리액트로 보내줄 헤더


            lis = ['asdasdasd', '나이스', 'Yellow', 'Green', 'Purple', 'Orange1']
            num = [12, 19, 3, 5, 2, 3]

            pub_data = []
            arr2 = ['아침','점심','저녁','간식']
            time1=[0,0,0,0]
            if len(foodDiary) > 0:
                # print('test')
                data1 =[0,0,0,0,0,0]
                for i in range(len(foodDiary)):
                    # print('1',foodDiary[i][3])
                    time0 = datetime.strptime(foodDiary[i][3], '%Y-%m-%d %H:%M:%S')
                    hTime = time0.strftime('%H')

                    data = str(foodDiary[i][2])
                    pub_data = pub.line(data)[0][2:]

                    pub_num=0
                    if pub.line(data)[0][1].find('g') == -1:
                        pub_num = float(pub.line(data)[0][1][:-2])
                    else:
                        pub_num = float(pub.line(data)[0][1][:-1])
                    dnum = foodDiary[i][5]/pub_num

                    print("공공데이타 :", pub_data)  # null값 있는지 확인
                    if (int(hTime) >= 5 and int(hTime) <= 9):
                        time1[0] += round((pub_data[0]*dnum)+time1[0],2)
                    elif int(hTime) <= 14 and int(hTime) >= 11:
                        time1[1] += round((pub_data[0]*dnum)+time1[1],2)
                    elif int(hTime) <= 20 and int(hTime) >= 17:
                        time1[2] += round((pub_data[0]*dnum)+time1[2],2)
                    else:
                        time1[3] += round((pub_data[0]*dnum)+time1[3],2)


                    for k in range(len(pub_data)):
                        if(np.isnan(pub_data[k])):
                            print('k',k)
                            pub_data[k] = 0
                        print(k,':',round(pub_data[k]*dnum,2))
                        data1[k] = round(round(pub_data[k]*dnum,2)+data1[k],2)
                    pub_data = data1
                print(time1)
            arr = ('에너지(kcal)', '수분(g)', '단백질(g)', '지방(g)', '회분(g)', '탄수화물(g)')
            j=[]
            chart1 = []
            chart2 = []
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
                # print(len(pub_data))
                if index < len(arr) and len(pub_data) != 0:
                    chart1.append({'name':arr[index],'size':pub_data[index]})
                if index < len(arr2) and len(time1) != 0:
                    chart2.append({'name':arr2[index],'size':time1[index]})


            return jsonify(dict(zip(list_, (j, foodDiary, chart1, chart2))))
        except:
            print("error")

    def post(self,user_id):
        # print(user_id)
        args = self.parser.parse_args()
        # print(args['DIET_IMAGE'])
        # print(type(args))
        # imagedb
        image = args['DIET_IMAGE']
        print('image', image == '', image==None)
        if image != None and image != '':
            print('image,dase64',image)
            conn = imagedb.connectDatabase()
            data = imagedb.insert(conn)
            str1 = 'C:\\Users\\user\\Upload\\' + str(data[0]) + '.png'
            args['DIET_IMAGE'] = str(data[0])
            with open(str1, "bw") as f:
                f.write(base64.b64decode(image.encode()))

        for t in args:
            print(t,':',args[t])


        conn1 = oracle.diet_connectDatabase()
        data = oracle.diet_insert(conn1, user_id, args)
        print('post',data)
        return data #테이블 2개여서 성공이면 2이다
    def put(self,user_id):
        args = self.parser.parse_args()
        # print('args',args)
        conn = oracle.diet_connectDatabase()
        data = oracle.diet_update(conn, user_id,args)
        oracle.diet_close(conn)
        return data

    def delete(self,user_id):
        print('data', user_id)
        conn = oracle.diet_connectDatabase()
        data = oracle.diet_delete(conn, user_id)
        print('data',data)
        return data
    # def get(self,user_id): #사용자가 날짜를 클릭하는데 date값도 같이 받아야하는거 아닌가...?ㅠㅠ
    #     try:
    #         conn = oracle.diet_connectDatabase()
    #         return make_response(json.dumps(oracle.diet_selectOne(conn,user_id),ensure_ascii=False))
    #     except:
    #         print("error")