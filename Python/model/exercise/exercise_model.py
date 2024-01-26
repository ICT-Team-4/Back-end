from configparser import ConfigParser
from cx_Oracle import connect
import datetime as dt
import os

def exercise_connectDatabase():  # 데이타베이스 연결
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


def exercise_close(conn):  # 커넥션객체 닫기
    if conn:
        conn.close()


# 운동 추가
def exercise_insert(conn, user_id, list_):
    print(user_id, ":", list_)
    test = []  # 캘린더 테이블
    test.append(user_id)
    test.append(list_['DESCRIPTION']) #제목
    test.append(list_['MEMO']) #메모,내용
    test.append(list_['CATEGORY']) #운동 종류
    test.append(list_['ACCURACY']) #평균 정확도
    test.append(list_['COUNTS']) #운동 횟수
    test.append(list_['WEIGHT']) #무게

    with conn.cursor() as cursor:
        try:
            cursor.execute('INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, :1, :2, :3,DEFAULT,default) INTO exercise VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), :4, :5, :6, :7) SELECT * FROM DUAL', test)
            conn.commit()
            return cursor.rowcount

        except Exception as e:
            print("error:", e)
            return 0


def exercise_selectOne(conn, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            date_.append(date['START_POSTDATE'])
            cursor.execute(
                f"SELECT c.calendar_no,description,memo,category,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') time,accuracy,counts,weight,account_no FROM calendar c JOIN exercise e ON c.calendar_no = e.calendar_no WHERE calendar_no = :1 ORDER by time",
                date_)
            return cursor.fetchone()
        except Exception as e:
            print('레코드 하나 조회시 오류:', e)
            return None


def exercise_selectAll(conn, user_id, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            now = dt.datetime.now()
            date_.append(date if date != None else now.strftime('%Y-%m-%d'))
            date_.append(user_id)
            cursor.execute(
                f"SELECT c.calendar_no,description,memo,category,to_char(end_postdate,'YYYY-MM-DD HH24:MI:SS') time,accuracy,counts,weight,account_no FROM calendar c JOIN exercise e ON c.calendar_no = e.calendar_no LEFT JOIN calendar_likes cl ON c.calendar_no = cl.calendar_no WHERE TRUNC(end_postdate) = :1 AND account_no = :2 ORDER by time",
                date_)
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None