from flask_restful import Resource,reqparse
from flask import jsonify , request
from flask import make_response
import model.oracledb.accountModel as oracle

class Account(Resource):
    def get(self,user_id):
        print(user_id)
        conn = oracle.connectDatabase()
        return oracle.selectOne(conn,user_id)
