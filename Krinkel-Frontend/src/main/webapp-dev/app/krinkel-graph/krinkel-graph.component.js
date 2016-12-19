class KrinkelGraphController {
    constructor(KrinkelService) {
        this.KrinkelService = KrinkelService;
        this.getDataForSunBurst();
        this.getDataForStatus();
        this.getDataForLogin();
    }

    getDataForSunBurst() {
        this.KrinkelService.getGraphSunInfo().then((results) => {
            this.sunBurstData = [{name: 'Inschrijvingen'}];
            this.sunBurstData[0].children = results.children;
        });
    }

    getDataForStatus() {
        this.KrinkelService.getGraphStatusInfo().then((results) => {
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
                }
            ];
        });
    }

    getDataForLogin() {
        this.KrinkelService.getGraphLoginInfo().then((results) => {
            this.lineData = this.mapServerJSONToChartJSON(results);
        });
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
        // this.getDataForSunBurst();
        // this.getDataForStatus();
        // this.getDataForLogin();
        this.test = "sunburstChart";

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
                    groupColorByParent: true
                }
            }
        };
        //show no data found sunburst
        this.sunBurstData = [];
        /**
         * Old login chart*
         */
        // this.lineOptions = {
        //     chart: {
        //         type: 'stackedAreaChart',
        //         height: 450,
        //         margin: {
        //             top: 20,
        //             right: 20,
        //             bottom: 30,
        //             left: 40
        //         },
        //         x: function (d) {
        //             return d[0];
        //         },
        //         y: function (d) {
        //             return d[1];
        //         },
        //         showControls: false,
        //         useVoronoi: false,
        //         clipEdge: true,
        //         duration: 100,
        //         useInteractiveGuideline: true,
        //         xAxis: {
        //             showMaxMin: false,
        //             tickFormat: function (d) {
        //                 return d3.time.format('%x')(new Date(d))
        //             }
        //         },
        //         yAxis: {
        //             tickFormat: function (d) {
        //                 return d3.format(',.0f')(d);
        //             }
        //         },
        //         zoom: {
        //             enabled: false,
        //             scaleExtent: [1, 10],
        //             useFixedDomain: false,
        //             useNiceScale: false,
        //             horizontalOff: false,
        //             verticalOff: false,
        //             unzoomEventType: 'dblclick.zoom'
        //         }
        //     }
        // };

        this.lineOptions = {
            chart: {
                rotateLabels: 90,
                type: 'multiBarChart',
                height: 450,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 30,
                    left: 40
                },
                x: function (d) {
                    if (d != undefined) {
                        console.log("d[0] = " +d[0]);
                        return d[0];
                    }
                },
                y: function (d) {
                    if (d != undefined) {
                        console.log("d[1] = " +d[1]);
                        return d[1];
                    }
                },
                clipEdge: true,
                duration: 100,
                useInteractiveGuideline: true,
                stacked: true,
                xAxis: {
                    showMaxMin: false,
                    tickFormat: function (d) {
                        return d3.time.format('%x')(new Date(d))
                    }
                },
                yAxis: {
                    tickFormat: function (d) {
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
        console.log(this.lineOptions.values());
    }


}

export var KrinkelGraphComponent = {
    template: require('./krinkel-graph.html'),
    controller: KrinkelGraphController
};

KrinkelGraphComponent.$inject = ['KrinkelService'];
