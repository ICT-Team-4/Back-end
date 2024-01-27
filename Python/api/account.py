from flask_restful import Resource
from flask import jsonify
import numpy as np
import model.public.accountModel as oracle

class Account(Resource):
    def get(self,user_id):
        # print(user_id,type(user_id))
        conn = oracle.connectDatabase()
        # print(conn,type(conn))
        list_ =['account','diet']
        # print(list_,type(list_))
        user = oracle.selectOne(conn,user_id)
        # print(user,type(user))
        diet1 = np.array(oracle.diet_calendar(conn,user_id))
        print('diet_calendar:',diet1,':',type(diet1))
        diet1 = list(diet1.reshape(diet1.shape[0]))
        # print(diet1,type(diet1))
        return jsonify(dict(zip(list_,(user,diet1))))
