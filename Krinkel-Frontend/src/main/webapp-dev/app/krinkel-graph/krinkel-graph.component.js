class KrinkelGraphController {
    constructor(KrinkelService , $filter) {
        this.$filter = $filter;
        var month = $filter('date')(Date.now(), "MM");
        var year = $filter('date')(Date.now(), "yyyy");
        var day = $filter('date')(Date.now(), "dd");
        this.startDate = new Date(year,month,day);
        month = month - 1;
        this.endDate = new Date(year,month,day);
        this.startDate = new Date(year,month-1,day);
        year = year +50;
        this.maxDate = new Date(year,1,1);
       /* $('#startDate').pickadate({
            format: 'dd-mm-yyyy',
            formatSumbit: 'dd-mm-yyyy',
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });
        $('#endDate').pickadate({
            format: 'dd-mm-yyyy',
            formatSumbit: 'dd-mm-yyyy',
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });*/
        /*
        //this.datePattern = /./;
        */
        this.KrinkelService = KrinkelService;
        this.selectedName = null;
        this.depth = 0;
        this.getAllGraphData();
    }
    //added so all graphs are refreshed
    getAllGraphData()
    {
        //console.log("d3 node " +d3.select(this).node());
        this.getDataForSunBurst();
        //console.log("done with getDataForSunBurst");
        this.getDataForStatus(this.selectedName,this.depth);
        //console.log("done with getDataForStatus");
        this.getDataForLogin(this.startDate,this.endDate);
        //console.log("done with getDataForLogin");
        this.$onInit();
    }

    getDataForSunBurst() {
        //console.log("in getDataForSunBurst");
        this.KrinkelService.getGraphSunInfo().then((results) => {
            this.sunBurstData = [{name: 'Inschrijvingen'}];
            this.sunBurstData[0].children = results.children;
        });
    }

    changeDate()
    {
            this.getAllGraphData();
    }

    getDataForStatus(name,depth) {
        //console.log("in getDataForStatus");
        //console.log("name= " + name);
        //console.log("depth= " + depth);
        this.KrinkelService.getGraphStatusInfo(name,depth).then((results) => {
            this.barData = [
                {
                    key: "Bevestigd deelnemer",
                    y: results.participantsConfirmed,
                    color: "#4caf50"
                },
                {
                    key: "Bevestigd medewerker",
                    y: results.volunteersConfirmed,
                    color: "#43a047"
                },
                {
                    key: "Niet bevestigd deelnemer",
                    y: results.participantsNotConfirmed,
                    color: "#2196f3"
                },
                {
                    key: "Niet bevestigd medewerker",
                    y: results.volunteersNotConfirmed,
                    color: "#1e88e5"
                },
                {
                    key: "Onbetaald deelnemer",
                    y: results.participantsNotPaid,
                    color: "#f44336"
                },
                {
                    key: "Onbetaald medewerker",
                    y: results.volunteersNotPaid,
                    color: "#b71c1c"
                },
                {
                    key: "Geannuleerd deelnemer",
                    y: results.participantsCancelled,
                    color: "#ffa500"
                },
                {
                    key: "Geannuleerd medewerker",
                    y: results.volunteersCancelled,
                    color: "#ff8c00"
                }
            ];
        });
    }

        getDataForLogin(startDate,endDate) {
            //console.log("in getDataForLogin");
        this.lineData = [];
        this.lineOptions = {};
        this.KrinkelService.getGraphLoginInfo(startDate,endDate).then((results) => {
            this.lineData = this.mapServerJSONToChartJSON(results);
        });
        this.$onInit();
    }

    mapServerJSONToChartJSON(results) {
        var outputArray = [];

        for (var key in results) {
            if (results.hasOwnProperty(key)) {
                var tempObject = {};
                var valuesOfKey = results[key];

                tempObject.key = key;
                tempObject.values = this.mapValuesToChartStructure(valuesOfKey);


                outputArray.push(tempObject);
            }
        }
        return outputArray;
    }

    mapValuesToChartStructure(valuesOfKey) {
        var tempArray = [];
        for (var keyOfValue in valuesOfKey) {
            if (valuesOfKey.hasOwnProperty(keyOfValue)) {
                tempArray.push([keyOfValue, valuesOfKey[keyOfValue]]);
            }
        }
        return tempArray;
    }

    $onInit() {
        //console.log("onInit executed")//remove
        this.test = "sunburstChart";
        var thiz = this;
        //sunburst------------------
        this.sunBurstOptions = {
            chart: {
                type: 'sunburstChart',
                height: 500,
                color: d3.scale.category20c(),
                noData: "Loading data...",
                duration: 250,
                sunburst: {
                    mode: 'size',
                    groupColorByParent: true,
                    //dispatch:{
                    //    chartClick:function(){console.log("chart clicked");},
                    //    elementClick:function(e){console.log("element clicked " + e.data.name+ e.data);}
                    //}
                },callback: function(chart) {
                    chart.sunburst.dispatch.on('elementClick', function(e){
                        if (undefined != e.data.name)
                        {
                            this.selectedName=e.data.name;
                            this.depth=e.data.depth;
                            thiz.getDataForStatus(e.data.name,e.data.depth);
                        }
                    });
                }
            }
        }
        //show no data found sunburst
        this.sunBurstData = [];


        this.lineOptions = {
            chart: {
                reduceXTicks:false,
                rotateLabels: 45,
                type: 'multiBarChart',
                height: 550,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 60, // to show the legend
                    left: 40
                },
                x: function (d) {
                    if (d != undefined) {
                        return d[0];
                    }
                },
                y: function (d) {
                    if (d != undefined) {
                        return d[1];
                    }
                },
                clipEdge: true,
                duration: 100,
                useInteractiveGuideline: true,
                stacked: true,
                xAxis: {
                    showMaxMin: false,
                    tickFormat: function (d)  {
                        //console.log("xAxis");
                       // console.log("xaxis" + d3.time.format('%d/%m/%Y')(new Date(d)));
                        //return d3.time.format('%d/%m/%Y')(new Date(d));
                        return d
                    }
                },
                yAxis: {
                    tickFormat: function (d) {
                        //console.log("Yaxis");
                       // console.log("yxaxis" + d3.time.format('%d/%m/%Y')(new Date(d)));
                        //return d3.time.format('%d/%m/%Y')(new Date(d));
                        return d;
                    }
                }
            }
        };

        this.lineData = [];
        //barchart ------------------- chart.tooltip.valueFormatter(function(d){return d.toFixed(4)});
        this.barOptions = {
            chart: {
                type: 'pieChart',
                height: 500,
                x: function (d) {
                    return d.key;
                },
                y: function (d) {
                    return d.y;
                },
                tooltip: {
                    valueFormatter: (function (d) {
                        return d.toFixed(0)
                    })
                },
                showLabels: true,
                duration: 500,
                labelThreshold: 0.01,
                labelSunbeamLayout: true,
                color: (function (d) {
                    return d.color
                }),
                legend: {
                    margin: {
                        top: 5,
                        right: 35,
                        bottom: 5,
                        left: 0
                    },
                    width: 400
                }
            }
        };
        this.barData = [];

    }
}

export var KrinkelGraphComponent = {
    template: require('./krinkel-graph.html'),
    controller: KrinkelGraphController
};

KrinkelGraphComponent.$inject = ['KrinkelService','$filter'];
