import store from '../store'

export function hasPermission(permission) {
  let myPermissions = store.getters.permissions;
  return myPermissions.indexOf(permission) > -1;
}
export function noPermissionTips() {
  this.$message({
    message: '无相关权限',
    type: 'warning'
  });
}
