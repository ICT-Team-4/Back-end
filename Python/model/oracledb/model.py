from configparser import ConfigParser
from cx_Oracle import connect

class Oracle():
    def connetDatebase(self):
        config = ConfigParser()
        config.read('oracle.ini', encoding='utf8')

        return connect(user=config['ORACLE']['user'],
                       password=config['ORACLE']['password'],
                       dsn=config['ORACLE']['url'], encoding='utf8')

    def close(conn):
        if conn:
            conn.close()