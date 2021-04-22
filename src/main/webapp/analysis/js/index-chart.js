//绘制地图流向的线
function makeLine() {
    $.ajaxSettings.async = false;
    var lines = [];
    $.getJSON("api/lines.json", function (data) {
        lines = data;
    })
    $.ajaxSettings.async = true;
    return {
        type: 'lines',
        effect: {
            show: true,
            period: 2, //箭头指向速度，值越小速度越快
            trailLength: 0.02, //特效尾迹长度[0,1]值越大，尾迹越长重
            symbol: 'arrow', //箭头图标
            symbolSize: 5, //图标大小
        },
        lineStyle: {
            normal: {
                width: 1, //尾迹线条宽度
                opacity: 1, //尾迹线条透明度
                curveness: .3 //尾迹线条曲直度
            }
        },
        data: lines
    }
}

//绘制地图的点
function makePoint() {
    $.ajaxSettings.async = false;
    var points = [];
    $.getJSON("api/points.json", function (data) {
        points = data;
    })
    console.log(points)
    $.ajaxSettings.async = true;
    return {
        type: 'effectScatter',
        coordinateSystem: 'geo',
        rippleEffect: { //涟漪特效
            period: 4, //动画时间，值越小速度越快
            brushType: 'stroke', //波纹绘制方式 stroke, fill
            scale: 4 //波纹圆环最大限制，值越大波纹越大
        },
        label: {
            normal: {
                show: true,
                position: 'right', //显示位置
                offset: [5, 0], //偏移设置
                formatter: function (params) { //圆环显示文字
                    return params.data.name;
                },
                fontSize: 13
            },
            emphasis: {
                show: true
            }
        },
        symbol: 'circle',
        symbolSize: 6,
        itemStyle: {
            normal: {
                show: false,
                color: "#2980b9",
            }
        },
        data: points
    };
}


//地图基本设置
// 基于准备好的dom，初始化echarts实例
$.getJSON('api/hebei.json', function (data) {
    echarts.registerMap('河北', data);
    // 为ECharts准备一个具备大小（宽高）的Dom
    var map = echarts.init(document.getElementById('map'));
    var series = [];
    series.push(makeLine(), makePoint());
    series.push({
        type: 'effectScatter',
        coordinateSystem: 'geo',
        rippleEffect: { //涟漪特效
            period: 4, //动画时间，值越小速度越快
            brushType: 'stroke', //波纹绘制方式 stroke, fill
            scale: 4 //波纹圆环最大限制，值越大波纹越大
        },
        label: {
            normal: {
                show: false,
                position: 'right', //显示位置
                offset: [5, 0], //偏移设置
                formatter: function (params) { //圆环显示文字
                    return params.data.name;
                },
                fontSize: 13
            },
            emphasis: {
                show: true
            }
        },
        symbol: 'square',
        symbolSize: 10,
        itemStyle: {
            normal: {
                show: false,
                color: "#2ecc71",
            }
        },
        data: [{
            name: "河北农业大学东校区",
            value: [115.489891,38.655841]
        }]
    })
    var option = {
        tooltip: {
            trigger: 'item',
            backgroundColor: 'rgba(236,240,241,0.8)',
            borderColor: '#FFFFCC',
            showDelay: 0,
            hideDelay: 0,
            transitionDuration: 0,
            extraCssText: 'z-index:100',
            formatter: function (param) {
                return param.data.name;
            }
        },
        backgroundColor: "rgba(0,0,0,0)",
        geo: {
            map: '河北',
            zoom: 1,
            label: {
                emphasis: {
                    show: false
                }
            },
            roam: true, //是否允许缩放
            itemStyle: {
                normal: {
                    color: 'rgba(255, 255, 255, 1)', //地图背景色
                    borderColor: '#516a89', //省市边界线00fcff 516a89
                    borderWidth: 1
                },
                emphasis: {
                    color: 'rgba(37, 43, 61, .5)' //悬浮背景
                }
            }
        },
        series: series
    };
    // 使用刚指定的配置项和数据显示图表。
    console.log(option)
    map.setOption(option);
});


//右侧图表1
var chart1Option = {
    title: {
        text: "今日总产量",
    },
    grid: {
        left: '3%',
        right: '5%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        data: [],
        name: '时间'
    },
    yAxis: {
        type: 'value',
        name: '克'
    },
    series: [{
        data: [],
        type: 'line'
    }]
};
var chart1 = echarts.init(document.getElementById("chart1"));
chart1.setOption(chart1Option)

//右侧图表2
var chart2Option = {
    title: {
        text: '今日品种产量'
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        data: ['2011年', '2012年']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        name: '克',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        name: '种类',
        data: [""]
    },
    series: [
        {
            name: '河北省',
            type: 'bar',
            data: [0]
        }
    ]
};
var chart2 = echarts.init(document.getElementById("chart2"));
chart2.setOption(chart2Option)

//修改图表信息
/*$("#query").on("click", function () {
    updateData(chart1Option);
    chart1.setOption(chart1Option);
    updateData(chart2Option);
    chart2.setOption(chart2Option);
});*/

function updateData(option) {
    var temp = [];
    for (var i = 0; i < 10; i++) {
        temp[i] = Math.round(Math.random() * 5000);
    }
    option.series[0].data = temp;
}

//表单序列化json
$.fn.serializeJson = function () {
    var serializeObj = {};
    $(this.serializeArray()).each(function () {
        serializeObj[this.name] = this.value;
    });
    return serializeObj;
};