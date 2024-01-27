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
    test.append(list_['ACCURACY']) #평균 정확도
    test.append(list_['COUNTS']) #운동 횟수
    test.append(list_['WEIGHT']) #무게
    test.append(list_['END_DATE']) #운동 시간
    print(user_id, ":", test)
    sql = f"INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, {user_id}, '{list_['DESCRIPTION']}', '{list_['MEMO']}', DEFAULT,TO_DATE('{list_['END_DATE']}' , 'YYYY-MM-DD HH24:MI')) INTO workout VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), '{list_['CATEGORY']}', '{list_['ACCURACY']}', '{list_['COUNTS']}', '{list_['WEIGHT']}') SELECT * FROM DUAL"
    with conn.cursor() as cursor:
        try:
            # cursor.execute(f"INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, :1, :2, :3,default,default) INTO workout VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), :4, :5, :6, :7) SELECT * FROM DUAL", test)
            print(sql)
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
            print(data)
            # cursor.execute(f"SELECT c.calendar_no,description,memo,category,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') time,accuracy,counts,weight,account_no FROM calendar c JOIN workout e ON c.calendar_no = e.calendar_no WHERE calendar_no = :1 ORDER by time",date_)
            cursor.execute(f"SELECT c.calendar_no,description,memo,category,to_char(start_postdate,'YYYY-MM-DD HH24:MI:SS') s_time,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') e_time,accuracy,counts,weight,account_no FROM calendar c JOIN workout w ON c.calendar_no = w.calendar WHERE c.calendar_no={data})")
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
            cursor.execute(
                f"SELECT c.calendar_no,description,memo,category,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') time,accuracy,counts,weight,account_no FROM calendar c JOIN workout w ON c.calendar_no = w.calendar_no LEFT JOIN calendar_likes cl ON c.calendar_no = cl.calendar_no WHERE TRUNC(end_postdate) = :1 AND account_no = :2 ORDER by time",
                date_)
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None


def workout_delete(conn,cal_id):
    with conn.cursor() as cursor:
        try:
            cursor.execute(f"delete calendar where calendar_no = {cal_id}")
            conn.commit()
            return cursor.rowcount
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None


def workout_update(conn,cal_id,date):
    pass