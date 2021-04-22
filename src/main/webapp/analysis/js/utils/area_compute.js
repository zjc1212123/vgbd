/**
 * 按地区分析的echarts的通用计算方法
 * @param res
 */

/** 寻找城市列表 */
function findCityList(res) {
    let keys = Object.keys(res);
    let li = []
    for (let i = 0;i < keys.length; i++) {
        for (let j = 0;j < res[ keys[i] ].length; j++) {
            insertWithoutRepeat(li, res[ keys[i] ][j]['name'])
        }
    }
    return li;
}
/** 寻找未插入的城市并插入 */
function insertWithoutRepeat(l, o) {
    for (let i = 0; i < l.length; i++) {
        if (l[i] === o) return;
    }
    l.push(o);
}