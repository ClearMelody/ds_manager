/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2020/01/07
 \* @filename: api_base_db.js
 \* Description:
 \*/
import * as API from './'

export default {
  listTables: id => API.GET('/base-db/list-tables/' + id, null),
  listTableColumns: params => API.GET('/base-db/list-columns', params),
  createTable: params => API.POST('/base-db/tb-create', params),
  pageData: params => API.GET('/base-db/page-data', params),
  exportTableData: (fileName,params) => API.EXPORT('/base-db/export-table-data',fileName, params),
  exportTableDataProgress: params => API.GET('/base-db/export-table-data-progress', null),
  downloadImportModel: (fileName,params) => API.EXPORT("/base-db/download-import-model", fileName, params)
}
