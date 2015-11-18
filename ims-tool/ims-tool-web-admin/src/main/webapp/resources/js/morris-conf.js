var Script = function () {

    //morris chart

    $(function () {
      // data stolen from http://howmanyleft.co.uk/vehicle/jaguar_'e'_type
      var tax_data = [
           {"period": '13:00', "URA Natura": 2407, "pesquisa": 660},            
           {"period": '14:00', "URA Natura": 3407, "pesquisa": 560},
           {"period": '15:00', "URA Natura": 4351, "pesquisa": 429},
           {"period": '16:00', "URA Natura": 3269, "pesquisa": 518},
           {"period": '17:00', "URA Natura": 2269, "pesquisa": 718},
           {"period": '18:00', "URA Natura": 2269, "pesquisa": 818},
           {"period": '19:00', "URA Natura": 1269, "pesquisa": 218},
           
      ];
      Morris.Line({
        element: 'hero-graph',
        data: tax_data,
        xkey: 'period',
        ykeys: ['URA Natura', 'pesquisa'],
        labels: ['URA Natura', 'Pesquisa'],
        lineColors:['#4ECDC4','#ed5565']
      });

      Morris.Donut({
        element: 'hero-donut',
        data: [
          {label: 'WebService OK', value: 99 },
          {label: 'Falha Webservice', value: 1 }
        ],
          colors: ['#3498db', 'red', '#34495e'],
        formatter: function (y) { return y + "%" }
      });

      Morris.Area({
        element: 'hero-area',
        data: [
          {period: '14:00',    Retenção: 27},
          {period: '15:00',    Retenção: 29},
          {period: '16:00',    Retenção: 55},
          {period: '17:00',    Retenção: 45},
          {period: '18:00',    Retenção: 29},
          {period: '19:00',    Retenção: 28},
          {period: '20:00',    Retenção: 33},
          {period: '21:00',    Retenção: 35},
        ],

          xkey: 'period',
          ykeys: ['Retenção'],
          labels: ['Retenção'],
          hideHover: 'auto',
          lineWidth: 1,
          pointSize: 5,
          lineColors: ['#4a8bc2', '#ff6c60', '#a9d86e'],
          fillOpacity: 0.5,
          smooth: true
      });

      Morris.Bar({
        element: 'hero-bar',
        data: [
          {device: 'CN', geekbench: 5000},
          {device: 'CNO', geekbench: 300},
          {device: 'GR', geekbench: 265},
          {device: 'CND', geekbench: 2891},
          {device: 'CF', geekbench: 436}
        ],
        xkey: 'device',
        ykeys: ['geekbench'],
        labels: ['Perfil'],
        barRatio: 0.4,
        xLabelAngle: 35,
        hideHover: 'auto',
        barColors: ['#ac92ec']
      });

      new Morris.Line({
        element: 'examplefirst',
        xkey: 'year',
        ykeys: ['value'],
        labels: ['Value'],
        data: [
          {year: '2008', value: 20},
          {year: '2009', value: 10},
          {year: '2010', value: 5},
          {year: '2011', value: 5},
          {year: '2012', value: 20}
        ]
      });

      $('.code-example').each(function (index, el) {
        eval($(el).text());
      });
    });

}();




