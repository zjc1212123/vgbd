/**
 *  下边是一些全局变量
 */
let legend1Option = [] // 左上和右下的图的对应的蔬菜名称
let legend2Option = [] // 左下的图的对应的蔬菜名称


//表单序列化json
$.fn.serializeJson = function () {
    var serializeObj = {};
    $(this.serializeArray()).each(function () {
        serializeObj[this.name] = this.value;
    });
    return serializeObj;
};

var chart1 = echarts.init(document.getElementById("chart1")); // 初始化左上角图表
var chart2 = echarts.init(document.getElementById("chart2")); // 初始化左下角图表
var chart3 = echarts.init(document.getElementById("chart3")); // 初始化右上角图表
var chart4 = echarts.init(document.getElementById("chart4")); // 初始化右下角图表

init();

function init() {
    console.log(JSON.stringify($("#params").serializeJson()))
    /** 通过ajax向后台发送请求 */
    $.ajax({
        url: '../analysis/synthesize', // 表示要向后端的哪个路径发请求
        type: 'post',
        data: {
            params: JSON.stringify($("#params").serializeJson())
        }
    }).then(function (res) {

        // console.log(res)

        /** 时间列表 */
        let yearList = findYearMaxAndMin(res['yields']); // 产量数据

        let res1 = fixMessage(res['yields'], yearList);
        console.log(res1)
        /**
         * 左上图表一绘制 -------------------------------------------------------------
         * @type {string[]}
         */
        let varieties = Object.keys(res1)  // Object.keys()方法可以列出一个对象里边的所有key
        let tempSeries = []

        for (let i = 0; i < varieties.length; i++) {
            /** 初始化一钟蔬菜类型的数据 */
            let aTempSeries = {}
            /** 判断属于哪种蔬菜 */
            if (varieties[i] === '01') {
                legend1Option.push('黄瓜')
            } else if (varieties[i] === '02') {
                legend1Option.push('西红柿')
            } else if (varieties[i] === '03') {
                legend1Option.push('西瓜')
            } else if (varieties[i] === '04') {
                legend1Option.push('草莓')
            }
            /** 初始化数据列表 */
            let data = []
            for (let j = 0; j < res1[varieties[i]].length; j++) {
                data.push(res1[varieties[i]][j]['value'])
            }

            /** 初始化Series数据 */
            aTempSeries['name'] = legend1Option[i];
            aTempSeries['type'] = 'line'
            aTempSeries['lineStyle'] = {
                width: 1.5,
            }
            aTempSeries['data'] = data

            tempSeries.push(aTempSeries)
        }
        /** 设置给 echarts1 */
        chart1.setOption({
            title: {
                text: '产量'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: legend1Option
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                name: '时间',
                width: 0.5,
                data: yearList
            }],
            yAxis: [{
                name: '克',
                type: 'value'
            }],
            series: tempSeries,
            grid: {
                left: '4%',
                right: '5%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                show: false,
                feature: {
                    saveAsImage: {}
                }
            },
        })
        /** 左上图表绘制完成 */

        /**
         * 开始右下图的制作 ---------------------------------------------------------
         * @type {*[]}
         */
        let mySeries = []


        let biliList = getBili(res['yields'], yearList);
        for (let i = 0; i < legend1Option.length; i++) {
            let aSeries = {
                type: 'bar',
                data: biliList[i],
                coordinateSystem: 'polar',
                name: legend1Option[i],
                stack: 'a',
                emphasis: {
                    focus: 'series'
                }
            }
            mySeries.push(aSeries)
        }


        /**
         * 右下图表绘制开始
         */
        chart4.setOption({
            title: {
                text: '品种占比'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    },
                },
            },
            angleAxis: {
                type: 'category',
                data: yearList
            },
            radiusAxis: {},
            polar: {
                center: ['50%', '55%']
            },
            series: mySeries,
            legend: {
                show: true,
                data: legend1Option,
                padding: [25, 0, 0, 0]
            }
        })
        /** ------------------------------------------------------------------------------
         * 左下图表制作
         */
        let res2 = res['greenhouseNumbers']
        console.log()
        let values = [] // values参数
        for (let i = 0; i < res2.length; i++) { // 遍历设施数量参数， 取出value
            values.push(res2[i]['value'])
        }

        for (let i = 0; i < yearList.length; i++) {
            if ($.inArray(yearList[i], values) < 0) {
                res2.push({name: yearList[i], value: 0})
            }
        }

        values = [] // values参数
        for (let i = 0; i < res2.length; i++) { // 遍历设施数量参数， 取出value
            values.push(res2[i]['value'])
        }

        chart2.setOption({
            title: {
                text: '设施数量'
            },
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                name: '时间',
                type: 'category',
                width: 0.5,
                data: yearList
            },
            yAxis: {
                name: '个',
                type: 'value'

            },
            series: [{
                name: '设施数量',
                data: values,
                type: 'line',
                symbol: 'triangle',
                symbolSize: 15,
                lineStyle: {
                    color: 'blue',
                    width: 1.5,
                },
                itemStyle: {
                    borderWidth: 2,
                    borderColor: 'green',
                    color: 'blue'
                }
            }]
        })
        /** --------------------------------------------------------------------
         * 右上角图表绘制
         */
        console.log(res1)
        // console.log(res3)
        let res3 = fixMessage(res['areas'], yearList)
        let varietiesArea = Object.keys(res3)  // Object.keys()方法可以列出一个对象里边的所有key
        let tempSeriesArea = []

        for (let i = 0; i < varietiesArea.length; i++) {
            /** 初始化一钟蔬菜类型的数据 */
            let aTempSeries = {}
            /** 判断属于哪种蔬菜 */
            if (varieties[i] === '01') {
                legend2Option.push('黄瓜')
            } else if (varieties[i] === '02') {
                legend2Option.push('西红柿')
            } else if (varieties[i] === '03') {
                legend2Option.push('西瓜')
            } else if (varieties[i] === '04') {
                legend2Option.push('草莓')
            }
            /** 初始化数据列表 */
            let data = []
            for (let j = 0; j < res3[varietiesArea[i]].length; j++) {
                data.push(res3[varietiesArea[i]][j]['value'])
            }

            /** 初始化Series数据 */

            aTempSeries['name'] = legend2Option[i];
            aTempSeries['type'] = 'line';
            aTempSeries['stack'] = '面积';
            aTempSeries['areaStyle'] = {};
            aTempSeries['lineStyle'] = {
                width: 1.5,
            }
            aTempSeries['data'] = data

            tempSeriesArea.push(aTempSeries)
        }

        chart3.setOption({
            title: {
                text: '面积'
            },
            legend: {
                data: legend2Option,
                padding: [25, 0, 0, 0]
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            toolbox: {
                show: false,
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '5%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    name: '时间',
                    boundaryGap: false,
                    data: yearList
                }
            ],
            yAxis: [
                {
                    name: '亩',
                    type: 'value'
                }
            ],
            series: tempSeriesArea
        })
    })
}

//修改图表信息
$("#query").on("click", function () {
    init();
});

function updateData(option) {
    var temp = [];
    for (var i = 0; i < 10; i++) {
        temp[i] = Math.round(Math.random() * 5000);
    }
    option.series[0].data = temp;
}