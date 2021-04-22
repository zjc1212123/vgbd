/**
 * 寻找所有数据中最大和最小的年份，并补全时间轴信息
 * @param obj
 * @returns {[]}
 */
function findYearMaxAndMin (obj) {
    let keys = Object.keys(obj)

    let maxYear = 0;
    let minYear = 999999;

    for (let i = 0; i < keys.length; i++) {
        for (let j = 0; j < obj[ keys[i] ].length; j++ ) {
            let year = obj[ keys[i] ][j]['name']
            if (parseInt(year) < minYear) {
                minYear = parseInt(year)
            }

            if (parseInt(year) > maxYear) {
                maxYear = parseInt(year)
            }
        }
    }

    let res = []
    for (let i = minYear;i <= maxYear;i++) {
        res.push(i)
    }

    return res;
}

/**
 * 补全数据信息
 * @param obj
 * @param yearList
 * @returns {*}
 */
function fixMessage(obj, yearList) {
    let keys = Object.keys(obj)
    for (let i = 0; i < keys.length; i++) { // 遍历每一种蔬菜

        let flag = []; // 定义一个空列表， 用来存放已经有的年份
        for (let j = 0;j < yearList.length;j++) {
            if(obj[keys[i]][j] !== undefined) {
                flag.push(obj[ keys[i] ][j]['name']);
            }
        }

        for (let j = 0;j < yearList.length; j++) {
            if(!isIn(flag, yearList[j])) { // 如果年份不在echarts列表中， 那么填进去
                let o = {}
                o['name'] = yearList[j];
                o['value'] = 0;
                obj[ keys[i] ].push(o)
            }
        }

        // 对列表进行年份排序
        obj[ keys[i] ].sort(compareObj('name'))
    }
    return obj;
}

/**
 * 比较某个元素的大小， 用于排序
 * @param param
 * @returns {function(*, *): number}
 */
function compareObj(param){
    return function(obj1, obj2){
        let val1 = obj1[param];
        let val2 = obj2[param];

        if (val1 < val2){
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    };
}

/**
 * 判断一个元素是否在列表中
 * @param list
 * @param k
 * @returns {boolean}
 */
function isIn(list, k) {
    for (let i = 0;i < list.length; i++) {
        if (list[i] === k) {
            return true;
        }
    }return false;
}

/**
 * 计算所有年的产量占比
 * @param obj
 * @param yearList
 * @returns {[]}
 */
function getBili(obj, yearList) {
    let biliList = []
    let keys = Object.keys(obj);
    for (let i = 0; i < yearList.length; i++) { // 循环n年， 取出每一年的数据做比例计算
        let temp = [];
        let sum = 0;
        // 计算某一年的所有蔬菜总量
        for (let j = 0; j < keys.length;j++) { // 循环第n年里的所有蔬菜的产量， 并做比例计算
            sum += obj[ keys[j] ][i]['value'];
        }
        for (let j = 0; j < keys.length; j++) {
            if (sum === 0) temp.push(0);
            else temp.push((obj[ keys[j] ][i]['value'] / sum).toFixed(2));
        }
        biliList.push(temp);
    }

    let resList = []
    /** 转换 biliList 为echarts类型 */
    for (let i = 0; i < keys.length; i++) {
        let temp = [];
        for (let j = 0; j < yearList.length;j++) {
            temp.push(biliList[j][i]);
        }
        resList.push(temp)
    }
    return resList;
}