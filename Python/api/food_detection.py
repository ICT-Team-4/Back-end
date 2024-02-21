'''
python -m pip install --upgrade pip
pip install ultralytics
pip install pillow
pip install opencv-python


'''
from flask_restful import Resource
from flask import request,make_response


import base64
from PIL import Image
import json
import io
import os
from ultralytics import YOLO
import shutil

import pandas as pd

class FoodDetection(Resource):
    def __init__(self):
        #모델 로드(#best.pt파일은 프로젝트 루트에)
        # 커스텀 이미지로 사전 학습된 모델 로드
        self.model = YOLO('food/best.pt')
    #def get(self):
        # 테스트용
        #base64Encoded = "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"
    def post(self):
        base64Encoded = request.form['base64Encoded']
        # base64 이미지 인코딩 문자열을 decode
        image_b64 = base64.b64decode(base64Encoded)
        image_memory = Image.open(io.BytesIO(image_b64))#이미지 파일로 디코딩
        image_memory.save('./images/new.jpg')#물리적으로 이미지 저장
        results = self.model.predict(['./images/new.jpg'],save=True, save_txt= True)
        # print('results\n',results)
        # print('results[0].save_dir\n', results[0].save_dir)
        # train()후 prdict()가 아니기때문에 예측 이미지명은 그대로다
        #pred_image = Image.open(os.path.join(results[0].save_dir, 'new.jpg'))
        # 이미지 뷰어 윈도우 프로그램에 띄울때
        #pred_image.show()
        #예측 이미지를 base64로 인코딩
        with open(os.path.join(results[0].save_dir, 'labels\\new.txt'),'rb') as f:
            data = f.readlines() #예측데이타
        #공공데이타와 연결하기 위한 수정 과정
        data1 = pd.read_csv('food/food_yolov8.csv',encoding='utf-8')
        name =(data[0].split()[0]).decode('utf-8').replace('\x08', '')
        # print(name,':',data1['number']==int(name))
        foodName = data1[data1['number']==int(name)]['name'].to_string()
        print(foodName)
        # print('dir',os.path.join(results[0].save_dir, 'new.jpg'))
        with open(os.path.join(results[0].save_dir, 'new.jpg'),'rb') as f:
            base64Predicted= base64.b64encode(f.read()).decode('utf-8')
        # print('base64Predicted\n',base64Predicted)
        shutil.rmtree(results[0].save_dir)
        return make_response(json.dumps({'base64':base64Predicted , 'food':foodName.split()[1]},ensure_ascii=False))
        #return make_response(json.dumps(y_pred_dict,ensure_ascii=False))
