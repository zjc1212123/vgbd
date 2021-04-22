/**
 * 按时间排序的各种方法
 */

/** 补全时间轴 */
function getTimeList(res) {
    let timeList = [];
    let keys = Object.keys(res);
    for (let i = 0;i < keys.length; i++) {
        let o = res[ keys[i] ];
        for (let j = 0;j < o.length; j++) {
            if ($.inArray(o[j]['name'], timeList) < 0) {
                timeList.push(o[j]['name']);
            }
        }
    }
    return timeList.sort(function (a, b) {
        return new Date(a) > new Date(b) ? 1 : -1;
    });
}

/** 补全信息 */
function fixData(res, timeList) {
    let keys = Object.keys(res)

    for (let i = 0; i < keys.length; i++) {
        let flag = [] // 定义空列表
        for (let j = 0; j < timeList.length; j++) {
            if (res[ keys[i] ][j] !== undefined) {
                flag.push(res[ keys[i] ][j]['name']);
            }
        }

        for (let j = 0; j < timeList.length;j++) {
            if ($.inArray(timeList[j], flag) < 0) {
                let x = {};
                x['name'] = timeList[j];
                x['value'] = 0;
                res[ keys[i] ].push(x);
            }
        }

        // 对列表进行日期排序
        res[ keys[i] ].sort(compareObj('name'));
    }
    return res;
}

/**
 * 比较某个元素的大小， 用于排序
 * @param param
 * @returns {function(*, *): number}
 */
function compareObj(param){
    return function(obj1, obj2){
        let val1 = new Date(obj1[param]);
        let val2 = new Date(obj2[param]);

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
 * 计算年月时间轴
 */
function getMonthOrYearList(res, l, type) {
    let result = [];
    let resultData = [];
    let temp;
    let sum = 0;
    for (let i = 0; i < l.length; i++) { // 遍历日期时间轴
        temp = l[i].substring(0, type)
        sum += res[i]['value'];
        if ($.inArray(temp, result) < 0) {
            result.push(temp);
        }
        // 遍历算法， 计算每个月或者年的产量
        if (i !== l.length - 1 && l[i+1].substring(0, type) !== temp) {
            resultData.push(sum);
            sum = 0;
        } else if (i === l.length - 1) {
            resultData.push(sum);
        }
    }
    return [result, resultData];
}