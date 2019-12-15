const LoginKey = 'hasLogin'

export function getToken() {
  return sessionStorage.getItem(LoginKey);
}

export function setToken() {
  return sessionStorage.setItem(LoginKey, "1")
}

export function removeToken() {
  return sessionStorage.removeItem(LoginKey)
}
