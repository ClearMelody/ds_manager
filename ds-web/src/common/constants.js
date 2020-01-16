/**
 \* Created with IntelliJ IDEA.
 \* User: wyk
 \* Date: 2020/01/02
 \*/

import Env from '../api/env';
let base = Env.baseURL;
export default {
  DB_TYPES : [
    {
      name: 'Oracle',
      tagColor: '#f56c6c',
      icon: 'db_oracle',
      urlModel: 'jdbc:oracle:thin:@<server>[:<1521>]:<database_name>'
    },
    {
      name: 'Mysql',
      tagColor: '#67c23a',
      icon: 'db_mysql',
      urlModel: 'jdbc:mysql://<hostname>[<:3306>]/<dbname>'
    },
    {
      name: 'PostgreSQL',
      tagColor: '#409eff',
      icon: 'db_postgresql',
      urlModel: 'jdbc:postgresql:[<//host>[:<5432>/]]<database>'
    },
    {
      name: 'Elasticsearch',
      icon: 'db_elasticsearch',
      tagColor: '#e6a23c',
      urlModel: null
    }
  ]
}
