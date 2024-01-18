from flask_restful import Resource,reqparse
from flask import jsonify
from flask import make_response
# import json

class Diet(Resource):
    def get(self,user_id):
        try:
            lis = ['asdasdasd', '나이스', 'Yellow', 'Green', 'Purple', 'Orange']
            num = [12, 19, 3, 5, 2, 3]
            
            #맘에 안듬
            j=[]
            for index in range(len(lis)):
                j.append({'name':lis[index],'size':num[index]})
                # print(lis[index])
            return jsonify(j)
        except:
            print("error")
        # print(user_id)
        # return user_id