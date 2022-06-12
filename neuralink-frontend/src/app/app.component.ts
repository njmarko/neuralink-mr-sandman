import { Component, OnInit } from '@angular/core';
import { WebSocketService } from './service/web-socket-service/web-socket.service';

import { SignalChartData } from './model/SignalChartData';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from './service/user-service/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from './model/User';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'neuralink-frontend';

  chartData: SignalChartData[] = [
    {
      labels: [],
      chartData: [{ data: [], label: 'Speed' }]
    },
    {
      labels: [],
      chartData: [{ data: [], label: 'Temperature' }]
    },
    {
      labels: [],
      chartData: [{ data: [], label: 'Heart rate' }]
    },
    {
      labels: [],
      chartData: [{ data: [], label: 'Muscle voltage' }]
    },
    {
      labels: [],
      chartData: [{ data: [], label: 'Light level' }]
    },
    {
      labels: [],
      chartData: [{ data: [], label: 'Alcohol level' }]
    },
    {
      labels: [],
      chartData: [{ data: [], label: 'Caffeine level' }]
    },
  ]

  chartDataMap: Map<string, SignalChartData> = new Map();
  form: FormGroup;
  registered: boolean = false;
  selectedTabIndex: number = 0;
  user!: User;

  constructor(
    private socketService: WebSocketService,
    private fb: FormBuilder,
    private userService: UserService,
    private snackbar: MatSnackBar
  ) {
    this.chartDataMap.set('SPEED', this.chartData[0]);
    this.chartDataMap.set('TEMPERATURE', this.chartData[1]);
    this.chartDataMap.set('HEART_RATE', this.chartData[2]);
    this.chartDataMap.set('MUSCLE_VOLTAGE', this.chartData[3]);
    this.chartDataMap.set('LIGHT_LEVEL', this.chartData[4]);
    this.chartDataMap.set('ALCOHOL_LEVEL', this.chartData[5]);
    this.chartDataMap.set('CAFFEINE_LEVEL', this.chartData[6]);

    this.form = fb.group({
      age: [25, Validators.required],
      goingToBedTime: ['23:00', Validators.required],
      isLightSleep: [false, Validators.required],
      gender: ['MALE', Validators.required]
    });
  }

  ngOnInit(): void {
    this.socketService.initializeWebSocketConnection();

    this.socketService.onSignalRecieved().subscribe(signal => {
      const type: string = signal.signalType;
      if (this.chartDataMap.has(type)) {
        this.chartDataMap.get(type)?.labels.push('.');
        this.chartDataMap.get(type)?.chartData[0].data?.push(signal.value);
      }
    });

    this.socketService.onEventNotification().subscribe(event => {
      this.snackbar.open(`New notification: ${event.eventType} occured.`, "Confirm", { duration: 3000 });
    })
  }

  onSubmit(): void {
    if (!this.form.valid) {
      return;
    }
    this.userService.register(this.form.value).subscribe(response => {
      this.user = response;
      this.registered = true;
      this.selectedTabIndex = 1;
    })
  }
}
