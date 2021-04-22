
//表单序列化json
$.fn.serializeJson = function () {
    var serializeObj = {};
    $(this.serializeArray()).each(function () {
        serializeObj[this.name] = this.value;
    });
    return serializeObj;
};

var myChart1 = echarts.init(document.getElementById('chart1'));
var myChart2 = echarts.init(document.getElementById('chart2'));
var myChart3 = echarts.init(document.getElementById('chart3'));
var chart4 = echarts.init(document.getElementById("chart4"));
var chart5 = echarts.init(document.getElementById("chart5"));

init();
/**
 * 初始化函数， 也是搜索函数
 */
function init() {

    $.ajax({
        url: '../analysis/timeDiff',
        type: 'post',
        data: {
            params: JSON.stringify($("#params").serializeJson())
        }
    }).then(function (res) {
        console.log(res)

        /**
         * 左上第一个charts的构建
         */
        let res1 = res['allYieldByDate']
        let tempTime = []
        let tempData = []
        for (let i = 0;i < res1.length; i++) {
            tempTime.push(res1[i]['name'])
            tempData.push(res1[i]['value'])
        }

        var data_11= getMonthOrYearList(res1, tempTime, 4)[0]; // 年要截取前4位
        var data_12= getMonthOrYearList(res1, tempTime, 7)[0]; // 月要截取前7位
        var data_13= tempTime
        var data11= getMonthOrYearList(res1, tempTime, 4)[1];
        var data12= getMonthOrYearList(res1, tempTime, 7)[1];
        var data13= tempData



        myChart1.setOption({
            title:{
                text:'总产量'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid:{
                x:60,
                y:60,
                x2:40,
                y2:30,
                borderWidth:10
            },
            legend:{
                data:['年','月','日'],
                padding:[25,0,0,0],
                selectedMode : 'single',
                selected: {
                    '年': false,
                    '月': false,
                    '日': true
                }
            },
            xAxis:{
                name: '时间',
                data:data_13
            },
            yAxis:{
                name: '克'
            },
            series:[
                {
                    name:'年',
                    type:'line',
                    data:data11
                },{
                    name:'月',
                    type:'line',
                    data:data12
                },{
                    name:'日',
                    type:'line',
                    data:data13
                }
            ]
        })

        myChart1.on('legendselectchanged', function(obj) {
            var name=obj.name;
            var option=myChart1.getOption();
            if(name=='年'){
                option.xAxis[0].data=data_11;
            }else if(name=='月'){
                option.xAxis[0].data=data_12;
            }else{
                option.xAxis[0].data=data_13;
            }
            myChart1.setOption(option,true);
        });

        /**
         * 左下第二个charts的构建
         */
        let res2 = res['allAreaByDate']
        let temp2Time = []
        let temp2Data = []
        for (let i = 0; i < res2.length; i++) {
            temp2Time.push(res2[i]['name']);
            temp2Data.push(res2[i]['value']);
        }
        var data_21= getMonthOrYearList(res2, temp2Time, 4)[0];
        var data_22= getMonthOrYearList(res2, temp2Time, 7)[0];
        var data_23= temp2Time;
        var data21= getMonthOrYearList(res2, temp2Time, 4)[1];
        var data22= getMonthOrYearList(res2, temp2Time, 7)[1];
        var data23= temp2Data

        myChart2.setOption({
            title:{
                text:'总面积'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid:{
                x:60,
                y:60,
                x2:40,
                y2:30,
                borderWidth:10
            },
            legend:{
                data:['年','月','日'],
                padding:[25,0,0,0],
                selectedMode : 'single',
                selected: {
                    '年': false,
                    '月': false,
                    '日': true
                }
            },
            xAxis:{
                name: '时间',
                data:data_23
            },
            yAxis:{
                name: '亩'
            },
            series:[
                {
                    name:'年',
                    type:'line',
                    data:data21
                },{
                    name:'月',
                    type:'line',
                    data:data22
                },{
                    name:'日',
                    type:'line',
                    data:data23
                }
            ]
        })

        myChart2.on('legendselectchanged', function(obj) {
            var name=obj.name;
            var option=myChart2.getOption();
            if(name=='年'){
                option.xAxis[0].data=data_21;
            }else if(name=='月'){
                option.xAxis[0].data=data_22;
            }else{
                option.xAxis[0].data=data_23;
            }
            myChart2.setOption(option,true);
        });
        /**
         * 左下第三个chart的制作
         */

        let res3 = res['allGreenhouseByDate']
        let temp3Time = []
        let temp3Data = []
        for (let i = 0;i < res3.length; i++) {
            temp3Time.push(res3[i]['name'])
            temp3Data.push(res3[i]['value'])
        }

        var data_31= getMonthOrYearList(res3, temp3Time, 4)[0];
        var data_32= getMonthOrYearList(res3, temp3Time, 7)[0];
        var data_33= temp3Time;
        var data31= getMonthOrYearList(res3, temp3Time, 4)[1];
        var data32= getMonthOrYearList(res3, temp3Time, 7)[1];
        var data33= temp3Data


        myChart3.setOption({
            title:{
                text:'设施数量'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid:{
                x:60,
                y:60,
                x2:40,
                y2:30,
                borderWidth:10
            },
            legend:{
                data:['年','月','日'],
                padding:[25,0,0,0],
                selectedMode : 'single',
                selected: {
                    '年': false,
                    '月': false,
                    '日': true
                }
            },
            xAxis:{
                name: '时间',
                data:data_33
            },
            yAxis:{
                name: '个'
            },
            series:[
                {
                    name:'年',
                    type:'line',
                    data:data31
                },{
                    name:'月',
                    type:'line',
                    data:data32
                },{
                    name:'日',
                    type:'line',
                    data:data33
                }
            ]
        })
        myChart3.on('legendselectchanged', function(obj) {
            var name=obj.name;
            var option=myChart3.getOption();
            if(name=='年'){
                option.xAxis[0].data=data_31;
            }else if(name=='月'){
                option.xAxis[0].data=data_32;
            }else{
                option.xAxis[0].data=data_33;
            }
            myChart3.setOption(option,true);
        });

        /**
         * 右上第四个charts的绘制
         */

        let res4 = res['yieldsByDate'] // 获取信息
        let keys = Object.keys(res4); // 蔬菜的品种编号
        let legend1Option = [] // 用来存放蔬菜的名字
        let timeList = getTimeList(res4); // 时间列表
        res4 = fixData(res4, timeList) // 补全缺失的信息
        let series = []
        for (let i = 0;i < keys.length; i++) {
            if (keys[i] === '01') {
                legend1Option.push('黄瓜')
            } else if (keys[i] === '02') {
                legend1Option.push('西红柿')
            } else if (keys[i] === '03') {
                legend1Option.push('西瓜')
            } else if (keys[i] === '04') {
                legend1Option.push('草莓')
            }
            let aSeries = {
                name: legend1Option[i],
                type: 'line',
                lineStyle: {
                    width: 1.5
                },
                data: []
            }
            for (let j = 0; j < timeList.length;j++) {
                aSeries['data'].push(res4[ keys[i] ][j]['value']);
            }
            series.push(aSeries);
        }

        chart4.setOption({
            title: {
                text: '品种产量'
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
                data: timeList
            }],
            yAxis: [{
                name: '克',
                type: 'value'
            }],
            series: series,
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
            }
        })
        /**
         * 右下第五个图的制作
         */
        let res5 = res['areaByDate'] // 获取信息
        let keys2 = Object.keys(res5); // 蔬菜的品种编号
        let legend2Option = [] // 用来存放蔬菜的名字
        let timeList2 = getTimeList(res5); // 时间列表
        res4 = fixData(res5, timeList2) // 补全缺失的信息
        let series2 = []
        for (let i = 0;i < keys2.length; i++) {
            if (keys2[i] === '01') {
                legend2Option.push('黄瓜')
            } else if (keys2[i] === '02') {
                legend2Option.push('西红柿')
            } else if (keys2[i] === '03') {
                legend2Option.push('西瓜')
            } else if (keys2[i] === '04') {
                legend2Option.push('草莓')
            }
            let aSeries = {
                name: legend2Option[i],
                type: 'line',
                lineStyle: {
                    width: 1.5
                },
                data: []
            }
            for (let j = 0; j < timeList2.length;j++) {
                aSeries['data'].push(res5[ keys2[i] ][j]['value']);
            }
            series2.push(aSeries);
        }

        chart5.setOption({
            title: {
                text: '品种面积'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: legend2Option
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                name: '时间',
                width: 0.5,
                data: timeList2
            }],
            yAxis: [{
                name: '亩',
                type: 'value'
            }],
            series: series2,
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
            }
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