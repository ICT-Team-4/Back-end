from flask_restful import Resource
from flask import jsonify
import numpy as np
import model.public.accountModel as oracle

class Account(Resource):
    def get(self,user_id):
        print(user_id,type(user_id))
        conn = oracle.connectDatabase()
        print(conn,type(conn))
        list_ =['account','diet','workout']
        print(list_,type(list_))
        user = oracle.selectOne(conn,user_id)
        print(user,type(user))
        diet1 = np.array(oracle.diet_calendar(conn,user_id))
        diet1 = list(diet1.reshape(diet1.shape[0]))
        workout1 = np.array(oracle.exercise_calendar(conn,user_id))
        workout1 = list(workout1.reshape(workout1.shape[0]))
        return jsonify(dict(zip(list_,(user,diet1,workout1))))