import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketService } from './service/web-socket-service/web-socket.service';

import * as uuid from 'uuid';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignalDriverConfig } from './model/SignalDriverConfig';
import { DeviceSendSignal } from './model/DeviceSendSignal';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'neuralink-devices';
  signalDrivers: SignalDriverConfig[] = [
    {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'SPEED'
    },
    {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'TEMPERATURE'
    },
    {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'HEART_RATE'
    }, {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'MUSCLE_VOLTAGE'
    }, {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'LIGHT_LEVEL'
    }, {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'ALCOHOL_LEVEL'
    },
    {
      low: 0,
      high: 100,
      frequency: 1000,
      running: false,
      deviceId: uuid.v4(),
      signalType: 'CAFFEINE_LEVEL'
    }
  ];
  displayedColumns: string[] = ['signalType', 'low', 'high', 'frequency', 'action'];
  drivers: Map<string, any> = new Map();

  constructor(private socketService: WebSocketService) {

  }

  ngOnDestroy(): void {
    this.signalDrivers.forEach(signalDriver => this.stopDriver(signalDriver));
  }

  ngOnInit(): void {
    this.socketService.initializeWebSocketConnection();
  }

  onClick(): void {
    this.socketService.sendMessageUsingSocket({
      deviceId: uuid.v4(),
      signalType: "SPEED",
      value: 123
    });
  }

  sendSignal(request: DeviceSendSignal) {
    this.socketService.sendMessageUsingSocket(request);
  }

  randomSignalValue(min: number, max: number): number {
    return Math.random() * (max - min) + min;
  }

  startDriver(signal: SignalDriverConfig): void {
    this.drivers.set(signal.signalType, setInterval(
      () => {
        this.sendSignal({
          deviceId: signal.deviceId,
          signalType: signal.signalType,
          value: this.randomSignalValue(signal.low, signal.high)
        });
      }, signal.frequency
    ));
    signal.running = true;
  }

  stopDriver(signal: SignalDriverConfig): void {
    if (this.drivers.has(signal.signalType)) {
      clearInterval(this.drivers.get(signal.signalType));
    }
    signal.running = false;
  }
}
