from flask import Flask,jsonify
from flask_restx import Api,Resource
from flask_cors import CORS
import cx_Oracle
import json

app = Flask(__name__)
CORS(app)

api = Api(app, version='1.0', title='API 문서', description='Swagger 문서', doc="/swagger-ui")
test_api = api.namespace('test', description='조회 API')
# 출처: https://chamch-dev.tistory.com/21 [개발참치의 개발이야기:티스토리]

#test용
@app.route('/users')
def users():
	# users 데이터를 Json 형식으로 반환한다
    s =[{"id": 1, "pwd": "yerin"},{"id": 2, "pwd": "dalkong"}]
    return jsonify(s)

@test_api.route('/testAPI') #/test/testAPI
class Test(Resource): #외부에는 의미 없음
    # def get(self): #get,post,put.delete
    #     return 'Hello World Test'
    def get(self):
        print('tset용')
        connection = cx_Oracle.connect("ICTUSER", "ICT1234", "localhost:1521/xepdb1")
        cursor = connection.cursor()
        cursor.execute("SELECT USERNAME,PASSWORD FROM member")
        rows = cursor.fetchall()
        cursor.close()
        connection.close()
        j =[]
        for id,pwd in rows:
            j.append({'id':id,'pwd':pwd})
        print(json.dumps(j))
        with open("user.json",'w') as f:
            f.write(json.dumps(j,indent=4,ensure_ascii=False))
        return jsonify(j)

@test_api.route('/testGET') #/test/testAPI
@test_api.response(404, 'id를 찾을 수가 없어요')
@test_api.param('id','test')
class TestGet(Resource): #외부에는 의미 없음
    def get(self, id):
        print(id)

        return "tes"



if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=5000)

