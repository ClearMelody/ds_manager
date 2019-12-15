const getters = {
  id: state => state.user.id,
  avatar: state => state.user.avatar,
  role: state => state.user.role,
  menus: state => state.user.menus,
  permissions: state => state.user.permissions,

  //permission_routers: state => state.permission.routers,
  //addRouters: state => state.permission.addRouters
}
export default getters
