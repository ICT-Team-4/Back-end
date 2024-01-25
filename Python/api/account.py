from flask_restful import Resource
from flask import jsonify
import numpy as np
import model.diet.accountModel as oracle

class Account(Resource):
    def get(self,user_id):
        print(user_id)
        conn = oracle.connectDatabase()
        list_ =['account','diet']
        user = oracle.selectOne(conn,user_id)
        diet1 = np.array(oracle.diet_calendar(conn,user_id))
        diet1 = list(diet1.reshape(diet1.shape[0]))
        return jsonify(dict(zip(list_,(user,diet1))))
