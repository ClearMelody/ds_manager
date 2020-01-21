/**
 \* Created with IntelliJ IDEA.
 \* User: wyk
 \* Date: 2020/01/02
 \*/

import Env from '../api/env';
let base = Env.baseURL;
export default {
  IMPORT_FILE_UPLOAD_URL: `${base}/base-db/import-table-data`,
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
  ],
  COLUMN_TYPES: [
    {
      name: '文本',
      value: 1
    },
    {
      name: '大文本',
      value: 2
    },
    {
      name: '数值',
      value: 3
    },
    {
      name: '数值(带小数)',
      value: 4
    },
    {
      name: '日期',
      value: 5
    }
  ]
}
