from configparser import ConfigParser
from cx_Oracle import connect
import datetime as dt
import os

def workout_connectDatabase():  # 데이타베이스 연결
    config = ConfigParser()
    # print(os.path.abspath('.'))
    # 데이터 절대경로 찾아주기
    path = os.path.dirname(os.path.dirname(__file__))
    # print(path)
    config.read(path + '/public/oracle.ini', encoding='utf8')
    # 데이타베이스 연결
    return connect(user=config['ORACLE']['user'],
                   password=config['ORACLE']['password'],
                   dsn=config['ORACLE']['URL'], encoding="UTF-8")


def workout_close(conn):  # 커넥션객체 닫기
    if conn:
        conn.close()


# 운동 추가
def workout_insert(conn, user_id, list_):

    test = []  # 캘린더 테이블
    test.append(user_id)
    test.append(list_['DESCRIPTION']) #제목
    test.append(list_['MEMO']) #메모,내용
    test.append(list_['CATEGORY']) #운동 종류
    test.append(list_['COUNTS']) #운동 횟수
    test.append(list_['WEIGHT']) #무게
    test.append(list_['END_DATE']) #운동 시간
    print('workout_insert',user_id, ":", test)
    sql = f"INSERT ALL INTO calendar VALUES(SEQ_CALENDAR.nextval, {user_id}, '{list_['DESCRIPTION']}', '{list_['MEMO']}', DEFAULT,TO_DATE('{list_['END_DATE']}' , 'YYYY-MM-DD HH24:MI')) INTO workout VALUES((SEQ_CALENDAR.nextval),'{list_['CATEGORY']}','0','{list_['COUNTS']}', '{list_['WEIGHT']}') SELECT * FROM DUAL"
    with conn.cursor() as cursor:
        try:
            # cursor.execute(f"INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, :1, :2, :3,default,default) INTO workout VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), :4, :5, :6, :7) SELECT * FROM DUAL", test)
            print(sql,'workout_sql')
            cursor.execute(sql)
            conn.commit()
            return cursor.rowcount

        except Exception as e:
            print("error:", e)
            return 0


def workout_selectOne(conn, cal_id):
    with conn.cursor() as cursor:
        try:
            # date_ = []  date인자
            # date_.append(date['START_POSTDATE'])
            data = cal_id if cal_id != 'undefined' else 0
            data = data if data != 'false' else 0
            print(data,'workout_data')
            # cursor.execute(f"SELECT c.calendar_no,description,memo,category,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') time,accuracy,counts,weight,account_no FROM calendar c JOIN workout e ON c.calendar_no = e.calendar_no WHERE calendar_no = :1 ORDER by time",date_)
            sql=f"SELECT c.calendar_no,description,memo,category,to_char(start_postdate,'YYYY-MM-DD HH24:MI:SS') s_time,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') e_time,accuracy,counts,weight,account_no FROM calendar c JOIN workout w ON c.calendar_no = w.calendar_no WHERE c.calendar_no = {data}"
            cursor.execute(sql)
            return cursor.fetchone()
        except Exception as e:
            print('레코드 하나 조회시 오류:', e)
            return None


def workout_selectAll(conn, user_id, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            now = dt.datetime.now()
            date_.append(date if date != None else now.strftime('%Y-%m-%d'))
            date_.append(user_id)
            # print(date if date != None else now.strftime('%Y-%m-%d'))
            # print(date_,'date_이건가..?')
            sql = f"SELECT c.calendar_no,description,memo,category,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') time,accuracy,counts,weight,like_date FROM calendar c JOIN workout w ON c.calendar_no = w.calendar_no LEFT JOIN calendar_likes cl ON c.calendar_no = cl.calendar_no WHERE TRUNC(end_postdate) = '{date if date != None else now.strftime('%Y-%m-%d')}' AND account_no = {user_id} ORDER by time"
            # print('sql:',sql)
            cursor.execute(sql)
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None


def workout_delete(conn,cal_id):
    print(cal_id, "workout_delete")
    with conn.cursor() as cursor:
        try:
            cursor.execute(f"delete calendar where calendar_no = {cal_id}")
            conn.commit()
            return cursor.rowcount
        except Exception as e:
            print('데이터 삭제시 오류:', e)
            return None


def workout_update(conn,cal_id,data):
    print(cal_id, "diet_update :",data)
    # test = []  # 캘린더 테이블
    # test.append(cal_id)
    # test.append(data['DESCRIPTION'])  # 제목
    # test.append(data['MEMO'])  # 메모,내용
    # test.append(data['CATEGORY'])  # 운동 종류
    # test.append(data['COUNTS'])  # 운동 횟수
    # test.append(data['WEIGHT'])  # 무게
    # test.append(data['END_DATE'])  # 운동 시간
    # print('workout_update', cal_id, ":", test)
    sql1 = f"UPDATE calendar SET DESCRIPTION='{data['DESCRIPTION']}', MEMO='{data['MEMO']}', END_DATE=TO_DATE('{data['END_DATE']}','YYYY-MM-DD HH24:MI') WHERE calendar_no={cal_id}"
    sql2 = f"UPDATE workout SET CATEGORY='{data['CATEGORY']}' ,COUNTS='{data['COUNTS']}' , WEIGHT='{data['WEIGHT']}' WHERE calendar_no={cal_id})"
    with conn.cursor() as cursor:
        try:
            cursor.execute(sql1)
            if cursor.rowcount != 0:
                cursor.execute(sql2)
            conn.commit()
            return cursor.rowcount
        except Exception as e:
            # conn.rollback()
            print('데이터 수정시 오류:', e)
            return 0