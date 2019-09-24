/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @time: 2018/11/15 12:01
 \* Description: 问卷state
 \*/
import API from "../../api/api_questionnaire";
const state = {
  pageParam: {
    name: "",
    tag: "",
    limit: 10,
    page: 1
  },
  pageData: {
    currentPage: 1,
    pageSize: 5,
    pageSizes: [5],
    total: 0,
    rows: []
  }
};

const getters = {
  pageData: state => {
    return state.pageData
  },
  pageParam: state => {
    return state.pageParam
  }
};

const actions = {
  /**
   * 分页列出问卷
   * @param state
   * @param commit
   * @param params
   * @returns {Promise<any>}
   */
  listQuestionnairesByPageAct({state, commit}, params) {
    let param;
    if (!params) {
      param = state.pageParam
    } else {
      param = {
        limit: params.limit ? params.limit : state.pageParam.limit,
        page: params.page ? params.page : state.pageParam.page,
        name: params.name ? params.name : state.pageParam.name,
        tag: params.tag ? params.tag : state.pageParam.tag
      }
    }
    commit('setPageParam', param);
    return new Promise((resolve, reject) => {
      API.listQuestionnairesByPage(param).then(result => {
        if (!result) {
          reject(result)
        }
        result.pageSizes = [5];
        commit('setPageData', result);
        resolve(result)
      }, error => {
        reject(error)
      }).catch(err => {
        reject(err)
      })
    })
  },
  /**
   * 刷新分页数据
   * @param state
   * @param commit
   * @param params
   * @returns {Promise<any>}
   */
  resetPageAct({state, commit}, params) {
    let param = {
      name: "",
      tag: "",
      limit: 10,
      page: 1
    };
    commit('setPageParam', param);
    actions.listQuestionnairesByPageAct({state, commit})
  },
  deleteQuestionnaireAct({state, commit}, params) {
    return new Promise((resolve, reject) => {
      API.deleteQuestionnaire(params).then(result => {
        resolve(result)
      }, error => {
        reject(error)
      }).catch(err => {
        reject(err)
      })
    })
  },
  issueQuestionnaireAct({state, commit}, params) {
    return new Promise((resolve, reject) => {
      API.issueQuestionnaire(params).then(result => {
        resolve(result)
      }, error => {
        reject(error)
      }).catch(err => {
        reject(err)
      })
    })
  }
};

const mutations = {
  setPageData(state, entity) {
    state.pageData = entity
  },
  setPageParam(state, entity) {
    state.pageParam = entity
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
  namespaced: true
}
