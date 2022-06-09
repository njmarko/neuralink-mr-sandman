import { Component, Input } from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';

@Component({
  selector: 'app-signal-chart',
  templateUrl: './signal-chart.component.html',
  styleUrls: ['./signal-chart.component.scss']
})
export class SignalChartComponent {
  
  @Input()
  data: ChartDataSets[] = [];
  
  @Input()
  labels: Label[] = [];

  @Input()
  type: 'line' | 'bar' = 'line';

  options: (ChartOptions & { annotation?: any }) = {
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }
  };
  colors: Color[] = [
    {
      borderColor: '#3f51b5',
      backgroundColor: 'rgba(63, 81, 181, 0.3)',
    },
  ];
}