from configparser import ConfigParser
from  cx_Oracle import connect
import os

'''
    명진형님 작업장(될예정)
'''
def connectDatabase():#데이타베이스 연결
    config = ConfigParser()
    # print(os.path.abspath('.'))
    # 데이터 절대경로 찾아주기
    path = os.path.dirname(os.path.abspath(__file__))
    # print(path)
    config.read(path+'/oracle.ini', encoding='utf8')
    # 데이타베이스 연결
    return connect(user=config['ORACLE']['USER'],
                   password=config['ORACLE']['PASSWORD'],
                   dsn=config['ORACLE']['URL'],encoding="UTF-8")
def close(conn):#커넥션객체 닫기
    if conn:
        conn.close()
def selectAll(conn):
    # SELECT c.calendar_no,c.account_no,DESCRIPTION,MEMO,START_POSTDATE,DIET_IMAGE,FOOD,FOOD_WEIGHT FROM calendar c INNER JOIN diet d ON c.calendar_no = d.calendar_no WHERE c.calendar_no = 105;
    with conn.cursor() as cursor:
        try:
            cursor.execute('SELECT * FROM account')
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이타 조회시 오류:',e)
            return None

# 식단 추가
def insert(conn,user_id,list_):

    test = [] #캘린더 테이블
    test.append(user_id)
    test.append(list_['DESCRIPTION'])
    test.append(list_['MEMO'])
    test.append(list_['DIET_IMAGE'])
    test.append(list_['FOOD'])
    test.append(list_['FOOD_WEIGHT'])
    with conn.cursor() as cursor:
        try:

            cursor.execute('INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, :1, :2, :3,DEFAULT,default) INTO diet VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), :4, :5, :6) SELECT * FROM DUAL',test)
            conn.commit()
            return cursor.rowcount
            '''
                CALENDAR_NO    NOT NULL NUMBER  필요없고       
                ACCOUNT_NO     NOT NULL NUMBER  넌 뭔데 no로 왔냐? user_id를 ACCOUNT_NO로 받아오자  
                DESCRIPTION    NOT NULL NVARCHAR2(100) 제목
                MEMO           NOT NULL NVARCHAR2(100) 내용
                START_POSTDATE          DATE           일정 시작일 (짜피 먹는건데 그냥 디폴트 하루임)
                END_POSTDATE            DATE            일정 끝나는날(날짜가 하루면 디폴트 넣어주셈)
                ----------- -------- -------------- 
                CALENDAR_NO NOT NULL NUMBER             SELECT max(calendar_no+1) FROM calendar; 로 받아오자
                DIET_IMAGE           NVARCHAR2(50)      사진
                FOOD        NOT NULL NVARCHAR2(100)     음식 예시:닭갈비
                FOOD_WEIGHT NOT NULL NUMBER             음식 용량 예시: 100
            
                INSERT ALL
                    INTO calendar VALUES(
                        SEQ_CALENDAR_CALENDAR_NO.nextval,
                        3,
                        '다중 insert2',
                        '볶음밥',
                        DEFAULT,
                        default  
                    )
                    INTO diet VALUES(
                        (SELECT max(calendar_no) FROM calendar),
                        null,
                        '김치볶음밥1',
                        100
                        )
                    SELECT * FROM DUAL;
            '''

        except Exception as e:
            print("error:",e)
            return 0
# def update(conn,user_id,list_): # user_id를 넣어야 하나 말아야 하나 캘린더 일련번호로 체크? 아니면 일련번호 + 사용자 번호까지?
#     '''CALENDAR_NO 추가
#         넌 왜 업데이트 2번해야하니?
#         인설트는 하나로 되던데? 모질이인가?
#     '''
#     # UPDATE (SELECT c.calendar_no,account_no,description,MEMO,START_POSTDATE,DIET_IMAGE,FOOD,FOOD_WEIGHT FROM calendar c INNER JOIN diet d ON c.calendar_no = d.calendar_no ) a SET (description,MEMO,DIET_IMAGE,FOOD,FOOD_WEIGHT) = (:1,:2,:3,:4,:5) WHERE a.calendar_no = :6;
#     list_cal =[]
#     list_cal.append(list_['DESCRIPTION'])
#     list_cal.append(list_['MEMO'])
#     list_cal.append(list_['CALENDAR_NO'])
#
#     list_diet = []
#     list_diet.append(list_['DIET_IMAGE'])
#     list_diet.append(list_['FOOD'])
#     list_diet.append(list_['FOOD_WEIGHT'])
#     list_diet.append(list_['CALENDAR_NO'])
#     print(list_diet)
#     with conn.cursor() as cursor:
#         try:
#             cursor.execute('',list_cal)
#             cursor.execute('', list_diet)
#             conn.commit()
#             return cursor.rowcount
#         except Exception as e:
#             print("에러")
#             return 0
#     return 0
