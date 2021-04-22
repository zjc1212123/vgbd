
//表单序列化json
$.fn.serializeJson = function () {
    var serializeObj = {};
    $(this.serializeArray()).each(function () {
        serializeObj[this.name] = this.value;
    });
    return serializeObj;
};

var chart1 = echarts.init(document.getElementById("chart1"));
var chart2 = echarts.init(document.getElementById("chart2"));
var chart3 = echarts.init(document.getElementById("chart3"));
var chart4 = echarts.init(document.getElementById("chart4"));
var chart5 = echarts.init(document.getElementById("chart5"));

init();

function init() {

    $.ajax({
        url: '../analysis/areaDiff',
        type: "post",
        data: {
            params: JSON.stringify($("#params").serializeJson())
        }
    }).then(function (res) {
        console.log(res)
        let res1 = res['yieldsByArea'] // 品种产量json
        let keys1 = Object.keys(res1) // 品种产量的key
        let legend1Option = [] // 果蔬种类
        let city1Option; // 城市分类
        /** 遍历每个元素 */
        for (let i = 0; i < keys1.length; i++) { // 获取所有的果蔬的种类
            if (keys1[i] === '01') {
                legend1Option.push('黄瓜')
            } else if (keys1[i] === '02') {
                legend1Option.push('西红柿')
            } else if (keys1[i] === '03') {
                legend1Option.push('西瓜')
            } else if (keys1[i] === '04') {
                legend1Option.push('草莓')
            } else {
                legend1Option.push(keys1[i])
            }
        }
        /** 配置城市信息 */
        city1Option = findCityList(res1);
        console.log(city1Option)
        /**
         * 配置第一个charts
         */
        let series1 = []
        for (let i = 0; i < legend1Option.length; i++) { // 遍历品种
            let aSeries = {
                name: legend1Option[i],
                type: 'bar',
                data: []
            }
            for (let j = 0; j < city1Option.length; j++) { // 遍历城市
                for (let o = 0; o < res1[ keys1[i] ].length; o++) { // 遍历
                    if (res1[keys1[i]][o].name === city1Option[j])
                        aSeries.data.push(res1[keys1[i]][o].value);
                }
            }
            series1.push(aSeries);
        }

        /** 配置数据列表 */
        chart1.setOption({
            title: {
                text: '品种产量'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: legend1Option,
                padding:[25,0,0,0]
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    name: '地区',
                    data: city1Option,
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '克',
                }
            ],
            series: series1
        })

        /**
         * 配置第二个echarts
         */
        let res2 = res['areaByCity'];
        let series2 = []
        for (let i = 0; i < legend1Option.length; i++) { // 遍历品种
            let aSeries = {
                name: legend1Option[i],
                type: 'bar',
                data: []
            }
            for (let j = 0; j < city1Option.length; j++) { // 遍历城市
                for (let o = 0; o < res2[ keys1[i] ].length; o++) { // 遍历
                    if (res2[keys1[i]][o].name === city1Option[j])
                        aSeries.data.push(res2[keys1[i]][o].value);
                }
            }
            series2.push(aSeries);
        }

        chart2.setOption({
            title: {
                text: '面积'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: legend1Option,
                padding:[25,0,0,0]
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                name: '亩',
                type: 'value'
            },
            yAxis: {
                type: 'category',
                name: '地区',
                data: city1Option
            },
            series: series2
        })

        /**
         * 设置第三个charts
         */
        let res3 = res['allYieldByCity']
        chart3.setOption({
            title:{
                text:'总产量'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c}'
            },
            legend: {
                orient: 'vertical',
                right: 0,
                data: city1Option
            },
            series: [
                {
                    name: '总产量',
                    type: 'pie',
                    selectedMode: 'single',
                    radius: [0, '40%'],
                    center: ['43%', '50%'],
                    label: {
                        position: 'inner'
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        {value: 335, name: '合计'}
                    ]
                },
                {
                    name: '总产量占比',
                    type: 'pie',
                    radius: ['55%', '80%'],
                    center: ['43%', '50%'],
                    label: {
                        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c|{c}}  {per|{d}%}  ',
                        backgroundColor: '#eee',
                        borderColor: '#aaa',
                        borderWidth: 1,
                        borderRadius: 3,
                        shadowBlur:3,
                        shadowOffsetX: 2,
                        shadowOffsetY: 2,
                        shadowColor: '#999',
                        padding: [0, 1],
                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                fontSize: 12,
                                align: 'center'
                            },
                            hr: {
                                borderColor: '#aaa',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            b: {
                                fontSize: 11,
                                lineHeight: 23
                            },
                            c: {
                                fontSize: 11
                            },
                            per: {
                                color: '#eee',
                                fontSize: 11,
                                backgroundColor: '#334455',
                                padding: [1, 1],
                                borderRadius: 2
                            }
                        }
                    },
                    data: res3
                }
            ]
        })

        /**
         * 设置第四个charts
         */
        let res4 = res['allAreaByCity']
        chart4.setOption({
            title: {
                text: '总面积',
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                right: 0,
                data: city1Option
            },
            toolbox: {
                show: false,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: '总面积占比',
                    type: 'pie',
                    radius: [20, 80],
                    center: ['43%', '50%'],
                    roseType: 'area',
                    data: res4
                }
            ]
        })

        /**
         * 第五个charts的绘制
         */

        let res5Temp = res['allGreenhouseByCity']
        let res5 = res5Temp.sort(function (a, b) { return a.value - b.value; });

        chart5.setOption({
            title: {
                text: '设施数量',
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                right: 0,
                data: city1Option
            },
            visualMap: {
                show: false,
                min: res5[0]['value'] * 1.2,
                max: res5[res5.length - 1]['value'] * 1.2,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series: [
                {
                    name: '设施水量占比',
                    type: 'pie',
                    radius: '100%',
                    center: ['43%', '50%'],
                    data: res5,
                    roseType: 'radius',
                    label: {
                        color: 'black'
                    },
                    labelLine: {
                        lineStyle: {
                            color: 'rgba(25,25,112, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    },
                    itemStyle: {
                        color: '#6495ed',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0)'
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
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