import { ChartDataSets } from "chart.js";

export interface SignalChartData {
    labels: string[];
    chartData: ChartDataSets[];
}