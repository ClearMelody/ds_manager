function deepCopy(obj) {
  if (typeof obj !== 'object') {
    return obj;
  }
  let newobj = {};
  for (let attr in obj) {
    if (attr !== null) {
      newobj[attr] = deepCopy(obj[attr]);
    }
  }
  return newobj;
}

function groupArray(list, attr) {
  let map = {}, dest = [];
  for (let i = 0; i < list.length; i++) {
    let ai = list[i];
    if (!map[ai[attr]]) {
      dest.push({
        initial: ai[attr],
        list: [ai]
      });
      map[ai[attr]] = ai;
    } else {
      for (let j = 0; j < dest.length; j++) {
        let dj = dest[j];
        if (dj.initial === ai[attr]) {
          dj.list.push(ai);
          break;
        }
      }
    }
  }
  return dest;
}

export default {
  copyObject: deepCopy,
  groupArray: groupArray
}
