from configparser import ConfigParser
from cx_Oracle import connect
import os

def excercise_connectDatabase():  # 데이타베이스 연결
    config = ConfigParser()
    # print(os.path.abspath('.'))
    # 데이터 절대경로 찾아주기
    path = os.path.dirname(os.path.abspath(__file__))
    # print(path)
    config.read(path + '/oracle.ini', encoding='utf8')
    # 데이타베이스 연결
    return connect(user=config['ORACLE']['user'],
                   password=config['ORACLE']['password'],
                   dsn=config['ORACLE']['URL'], encoding="UTF-8")


def excercise_close(conn):  # 커넥션객체 닫기
    if conn:
        conn.close()


# 운동 추가
def excercise_insert(conn, user_id, list_):
    print(user_id, ":", list_)
    test = []  # 캘린더 테이블
    test.append(user_id)
    test.append(list_['DESCRIPTION']) #제목
    test.append(list_['MEMO']) #메모,내용
    test.append(list_['CATEGORY']) #운동 종류
    test.append(list_['ACCURACY']) #평균 정확도
    test.append(list_['COUNTS']) #운동 횟수
    est.append(list_['WEIGHT']) #무게

    with conn.cursor() as cursor:
        try:
            cursor.execute('INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, :1, :2, :3,DEFAULT,default) INTO diet VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), :4, :5, :6) SELECT * FROM DUAL', test)
            conn.commit()
            return cursor.rowcount

        except Exception as e:
            print("error:", e)
            return 0


def excercise_selectOne(conn, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            date_.append(date['START_POSTDATE'])
            cursor.execute(
                f'',
                [date_])
            return cursor.fetchone()
        except Exception as e:
            print('레코드 하나 조회시 오류:', e)
            return None


def excercise_selectAll(conn, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            date_.append(date['START_POSTDATE'])
            date_.append(date['END_POSTDATE'])
            cursor.execute(
                f'',
                date_)
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None