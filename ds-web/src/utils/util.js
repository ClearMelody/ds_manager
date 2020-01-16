export function clearObject(obj) {
  for (let i in obj) {
    obj[i] = null
  }
}
